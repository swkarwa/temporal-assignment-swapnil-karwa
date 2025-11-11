package com.temporal.assignment.workflows;


import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PaymentWorkflowInf {

    @WorkflowMethod
    public String processPayment(String transactionId);
}
