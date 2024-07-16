package com.nlw.planner.service;

import com.nlw.planner.dto.TripRequestPayload;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.exceptions.InvalidTripPeriod;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.TripRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripServiceTest {
    @Mock
    private TripRepository repository;

    @InjectMocks
    private TripService tripService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create trip successfully")
    void registerTripCase1() {
        // Arrange
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");
        var payload = new TripRequestPayload("São Paulo",
                "2024-06-25T21:51:54.7342",
                "2024-06-26T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");

        var trip = new Trip(payload);
        when(repository.save(any(Trip.class))).thenReturn(trip);

        // Act
        TripResponseDTO responseDTO = tripService.registerTrip(payload);

        // Assert
        assertEquals(trip.getId(), responseDTO.tripId());
        verify(repository, times(1)).save(any(Trip.class));
    }

    @Test
    @DisplayName("should throw InvalidTripPeriod exception")
    void registerTripCase2() {
        // Arrange
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");
        var payload = new TripRequestPayload("São Paulo",
                "2024-06-25T21:51:54.7342",
                "2024-06-23T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");


        // Act & Assert
        assertThrows(InvalidTripPeriod.class, () -> tripService.registerTrip(payload));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("should update trip successfully")
    void updateTripCase1() {
        // Arrange
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");
        var payloadTrip = new TripRequestPayload("São Paulo",
                "2024-06-20T21:51:54.7342",
                "2024-06-25T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");
        var rawTrip = new Trip(payloadTrip);
        var payload = new TripRequestPayload("Natal",
                "2024-06-15T21:51:54.7342",
                "2024-06-30T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");

        // Act
        var updatedTrip = tripService.updateTrip(payload, rawTrip);

        assertEquals(LocalDateTime.parse("2024-06-15T21:51:54.7342", DateTimeFormatter.ISO_DATE_TIME), updatedTrip.getStartsAt());
        assertEquals(LocalDateTime.parse("2024-06-30T21:51:54.7342", DateTimeFormatter.ISO_DATE_TIME), updatedTrip.getEndsAt());
        assertEquals(payload.destination(), updatedTrip.getDestination());

        verify(repository, times(1)).save(updatedTrip);
    }

    @Test
    void confirmTrip() {
        // Arrange
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");
        var payload = new TripRequestPayload("São Paulo",
                "2024-06-20T21:51:54.7342",
                "2024-06-25T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");
        var trip = new Trip(payload);

        var tripConfirmed = tripService.confirmTrip(trip);

        assertEquals(true, tripConfirmed.getIsConfirmed());

        verify(repository, times(1)).save(any(Trip.class));
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }
}