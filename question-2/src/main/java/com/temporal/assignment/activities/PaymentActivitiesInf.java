package com.temporal.assignment.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentActivitiesInf {

    @ActivityMethod
    String callPaymentGateway(String transactionId);
}
