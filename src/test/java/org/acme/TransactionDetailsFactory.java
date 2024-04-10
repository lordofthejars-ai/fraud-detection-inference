package org.acme;

public class TransactionDetailsFactory {

    public static TransactionDetails getNoneFraudTransaction() {
        return new TransactionDetails(0.3111400080477545f, 1.9459399775518593f, true, true, false);
    }

    public static TransactionDetails getFraudTransaction() {
        return new TransactionDetails(0.3111400080477545f, 1.9459399775518593f, true, false, false);
    }
}
