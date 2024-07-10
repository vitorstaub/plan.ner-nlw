package com.nlw.planner.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartipantRepository extends JpaRepository<Participant, UUID> {
}
