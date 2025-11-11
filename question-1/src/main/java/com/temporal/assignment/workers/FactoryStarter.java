package com.temporal.assignment.workers;

import com.temporal.assignment.workflows.WorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class FactoryStarter {

    public static final String TASK_QUEUE = "HELLO_TASK_QUEUE";

    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);

        factory.start();
        System.out.println("Worker started for task queue: " + TASK_QUEUE);

    }
}
