package com.nlw.planner.controller;

import com.nlw.planner.dto.ParticipantRequestPayload;
import com.nlw.planner.model.Participant;
import com.nlw.planner.repository.PartipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private PartipantRepository repository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Participant> participant = this.repository.findById(id);

        if (participant.isPresent()) {
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(payload.name());

            this.repository.save(rawParticipant);

            return ResponseEntity.ok(rawParticipant);
        }

        return ResponseEntity.notFound().build();
    }
}
