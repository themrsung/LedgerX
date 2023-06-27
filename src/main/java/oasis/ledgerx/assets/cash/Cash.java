package oasis.ledgerx.assets.cash;

import oasis.ledgerx.actors.Actor;
import oasis.ledgerx.assets.Asset;
import oasis.ledgerx.assets.AssetMeta;
import oasis.ledgerx.assets.AssetType;

import javax.annotation.Nonnull;

/**
 * A fungible, fractional, and liquid asset
 */
public final class Cash implements Asset {
    public Cash(@Nonnull String symbol, @Nonnull Actor owner, @Nonnull CashMeta meta) {
        this.symbol = symbol;
        this.owner = owner;
        this.meta = meta;
    }

    public Cash(Cash other) {
        this.symbol = other.symbol;
        this.owner = other.owner;
        this.meta = other.meta;
    }

    @Nonnull
    private final String symbol;
    @Nonnull
    private final Actor owner;
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
     * Gets the actual owner
     */
    @Nonnull
    @Override
    public Actor getOwner() {
        return owner;
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
}
