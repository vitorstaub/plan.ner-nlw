package com.nlw.planner.dto;

import java.util.UUID;

public record ParticipantDTO(UUID id, String name, String email, Boolean isConfirmed) {
}
