package com.nlw.planner.exceptions;

public class ParticipantNotFound extends RuntimeException{
    public ParticipantNotFound() {
        super("Participant Not Found");
    }
    public ParticipantNotFound(String message) {
        super(message);
    }
}
