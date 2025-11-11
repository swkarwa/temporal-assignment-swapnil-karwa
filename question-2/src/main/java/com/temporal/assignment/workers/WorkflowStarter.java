package com.temporal.assignment.workers;

import com.temporal.assignment.workflows.PaymentWorkflowInf;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import java.time.Duration;


public class WorkflowStarter {

    public static void main(String[] args) {
        System.out.println("[Client] Starting workflow");

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        String workflowId = "PaymentWorkflow-" + System.currentTimeMillis();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(WorkerStarter.TASK_QUEUE)
                .setWorkflowId(workflowId)
                .setWorkflowRunTimeout(Duration.ofSeconds(12))
                .build();

        PaymentWorkflowInf workflow = client.newWorkflowStub(PaymentWorkflowInf.class, options);
        String result = workflow.processPayment("PAY-" + workflowId);
        System.out.println("Final workflow result: " + result);


    }

}
