package com.temporal.assignment.workflow;

import com.temporal.assignment.activity.CreditCheckActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ApplicationFailure;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class CreditWorkflowImpl implements CreditWorkflow {


    private String latestStatus = "PENDING";

    private final CreditCheckActivities activities =
            Workflow.newActivityStub(CreditCheckActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(3)
                                    .build())
                            .build());

    @Override
    public void run(String userId) {
        System.out.println("Workflow started for user: " + userId);

        String status = activities.performCreditCheck(userId);
        System.out.println("Credit check returned: " + status);

        if ("PROCESSING".equals(status)) {
            latestStatus = "PROCESSING";
            System.out.println("Waiting for external signal for up to 30 seconds...");
            boolean signalReceived = Workflow.await(Duration.ofSeconds(30),
                    () -> !"PROCESSING".equals(latestStatus));

            if (!signalReceived) {
                System.out.println("Timed out waiting for external signal.");
                throw Workflow.wrap(ApplicationFailure.newFailure(
                        "Timeout: no signal received in 30 seconds",
                        "TimeoutError"));
            }

            System.out.println("Signal received with status: " + latestStatus);
            status = latestStatus;
        }

        if ("PENDING".equals(status)) {
            System.out.println("Status is PENDING — retrying credit check...");
            status = activities.performCreditCheck(userId);
        }

        if ("SUCCESS".equals(status)) {
            System.out.println("Workflow completed successfully!");
            return;
        }

        if ("FAILED".equals(status)) {
            throw Workflow.wrap(ApplicationFailure.newFailure(
                    "Business failure after retries",
                    "BusinessError"));
        }

        System.out.println("Workflow ended with final status: " + status);
    }

    @Override
    public void updateStatus(String status) {
        System.out.println("Signal received — updating status to: " + status);
        this.latestStatus = status;
    }
}
