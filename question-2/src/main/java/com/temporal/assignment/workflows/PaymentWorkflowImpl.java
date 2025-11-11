package com.temporal.assignment.workflows;


import com.temporal.assignment.activities.PaymentActivitiesInf;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import java.time.Duration;


public class PaymentWorkflowImpl implements PaymentWorkflowInf {

    private PaymentActivitiesInf activities = Workflow.newActivityStub(PaymentActivitiesInf.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(9))
                    .build()

    );

    @Override
    public String processPayment(String transactionId) {

        System.out.println("[Workflow] started for transaction id :" + transactionId);
        Promise<String> resultPromise = Async.function(activities::callPaymentGateway, transactionId);

        boolean isPaymentDone = Workflow.await(
                Duration.ofSeconds(10),
                () -> resultPromise.isCompleted()
        );

        if (!isPaymentDone) {
            System.out.println("Workflow timeout hit for :" + transactionId);
            return "TIMEOUT";
        }

        String result = resultPromise.get();
        System.out.println("Workflow completed with result:" + result);
        return result;
    }
}
