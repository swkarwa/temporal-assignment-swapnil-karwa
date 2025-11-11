package com.temporal.assignment.workers;

import com.temporal.assignment.activity.CreditCheckActivitiesImpl;
import com.temporal.assignment.workflow.CreditWorkflow;
import com.temporal.assignment.workflow.CreditWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class Starter {

    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("CREDIT_TASK_QUEUE");

        worker.registerWorkflowImplementationTypes(CreditWorkflowImpl.class);
        worker.registerActivitiesImplementations(new CreditCheckActivitiesImpl());

        factory.start();

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("CREDIT_TASK_QUEUE")
                .setWorkflowId("credit-workflow-" + System.currentTimeMillis())
                .build();

        CreditWorkflow workflow = client.newWorkflowStub(CreditWorkflow.class, options);


        WorkflowClient.start(workflow::run, "user-123");

        System.out.println("Workflow started. You can send signals from another client to update status.");
    }
}
