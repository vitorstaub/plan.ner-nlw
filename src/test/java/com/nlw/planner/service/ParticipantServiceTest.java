package com.nlw.planner.service;

import com.nlw.planner.dto.ParticipantDTO;
import com.nlw.planner.dto.ParticipantResponseDTO;
import com.nlw.planner.factory.TripPayloadFactory;
import com.nlw.planner.model.Participant;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.PartipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private PartipantRepository repository;

    @InjectMocks
    private ParticipantService service;

    @Nested
    class RegisterParticipantsToEventTests {
        private List<String> participantsToInvite;
        private Trip trip;

        @BeforeEach
        void setUp() {
            participantsToInvite = List.of("email1@example.com", "email2@example.com");
            trip = new Trip(TripPayloadFactory.getPayload());
        }

        @Test
        void shouldRegisterParticipantsToEvent() {
            // Arrange
            List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

            // Act
            service.registerParticipantsToEvent(participantsToInvite, trip);

            // Assert
            ArgumentCaptor<List<Participant>> captor = ArgumentCaptor.forClass(List.class);
            verify(repository, times(1)).saveAll(captor.capture());

            List<Participant> capturedParticipants = captor.getValue();

            assertEquals(participantsToInvite.size(), capturedParticipants.size());
            for (int i = 0; i < participantsToInvite.size(); i++) {
                assertEquals(participantsToInvite.get(i), capturedParticipants.get(i).getEmail());
                assertEquals(trip, capturedParticipants.get(i).getTrip());
            }
        }
    }

    @Nested
    class RegisterParticipantToEventTests {
        private String email;
        private Trip trip;

        @BeforeEach
        void setUp() {
            email = "email@example.com";
            trip = new Trip();
            // Configure trip as needed
        }

        @Test
        void shouldRegisterParticipantToEvent() {
            // Arrange
            Participant newParticipant = new Participant(email, trip);
            when(repository.save(any(Participant.class))).thenReturn(newParticipant);

            // Act
            ParticipantResponseDTO response = service.registerParticipantToEvent(email, trip);

            // Assert
            assertNotNull(response);
            assertEquals(newParticipant.getId(), response.id());
            verify(repository, times(1)).save(any(Participant.class));
        }
    }

    @Nested
    class GetAllParticipantsFromEventTests {
        private UUID tripId;
        private List<Participant> participants;

        @BeforeEach
        void setUp() {
            tripId = UUID.randomUUID();
            participants = List.of(new Participant("email1@example.com", new Trip()), new Participant("email2@example.com", new Trip()));
        }

        @Test
        void shouldGetAllParticipantsFromEvent() {
            // Arrange
            when(repository.findByTripId(tripId)).thenReturn(participants);

            // Act
            List<ParticipantDTO> response = service.getAllParticipantsFromEvent(tripId);

            // Assert
            assertNotNull(response);
            assertEquals(participants.size(), response.size());
            verify(repository, times(1)).findByTripId(tripId);
        }
    }
}