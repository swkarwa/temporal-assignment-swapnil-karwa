package com.temporal.assignment.workers;

import com.temporal.assignment.activities.PaymentActivitiesImpl;
import com.temporal.assignment.workflows.PaymentWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;



public class WorkerStarter {
    public static final String TASK_QUEUE = "PAYMENT_TASK_QUEUE";

    public static void main(String[] args) {

        System.out.println("[Worker] starting temporal worker");

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(PaymentWorkflowImpl.class);
        worker.registerActivitiesImplementations(new PaymentActivitiesImpl());

        factory.start();
        System.out.println("[Worker] Worker started and waiting for tasks....");

    }
}
