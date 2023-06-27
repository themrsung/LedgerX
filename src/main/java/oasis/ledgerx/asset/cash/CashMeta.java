package oasis.ledgerx.asset.cash;

import oasis.ledgerx.asset.AssetMeta;

import javax.annotation.Nonnegative;

public final class CashMeta implements AssetMeta {
    public CashMeta() {
        this.fractionQuantity = 0d;
    }

    public CashMeta(@Nonnegative double fractionQuantity) {
        this.fractionQuantity = fractionQuantity;
    }

    public CashMeta(CashMeta other) {
        this.fractionQuantity = other.fractionQuantity;
    }

    @Nonnegative
    private double fractionQuantity;

    @Override
    public boolean isFractional() {
        return true;
    }

    @Override
    @Nonnegative
    public double getFractionalQuantity() {
        return fractionQuantity;
    }

    public void setFractionQuantity(@Nonnegative double fractionQuantity) throws IllegalArgumentException {
        if (fractionQuantity <= 0d) throw new IllegalArgumentException();
        this.fractionQuantity = fractionQuantity;
    }
}
