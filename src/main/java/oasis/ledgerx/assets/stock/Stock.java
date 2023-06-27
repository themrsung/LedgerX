package oasis.ledgerx.assets.stock;

import oasis.ledgerx.actors.Actor;
import oasis.ledgerx.assets.Asset;
import oasis.ledgerx.assets.AssetMeta;
import oasis.ledgerx.assets.AssetType;

import javax.annotation.Nonnull;

/**
 * Stocks
 * A fungible and integral asset
 */
public final class Stock implements Asset {
    public Stock(@Nonnull String symbol, @Nonnull Actor owner, @Nonnull StockMeta meta) {
        this.symbol = symbol;
        this.owner = owner;
        this.meta = meta;
    }

    public Stock(Stock other) {
        this.symbol = other.symbol;
        this.owner = other.owner;
        this.meta = other.meta;
    }

    @Nonnull
    private final String symbol;
    @Nonnull
    private final Actor owner;
    @Nonnull
    private StockMeta meta;

    @Nonnull
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return Stock
     */
    @Nonnull
    @Override
    public AssetType getType() {
        return AssetType.COMMODITY;
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
     * Gets a copy of this stock's metadata
     */
    @Nonnull
    @Override
    public StockMeta getMeta() {
        return new StockMeta(meta);
    }

    /**
     * Sets the asset metadata
     * @param meta Provide a StockMeta
     * @throws IllegalArgumentException When given meta is incompatible
     */
    @Override
    public void setMeta(@Nonnull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof StockMeta)) throw new IllegalArgumentException();
        this.meta = (StockMeta) meta;
    }
}
