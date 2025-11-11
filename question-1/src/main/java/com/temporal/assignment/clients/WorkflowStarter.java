package com.temporal.assignment.clients;

import com.temporal.assignment.workflows.WorkflowInf;
import com.temporal.assignment.workers.FactoryStarter;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class WorkflowStarter {


    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);


        startWorkflow(client , "HelloWorkflow-Alice" , "Alice");
        startWorkflow(client , "HelloWorkflow-bob" , "Bob");


    }
    public static void startWorkflow(WorkflowClient client , String workflowId , String name){
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(FactoryStarter.TASK_QUEUE)
                .setWorkflowId(workflowId)
                .build();

        WorkflowInf workflow = client.newWorkflowStub(WorkflowInf.class, options);

        WorkflowClient.start(workflow::sayHello, name);

        System.out.println("Started workflow: " + workflowId + " for "+name);
    }
}
