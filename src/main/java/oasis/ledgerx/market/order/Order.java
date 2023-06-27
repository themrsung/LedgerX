package oasis.ledgerx.market.order;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.exception.IncompatibleOrderTypeException;
import oasis.ledgerx.inventory.asset.CashStack;
import oasis.ledgerx.state.LedgerState;
import org.joda.time.DateTime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 *
 */
public interface Order {
    /**
     * Gets the unique ID of this order
     */
    UUID getUniqueId();

    /**
     * Gets the sender of the order
     */
    Actor getSender();

    /**
     * Gets the type of this order
     */
    OrderType getOrderType();

    /**
     * Changes the type of this order
     * @param type New type
     * @throws IncompatibleOrderTypeException When order cannot be changed to the new type
     */
    void setOrderType(OrderType type) throws IncompatibleOrderTypeException;

    /**
     * Whether this is a buy order or not
     */
    default boolean isBuy() {
        return getOrderType().isBuy();
    }

    /**
     * Gets the time of order creation
     */
    DateTime getTime();

    /**
     * Gets the bid/ask price of this order
     */
    @Nonnull
    CashStack getPrice();

    /**
     * Gets the quantity of this order
     */
    long getQuantity();

    /**
     * Gets the collateral used to submit this order
     * Set to null for collateral-less orders
     */
    @Nullable
    Contract getCollateral();

    /**
     * Called on order submission
     * Handles contract registration by default
     * @param state Current working state of LedgerX
     */
    void onSubmitted(LedgerState state);

    /**
     * Called on order fulfillment
     * Handles internal order processing
     * @param state Current working state of LedgerX
     * @param price Price of fulfillment
     * @param quantity Quantity of fulfillment
     */
    void onFulfilled(LedgerState state, @Nonnull CashStack price, @Nonnegative long quantity);

    /**
     * Called on order cancellation
     * Safely cancels the order
     * @param state Current working state of LedgerX
     */
    void onCancelled(LedgerState state);
}
