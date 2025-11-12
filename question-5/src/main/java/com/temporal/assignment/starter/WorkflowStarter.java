package com.temporal.assignment.starter;

import com.temporal.assignment.workflow.WorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class WorkflowStarter {


    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);


        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("HELLO_WORLD_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);

        factory.start();
        System.out.println("👷 Worker started. Listening on task queue: HELLO_WORLD_TASK_QUEUE");
    }
}
