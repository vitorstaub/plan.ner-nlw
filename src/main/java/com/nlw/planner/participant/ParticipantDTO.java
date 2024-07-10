package com.nlw.planner.participant;

import java.util.UUID;

public record ParticipantDTO(UUID id, String name, String email, Boolean isConfirmed) {
}
