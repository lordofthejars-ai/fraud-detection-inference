package org.acme;

public record TransactionDetails(float distanceFromLastTransaction, float ratioToMedianPrice, boolean usedChip,
                                    boolean usedPinNumber, boolean onlineOrder) {

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
