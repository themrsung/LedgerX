package oasis.ledgerx.stack.contract;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.stack.asset.AssetStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ContractStack {
    /**
     * Gets the contract this stack is holding
     */
    @Nonnull
    Contract getContract();

    /**
     * Gets expiry of this contract
     */
    @Nullable
    default DateTime getExpiry() {
        return getContract().getExpiry();
    }

    /**
     * Gets the delivery of this contract
     */
    @Nonnull
    default AssetStack getDelivery() {
        return getContract().getDelivery();
    }

    /**
     * Gets the number of contracts this stack is holding
     */
    @Nonnegative
    long getQuantity();

    /**
     * Sets the number of contracts this stack is holding
     * @param quantity New quantity of stack
     * @throws IllegalArgumentException When negative quantity is provided
     */
    void setQuantity(@Nonnegative long quantity) throws IllegalArgumentException;

    /**
     * Adds quantity to this stack
     * @param delta Amount to add
     * @throws IllegalArgumentException When quantity after change is negative
     */
    default void addQuantity(@Nonnegative long delta) throws IllegalArgumentException {
        long before = getQuantity();
        long after = before + delta;

        try {
            setQuantity(after);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes quantity from this stack
     * @param delta Amount to subtract
     * @throws IllegalArgumentException When quantity after change is negative
     */
    default void removeQuantity(@Nonnegative long delta) throws IllegalArgumentException {
        long before = getQuantity();
        long after = before - delta;

        try {
            setQuantity(after);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets the symbol of this contract
     */
    @Nonnull
    default String getSymbol() {
        return getContract().getSymbol();
    }

    /**
     * Gets the type of this contract
     */
    @Nonnull
    default ContractType getContractType() {
        return getContract().getType();
    }

    /**
     * Gets the buyer of this contract
     */
    @Nonnull
    default Actor getBuyer() {
        return getContract().getBuyer();
    }

    /**
     * Gets the seller of this contract
     */
    @Nonnull
    default Actor getSeller() {
        return getContract().getSeller();
    }
}
