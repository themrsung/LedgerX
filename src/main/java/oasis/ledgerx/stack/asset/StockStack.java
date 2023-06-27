package oasis.ledgerx.stack.asset;

import oasis.ledgerx.asset.stock.Stock;
import oasis.ledgerx.asset.stock.StockMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Stack of stocks
 * Fungible and integral
 */
public final class StockStack implements AssetStack, Serializable {
    public StockStack(Stock asset, @Nonnegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
    }

    public StockStack(StockStack other) {
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
