package oasis.ledgerx.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.bond.Bond;
import oasis.ledgerx.contract.futures.Futures;
import oasis.ledgerx.contract.option.Option;
import oasis.ledgerx.stack.asset.AssetStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A promise of delivery on a specified date
 * Contracts are stored centrally
 * Date can be null for perpetual contracts
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bond.class, name = "BOND"),
        @JsonSubTypes.Type(value = Futures.class, name = "FUTURES"),
        @JsonSubTypes.Type(value = Option.class, name = "OPTION")
})
public interface Contract {
    /**
     * Gets the unique symbol of this contract
     */
    @Nonnull
    @JsonProperty("symbol")
    String getSymbol();

    /**
     * Gets the buyer of this contract
     */
    @Nonnull
    @JsonProperty("buyer")
    Actor getBuyer();

    /**
     * Gets the seller of this contract
     */
    @Nonnull
    @JsonProperty("seller")
    Actor getSeller();

    /**
     * Gets the asset that should be delivered when expired/exercised
     */
    @Nonnull
    @JsonProperty("delivery")
    AssetStack getDelivery();

    /**
     * Gets the expiry of this contract
     */
    @Nullable
    @JsonProperty("expiry")
    DateTime getExpiry();

    /**
     * Gets the type of this contract
     */
    @JsonProperty("type")
    @Nonnull
    ContractType getType();
}
