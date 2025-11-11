package com.temporal.assignment.activities;



import java.util.Random;


public class PaymentActivitiesImpl implements PaymentActivitiesInf {

    public String callPaymentGateway(String transactionId) {
        try {
            /*
            * REST call to payment gateway can be called from here,
            * to mimic payment gateway , mentioning delay of 8 seconds
            * */
            int delay = 2 + new Random().nextInt(10); // 2 to 11 seconds
            Thread.sleep(delay * 1000L);
            System.out.println("[Activity] payment success for transaction id : " + transactionId);
            return "SUCCESS ("+delay+"'s)";

        } catch (InterruptedException e) {
            System.out.println("[Activity] payment interrupted or timed out.");
            return "CANCELLED";
        }

    }
}
