package com.temporal.assignment.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowInf {

    @WorkflowMethod
    public void run();
}
