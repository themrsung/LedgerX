package oasis.ledgerx.asset.cash;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * A fungible, fractional, and liquid asset
 */
public final class Cash implements Asset, Serializable {
    public Cash(@Nonnull String symbol, @Nonnull CashMeta meta) {
        this.symbol = symbol;
        this.meta = meta;
    }

    public Cash(Cash other) {
        this.symbol = other.symbol;
        this.meta = other.meta;
    }

    public Cash() {
        this.symbol = "";
        this.meta = new CashMeta();
    }

    @JsonProperty("symbol")
    @Nonnull
    private final String symbol;

    @JsonProperty("meta")
    @Nonnull
    private CashMeta meta;

    /**
     * Gets the currency of this cash
     */
    @Nonnull
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets a copy of metadata
     */
    @Nonnull
    @Override
    public CashMeta getMeta() {
        return new CashMeta(meta);
    }

    /**
     * Sets the asset metadata
     * @param meta Provide a CashMeta
     * @throws IllegalArgumentException When given meta is incompatible
     */
    @Override
    public void setMeta(@Nonnull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CashMeta)) throw new IllegalArgumentException();
        this.meta = (CashMeta) meta;
    }

    /**
     * Gets type of this asset
     */
    @Nonnull
    @Override
    public AssetType getType() {
        return AssetType.CASH;
    }

    /**
     * Gets an identical copy of this asset
     */
    @Override
    public Cash copy() {
        return new Cash(this);
    }
}
