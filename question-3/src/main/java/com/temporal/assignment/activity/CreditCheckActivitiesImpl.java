package com.temporal.assignment.activity;

import io.temporal.failure.ApplicationFailure;

public class CreditCheckActivitiesImpl implements CreditCheckActivities{


    @Override
    public String performCreditCheck(String userId) {
        System.out.println("Running credit check for user: " + userId);

        // Simulate random business outcome
        double random = Math.random();
        if (random < 0.4) {
            System.out.println("Credit check SUCCESS");
            return "SUCCESS";
        } else if (random < 0.7) {
            System.out.println("Credit check PROCESSING");
            return "PROCESSING";
        } else {
            System.out.println("Credit check FAILED");
            // Throwing exception triggers Temporal retry (technical + business)
            throw ApplicationFailure.newFailure("Business retry: FAILED", "BusinessFailure");
        }
    }
}
