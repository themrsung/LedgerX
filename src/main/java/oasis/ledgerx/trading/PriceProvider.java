package oasis.ledgerx.trading;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.cash.Cash;
import oasis.ledgerx.stack.asset.CashStack;

/**
 * An entity capable of providing fair prices to assets
 * Prices are used for exercising options
 */
public interface PriceProvider {
    /**
     * Gets the asset of which price is provided for
     */
    Asset getAsset();

    /**
     * Gets the current fair value of the asset traded
     * Returns 0 if no trades were ever made
     */
    CashStack getPrice();

    /**
     * Gets the currency of this market
     */
    default Cash getCurrency() {
        return getPrice().getAsset();
    }
}
