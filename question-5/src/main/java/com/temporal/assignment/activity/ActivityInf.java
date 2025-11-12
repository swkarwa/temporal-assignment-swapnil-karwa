package com.temporal.assignment.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ActivityInf {
    @ActivityMethod
    void runActivity();
}
