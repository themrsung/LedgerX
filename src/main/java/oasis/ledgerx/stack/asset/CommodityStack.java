package oasis.ledgerx.stack.asset;

import oasis.ledgerx.asset.commodity.Commodity;
import oasis.ledgerx.asset.commodity.CommodityMeta;
import oasis.ledgerx.asset.stock.Stock;
import oasis.ledgerx.asset.stock.StockMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Stack of commodities
 * Fungible and integral
 */
public final class CommodityStack implements AssetStack, Serializable {
    public CommodityStack(Commodity asset, @Nonnegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
    }

    public CommodityStack(CommodityStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
    }

    private final Commodity asset;
    private long quantity;

    @Nonnull
    @Override
    public Commodity getAsset() {
        return new Commodity(asset);
    }

    /**
     * Sets the quantity of this stack
     * @param quantity Will be rounded if quantity is fractional
     * @return Quantity after change
     * @throws IllegalArgumentException When given quantity is negative
     */
    @Override
    public double setQuantity(double quantity) throws IllegalArgumentException {
        if (quantity < 0d) throw new IllegalArgumentException();
        this.quantity = (long) Math.round(quantity);

        return this.quantity;
    }

    @Override
    public long getIntegralQuantity() {
        return quantity;
    }

    @Nonnull
    @Override
    public CommodityMeta getMeta() {
        return getAsset().getMeta();
    }
}
