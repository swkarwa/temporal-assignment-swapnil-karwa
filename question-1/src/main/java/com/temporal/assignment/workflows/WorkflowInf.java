package com.temporal.assignment.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowInf {

    @WorkflowMethod
    String sayHello(String name);
}
