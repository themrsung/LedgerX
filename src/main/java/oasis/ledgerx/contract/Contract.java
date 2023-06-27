package oasis.ledgerx.contract;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.stack.asset.AssetStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A promise of delivery on a specified date
 * Contracts are stored centrally
 * Date can be null for perpetual contracts
 */
public interface Contract {
    /**
     * Gets the unique symbol of this contract
     */
    @Nonnull
    String getSymbol();

    /**
     * Gets the buyer of this contract
     */
    @Nonnull
    Actor getBuyer();

    /**
     * Gets the seller of this contract
     */
    @Nonnull
    Actor getSeller();

    /**
     * Gets the asset that should be delivered when expired/exercised
     */
    @Nonnull
    AssetStack getDelivery();

    /**
     * Gets the expiry of this contract
     */
    @Nullable
    DateTime getExpiry();

    /**
     * Gets the type of this contract
     */
    @Nonnull
    ContractType getType();
}
