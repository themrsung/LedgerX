package oasis.ledgerx.asset.commodity;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;

import javax.annotation.Nonnull;

/**
 * Commodity
 * A fungible and integral asset
 */
public final class Commodity implements Asset {
    public Commodity(@Nonnull String symbol, @Nonnull CommodityMeta meta) {
        this.symbol = symbol;
        this.meta = meta;
    }

    public Commodity(Commodity other) {
        this.symbol = other.symbol;
        this.meta = other.meta;
    }

    @Nonnull
    private final String symbol;
    @Nonnull
    private CommodityMeta meta;

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
        return AssetType.STOCK;
    }

    /**
     * Gets a copy of this commodity's metadata
     */
    @Nonnull
    @Override
    public CommodityMeta getMeta() {
        return new CommodityMeta(meta);
    }

    /**
     * Sets the asset metadata
     * @param meta Provide a CommodityMeta
     * @throws IllegalArgumentException When given meta is incompatible
     */
    @Override
    public void setMeta(@Nonnull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CommodityMeta)) throw new IllegalArgumentException();
        this.meta = (CommodityMeta) meta;
    }

    /**
     * Gets an identical copy of this asset
     */
    @Override
    public Commodity copy() {
        return new Commodity(this);
    }
}