package oasis.ledgerx.asset.cash;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.Serializable;

public final class CashMeta implements AssetMeta, Serializable {
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

    @Nonnull
    @Override
    @JsonProperty("type")
    public AssetType getType() {
        return AssetType.CASH;
    }
}
