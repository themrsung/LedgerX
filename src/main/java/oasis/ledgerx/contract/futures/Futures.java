package oasis.ledgerx.contract.futures;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.annotation.NonCash;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.stack.asset.AssetStack;
import oasis.ledgerx.stack.asset.CashStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Futures implements Contract {
    public Futures() {
        this.symbol = "";
        this.buyer = new EconomicActor();
        this.seller = new EconomicActor();
        this.delivery = null;
        this.expiry = new DateTime();
    }

    /**
     * Creates a new futures contract
     * @param symbol Unique symbol of this contract
     * @param buyer Buyer of this contract
     * @param seller Seller of this contract
     * @param delivery Asset to be delivered (Cannot be cash)
     * @param expiry Expiration date of this contract
     * @throws IllegalArgumentException When CashStack is given as delivery
     */
    public Futures(String symbol, Actor buyer, Actor seller, @NonCash AssetStack delivery, @Nullable DateTime expiry) throws IllegalArgumentException {
        if (delivery instanceof CashStack) {
            throw new IllegalArgumentException();
        }

        this.symbol = symbol;
        this.buyer = buyer;
        this.seller = seller;
        this.delivery = delivery;
        this.expiry = expiry;
    }

    public Futures(Futures other) {
        this.symbol = other.symbol;
        this.buyer = other.buyer;
        this.seller = other.seller;
        this.delivery = other.delivery;
        this.expiry = other.expiry;
    }

    @JsonProperty("symbol")
    private final String symbol;
    @JsonProperty("buyer")
    private final Actor buyer;
    @JsonProperty("seller")
    private final Actor seller;
    @JsonProperty("delivery")
    @NonCash
    private final AssetStack delivery;
    @JsonProperty("expiry")
    @Nullable
    private final DateTime expiry;

    @Nonnull
    @Override
    public String getSymbol() {
        return symbol;
    }

    @Nonnull
    @Override
    public Actor getBuyer() {
        return buyer;
    }

    @Nonnull
    @Override
    public Actor getSeller() {
        return seller;
    }

    @Nonnull
    @NonCash
    @Override
    public AssetStack getDelivery() {
        assert delivery != null;
        return delivery;
    }

    @Nullable
    @Override
    public DateTime getExpiry() {
        if (expiry == null) return null;
        return new DateTime(expiry);
    }

    @Nonnull
    @Override
    public ContractType getType() {
        return ContractType.FUTURES;
    }
}
