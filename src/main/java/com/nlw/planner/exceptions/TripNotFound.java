package com.nlw.planner.exceptions;

public class TripNotFound extends RuntimeException{
    public TripNotFound() {
        super("Trip Not Found");
    }
    public TripNotFound(String message) {
        super(message);
    }
}
