package com.temporal.assignment.workflow;

import io.temporal.workflow.Workflow;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WorkflowImpl implements WorkflowInf {



    @Override
    public void run() {

        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Workflow.getLogger(WorkflowImpl.class).info("Hello Temporal! Current time: {}", time);

    }
}
