package com.nlw.planner.factory;

import com.nlw.planner.dto.TripRequestPayload;

import java.util.Arrays;
import java.util.List;

public class TripPayloadFactory {

    public static TripRequestPayload getPayload() {
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");

        return new TripRequestPayload("São Paulo",
                "2024-06-20T21:51:54.7342",
                "2024-06-30T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");
    }

    public static TripRequestPayload getPayloadWithInvalidPeriod() {
        List<String> emails = Arrays.asList("vitor@gmmail", "test@outlook");

        return new TripRequestPayload("São Paulo",
                "2024-06-30T21:51:54.7342",
                "2024-06-20T21:51:54.7342",
                emails,
                "staub@gmail",
                "staub");
    }
}
