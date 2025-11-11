package com.temporal.assignment.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface CreditCheckActivities {
    @ActivityMethod
    String performCreditCheck(String userId);
}
