package com.temporal.assignment.clients;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowExecutionStatus;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionRequest;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionResponse;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class WorkflowStatusChecker {

    public static void main(String[] args) {
        // Pass the workflowId from WorkflowStarter
        String[] workflows = new String[]{
                "HelloWorkflow-Alice" , "HelloWorkflow-bob" , "NonexistingWorkflow"
        };

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        for(String workflowId : workflows){
            checkWorkflowStatus(service , workflowId);
        }

    }

    public static void checkWorkflowStatus(WorkflowServiceStubs service , String workflowId){

        try {
            DescribeWorkflowExecutionRequest request = DescribeWorkflowExecutionRequest.newBuilder()
                    .setNamespace("default")
                    .setExecution(
                            WorkflowExecution.newBuilder()
                                    .setWorkflowId(workflowId)
                                    .build()
                    )
                    .build();

            DescribeWorkflowExecutionResponse response =
                    service.blockingStub().describeWorkflowExecution(request);

            WorkflowExecutionStatus status =
                    response.getWorkflowExecutionInfo().getStatus();

            System.out.println("Workflow ID: " + workflowId);
            System.out.println("Current Status: " + status.name());

        } catch (io.grpc.StatusRuntimeException e) {
            if (e.getStatus().getCode().name().equals("NOT_FOUND")) {
                System.out.println("Workflow not found: " + workflowId);
            } else {
                e.printStackTrace();
            }
        }
    }
}
