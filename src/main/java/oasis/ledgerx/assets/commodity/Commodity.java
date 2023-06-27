package oasis.ledgerx.assets.commodity;

import oasis.ledgerx.actors.Actor;
import oasis.ledgerx.assets.Asset;
import oasis.ledgerx.assets.AssetMeta;
import oasis.ledgerx.assets.AssetType;
import oasis.ledgerx.assets.stock.StockMeta;

import javax.annotation.Nonnull;

/**
 * Commodity
 * A fungible and integral asset
 */
public final class Commodity implements Asset {
    public Commodity(@Nonnull String symbol, @Nonnull Actor owner, @Nonnull CommodityMeta meta) {
        this.symbol = symbol;
        this.owner = owner;
        this.meta = meta;
    }

    public Commodity(Commodity other) {
        this.symbol = other.symbol;
        this.owner = other.owner;
        this.meta = other.meta;
    }

    @Nonnull
    private final String symbol;
    @Nonnull
    private final Actor owner;
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
     * Gets the actual owner
     */
    @Nonnull
    @Override
    public Actor getOwner() {
        return owner;
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
}
