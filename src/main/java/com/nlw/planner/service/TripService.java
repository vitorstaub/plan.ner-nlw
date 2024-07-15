package com.nlw.planner.service;

import com.nlw.planner.dto.TripRequestPayload;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    @Autowired
    private TripRepository repository;

    public TripResponseDTO registerTrip(TripRequestPayload payload) {
        var newTrip = new Trip(payload);

        repository.save(newTrip);

        return new TripResponseDTO(newTrip.getId());
    }
}
