package com.nlw.planner.service;

import com.nlw.planner.dto.LinkDTO;
import com.nlw.planner.dto.LinkRequestPayload;
import com.nlw.planner.dto.LinkResponseDTO;
import com.nlw.planner.model.Link;
import com.nlw.planner.repository.LinkRepository;
import com.nlw.planner.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkResponseDTO registerLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);

        this.repository.save(newLink);

        return new LinkResponseDTO(newLink.getId());
    }

    public List<LinkDTO> getAllLinksFromId(UUID tripId) {
        return this.repository.findByTripId(tripId).stream().map(link -> new LinkDTO(link.getId(),link.getTitle(), link.getUrl())).toList();
    }
}
