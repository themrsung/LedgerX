package oasis.ledgerx.asset.cash;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;

import javax.annotation.Nonnull;

/**
 * A fungible, fractional, and liquid asset
 */
public final class Cash implements Asset {
    public Cash(@Nonnull String symbol, @Nonnull CashMeta meta) {
        this.symbol = symbol;
        this.meta = meta;
    }

    public Cash(Cash other) {
        this.symbol = other.symbol;
        this.meta = other.meta;
    }

    @Nonnull
    private final String symbol;
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
     * @return Cash
     */
    @Nonnull
    @Override
    public AssetType getType() {
        return AssetType.CASH;
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
     * Gets an identical copy of this asset
     */
    @Override
    public Cash copy() {
        return new Cash(this);
    }
}
