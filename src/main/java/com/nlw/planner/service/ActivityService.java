package com.nlw.planner.service;

import com.nlw.planner.model.Activity;
import com.nlw.planner.dto.ActivityDTO;
import com.nlw.planner.dto.ActivityRequestPayload;
import com.nlw.planner.dto.ActivityResponseDTO;
import com.nlw.planner.repository.ActivityRepository;
import com.nlw.planner.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponseDTO registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);

        this.repository.save(newActivity);

        return new ActivityResponseDTO(newActivity.getId());
    }

    public List<ActivityDTO> getAllActivitiesFromId(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(activity -> new ActivityDTO(activity.getId(),activity.getTitle(), activity.getOccursAt())).toList();
    }
}
