package com.nlw.planner.service;

import com.nlw.planner.dto.TripRequestPayload;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.exceptions.InvalidTripPeriod;
import com.nlw.planner.factory.TripPayloadFactory;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository repository;

    @InjectMocks
    private TripService service;

    @Nested
    class RegisterTripTests {
        private TripRequestPayload payload;

        @BeforeEach
        void setUp() {
            payload = TripPayloadFactory.getPayload();
        }

        @Test
        void shouldRegisterTrip() {
            // Arrange
            var newTrip = new Trip(payload);
            when(repository.save(any(Trip.class))).thenReturn(newTrip);

            // Act
            TripResponseDTO response = service.registerTrip(payload);

            // Assert
            assertNotNull(response);
            assertEquals(newTrip.getId(), response.tripId());
            verify(repository, times(1)).save(any(Trip.class));
        }

        @Test
        void shouldReturnInvalidTripPeriod() {
            // Arrange
            var payload = TripPayloadFactory.getPayloadWithInvalidPeriod();
            var newTrip = new Trip(payload);

            // Act & Assert
            InvalidTripPeriod exception = assertThrows(InvalidTripPeriod.class, () -> service.registerTrip(payload));

            assertEquals("Invalid Trip Period", exception.getMessage());
            verify(repository, never()).save(any(Trip.class));
        }
    }

    @Nested
    class UpdateTripTests {
        private TripRequestPayload payload;
        private Trip rawTrip;

        @BeforeEach
        void setUp() {
            payload = TripPayloadFactory.getPayload();
            rawTrip = new Trip(payload);
            // Configure payload and rawTrip as needed
        }

        @Test
        void shouldUpdateTrip() {
            // Arrange
            rawTrip.setId(UUID.randomUUID());
            when(repository.save(any(Trip.class))).thenReturn(rawTrip);

            // Act
            var updatedTrip = service.updateTrip(payload, rawTrip);

            // Assert
            assertNotNull(updatedTrip);
            assertEquals(rawTrip.getId(), updatedTrip.getId());
            assertEquals(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME), updatedTrip.getStartsAt());
            assertEquals(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME), updatedTrip.getEndsAt());
            assertEquals(payload.destination(), updatedTrip.getDestination());
            verify(repository, times(1)).save(rawTrip);
        }
    }

    @Nested
    class ConfirmTripTests {
        private Trip trip;

        @BeforeEach
        void setUp() {
            trip = new Trip();
            // Configure trip as needed
        }

        @Test
        void shouldConfirmTrip() {
            // Arrange
            trip.setId(UUID.randomUUID());
            trip.setIsConfirmed(false);
            when(repository.save(any(Trip.class))).thenReturn(trip);

            // Act
            var confirmedTrip = service.confirmTrip(trip);

            // Assert
            assertNotNull(confirmedTrip);
            assertTrue(confirmedTrip.getIsConfirmed());
            verify(repository, times(1)).save(trip);
        }
    }
}