package com.temporal.assignment.starter;

import com.temporal.assignment.activity.ActivityImpl;
import com.temporal.assignment.workflow.WorkflowImpl;
import com.temporal.assignment.workflow.WorkflowInf;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.schedules.*;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class ScheduleStarter {
    private static final String TASK_QUEUE = "HelloScheduleTaskQueue";
    private static final String WORKFLOW_ID = "HelloScheduleWorkflow";
    private static final String SCHEDULE_ID = "HelloSchedule";

    public static void main(String[] args) throws InterruptedException {

        // Connect to local Temporal service
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        // Start worker for workflows
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(WorkflowImpl.class);
        factory.start();


        ScheduleClient scheduleClient = ScheduleClient.newInstance(service);

        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setWorkflowId(WORKFLOW_ID)
                .setTaskQueue(TASK_QUEUE)
                .build();


        ScheduleActionStartWorkflow action = ScheduleActionStartWorkflow.newBuilder()
                .setWorkflowType(WorkflowInf.class)
                .setArguments("Scheduled execution triggered!")
                .setOptions(workflowOptions)
                .build();


        Schedule schedule = Schedule.newBuilder()
                .setAction(action)
                .setSpec(ScheduleSpec.newBuilder()
                        .setStartAt(Instant.now())
                        .setIntervals(Collections.singletonList(
                                new ScheduleIntervalSpec(Duration.ofMinutes(10))
                        ))
                        .build())
                .build();


        ScheduleHandle handle = scheduleClient.createSchedule(
                SCHEDULE_ID,
                schedule,
                ScheduleOptions.newBuilder().build()
        );

        System.out.println("✅ Schedule created: " + SCHEDULE_ID);


        handle.trigger();
        System.out.println("🚀 First run triggered immediately");


        while (true) {
            Thread.sleep(60_000);
        }
    }
}
