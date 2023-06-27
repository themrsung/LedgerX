package oasis.ledgerx.inventory.asset;

import oasis.ledgerx.asset.stock.Stock;
import oasis.ledgerx.asset.stock.StockMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Stack of commodities
 * Fungible and integral
 */
public final class CommodityStack implements AssetStack {
    public CommodityStack(Stock asset, @Nonnegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
    }

    public CommodityStack(CommodityStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
    }

    private final Stock asset;
    private long quantity;

    @Nonnull
    @Override
    public Stock getAsset() {
        return new Stock(asset);
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
    public StockMeta getMeta() {
        return getAsset().getMeta();
    }
}
