package com.temporal.assignment.workflows;

import io.temporal.workflow.Workflow;



public class WorkflowImpl implements WorkflowInf{


    @Override
    public String sayHello(String name) {

        for (int i = 0; i < 5; i++) {
            Workflow.sleep(5000); // wait for 5 seconds for each iteration
            System.out.println(String.format("processing %s step for: %s", i, name));
        }
        String result = "Hello "+name+" from Temporal!";
        System.out.println(String.format("Workflow completed for : %s", name));
        return result;
    }
}
