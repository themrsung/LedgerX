package oasis.ledgerx.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.cash.Cash;
import oasis.ledgerx.stack.asset.CashStack;

import javax.annotation.Nonnull;

/**
 * An entity capable of providing fair prices to assets
 * Prices are used for exercising options
 */
public interface PriceProvider {
    /**
     * Gets the asset of which price is provided for
     */
    @Nonnull
    @JsonProperty("asset")
    Asset getAsset();

    /**
     * Gets the current fair value of the asset traded
     * Returns 0 if no trades were ever made
     */
    @Nonnull
    @JsonProperty("price")
    CashStack getPrice();

    /**
     * Gets the currency of this market
     */
    @Nonnull
    @JsonIgnore
    default Cash getCurrency() {
        return getPrice().getAsset();
    }
}
