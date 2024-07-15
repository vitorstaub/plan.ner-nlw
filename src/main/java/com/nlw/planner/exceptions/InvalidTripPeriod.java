package com.nlw.planner.exceptions;

public class InvalidTripPeriod extends RuntimeException {
    public InvalidTripPeriod() {
        super("Invalid Trip Period");
    }
    public InvalidTripPeriod(String message) {
        super(message);
    }
}
