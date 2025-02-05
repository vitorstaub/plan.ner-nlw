package com.nlw.planner.infra;

import com.nlw.planner.exceptions.InvalidTripPeriod;
import com.nlw.planner.exceptions.ParticipantNotFound;
import com.nlw.planner.exceptions.TripNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TripNotFound.class)
    private ResponseEntity<RestMessageError> tripNotFoundHandler(TripNotFound exception) {
        var response = new RestMessageError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ParticipantNotFound.class)
    private ResponseEntity<RestMessageError> participantNotFoundHandler(ParticipantNotFound exception) {
        var response = new RestMessageError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DateTimeParseException.class)
    private ResponseEntity<RestMessageError> dateTimeParseHandler(DateTimeParseException exception) {
        var response = new RestMessageError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidTripPeriod.class)
    private ResponseEntity<RestMessageError> invalidTripPeriodHandler(InvalidTripPeriod exception) {
        var response = new RestMessageError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestMessageError> argumentMismatchHandler(MethodArgumentTypeMismatchException exception) {
        var response = new RestMessageError(HttpStatus.BAD_REQUEST.value(), "Invalid Id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
