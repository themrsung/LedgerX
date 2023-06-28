package oasis.ledgerx.stack.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.stack.asset.AssetStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "contractType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BondStack.class, name = "BOND"),
        @JsonSubTypes.Type(value = FuturesStack.class, name = "FUTURES"),
        @JsonSubTypes.Type(value = OptionStack.class, name = "OPTION")
})
public interface ContractStack {
    /**
     * Gets the contract this stack is holding
     */
    @JsonProperty("contract")
    @Nonnull
    Contract getContract();

    /**
     * Gets expiry of this contract
     */
    @JsonProperty("expiry")
    @Nullable
    default DateTime getExpiry() {
        return getContract().getExpiry();
    }

    /**
     * Gets the delivery of this contract
     */
    @JsonIgnore
    @Nonnull
    default AssetStack getDelivery() {
        return getContract().getDelivery();
    }

    /**
     * Gets the number of contracts this stack is holding
     */
    @JsonProperty("quantity")
    @Nonnegative
    long getQuantity();

    /**
     * Sets the number of contracts this stack is holding
     * @param quantity New quantity of stack
     * @throws IllegalArgumentException When negative quantity is provided
     */
    @JsonIgnore
    void setQuantity(@Nonnegative long quantity) throws IllegalArgumentException;

    /**
     * Adds quantity to this stack
     * @param delta Amount to add
     * @throws IllegalArgumentException When quantity after change is negative
     */
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    default String getSymbol() {
        return getContract().getSymbol();
    }

    /**
     * Gets the type of this contract
     */
    @Nonnull
    @JsonIgnore
    default ContractType getContractType() {
        return getContract().getType();
    }

    /**
     * Gets the buyer of this contract
     */
    @Nonnull
    @JsonProperty("buyer")
    default Actor getBuyer() {
        return getContract().getBuyer();
    }

    /**
     * Gets the seller of this contract
     */
    @Nonnull
    @JsonProperty("seller")
    default Actor getSeller() {
        return getContract().getSeller();
    }
}
