package com.temporal.assignment.workflow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreditWorkflow {

    @WorkflowMethod
    public void run(String userId);

    @SignalMethod
    public void updateStatus(String status);
}
