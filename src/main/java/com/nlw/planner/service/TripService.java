package com.nlw.planner.service;

import com.nlw.planner.dto.TripRequestPayload;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TripService {
    @Autowired
    private TripRepository repository;

    public TripResponseDTO registerTrip(TripRequestPayload payload) {
        var newTrip = new Trip(payload);

        repository.save(newTrip);

        return new TripResponseDTO(newTrip.getId());
    }

    public Trip updateTrip(TripRequestPayload payload, Trip rawTrip) {
        rawTrip.setStarsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
        rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
        rawTrip.setDestination(payload.destination());

        this.repository.save(rawTrip);

        return rawTrip;
    }
}
