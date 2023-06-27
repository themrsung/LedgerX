package oasis.ledgerx.inventory.contract;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface ContractStack {
    /**
     * Gets the contract this stack is holding
     */
    @Nonnull
    Contract getContract();

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
