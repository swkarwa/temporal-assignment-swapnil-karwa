package com.temporal.assignment.activity;

import io.temporal.failure.ApplicationFailure;

public class ActivityImpl implements ActivityInf {


    @Override
    public void runActivity() {
        System.out.println("Current time in mili seconds : " + System.currentTimeMillis());
    }
}
