package org.acme;

public class TransactionDetails {

    public float distanceFromLastTransaction;
    public float ratioToMedianPrice;
    public boolean usedChip;
    public boolean usedPinNumber;
    public boolean onlineOrder;

    public TransactionDetails(float distanceFromLastTransaction, float ratioToMedianPrice, boolean usedChip, boolean usedPinNumber, boolean onlineOrder) {
        this.distanceFromLastTransaction = distanceFromLastTransaction;
        this.ratioToMedianPrice = ratioToMedianPrice;
        this.usedChip = usedChip;
        this.usedPinNumber = usedPinNumber;
        this.onlineOrder = onlineOrder;
    }

    public float[] toFloatRepresentation() {
        return new float[] {distanceFromLastTransaction,
                ratioToMedianPrice,
                usedChipAsFloat(),
                usedPinNumberAsFloat(),
                onlineOrderAsFloat()};
    }

    public float usedChipAsFloat() {
        return usedChip ? 1.0f : 0.0f;
    }

    public float usedPinNumberAsFloat() {
        return usedPinNumber ? 1.0f : 0.0f;
    }

    public float onlineOrderAsFloat() {
        return onlineOrder ? 1.0f : 0.0f;
    }

}
