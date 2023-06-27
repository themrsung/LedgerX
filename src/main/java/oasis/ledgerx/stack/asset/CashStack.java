package oasis.ledgerx.stack.asset;

import oasis.ledgerx.asset.cash.Cash;
import oasis.ledgerx.asset.cash.CashMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Stack of cash
 * Fungible and fractional
 */
public final class CashStack implements AssetStack {
    public CashStack(Cash asset, @Nonnegative double quantity) {
        this.asset = asset;
        this.setQuantity(quantity);
    }

    public CashStack(CashStack other) {
        this.asset = other.asset;
        this.integralQuantity = other.integralQuantity;
    }

    private final Cash asset;
    private long integralQuantity;

    @Nonnull
    @Override
    public Cash getAsset() {
        return new Cash(asset);
    }

    /**
     * Sets the quantity of this tack
     * @param quantity Will be rounded if asset is not fractional
     * @return Quantity after change
     * @throws IllegalArgumentException When given quantity is negative
     */
    @Override
    public double setQuantity(double quantity) throws IllegalArgumentException {
        if (quantity < 0d) throw new IllegalArgumentException();

        long integralPart = (long) Math.floor(quantity);
        double fractionalPart = quantity % 1;

        this.integralQuantity = integralPart;
        CashMeta meta = getMeta();

        return getQuantity();
    }


    @Override
    public long getIntegralQuantity() {
        return integralQuantity;
    }

    @Nonnull
    @Override
    public CashMeta getMeta() {
        return getAsset().getMeta();
    }
}
