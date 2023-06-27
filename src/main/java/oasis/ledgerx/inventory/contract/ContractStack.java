package oasis.ledgerx.inventory.contract;

import oasis.ledgerx.contracts.Contract;
import oasis.ledgerx.contracts.ContractType;

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
}
