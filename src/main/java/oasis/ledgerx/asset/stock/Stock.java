package oasis.ledgerx.asset.stock;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Stocks
 * A fungible and integral asset
 */
public final class Stock implements Asset, Serializable {
    public Stock(@Nonnull String symbol, @Nonnull StockMeta meta) {
        this.symbol = symbol;
        this.meta = meta;
    }

    public Stock(Stock other) {
        this.symbol = other.symbol;
        this.meta = other.meta;
    }

    public Stock() {
        this.symbol = "";
        this.meta = new StockMeta();
    }

    @Nonnull
    private final String symbol;
    @Nonnull
    private StockMeta meta;

    @Nonnull
    @Override
    public String getSymbol() {
        return symbol;
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

    /**
     * Gets type of this asset
     */
    @Nonnull
    @Override
    public AssetType getType() {
        return AssetType.STOCK;
    }

    /**
     * Gets an identical copy of this asset
     */
    @Override
    public Stock copy() {
        return new Stock(this);
    }
}
