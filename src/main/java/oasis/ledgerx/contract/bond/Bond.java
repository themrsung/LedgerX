package oasis.ledgerx.contract.bond;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.stack.asset.CashStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Bond implements Contract {
    public Bond() {
        this.symbol = "";
        this.buyer = new EconomicActor();
        this.seller = new EconomicActor();
        this.delivery = new CashStack();
        this.expiry = new DateTime();
    }
    public Bond(String symbol, Actor buyer, Actor seller, CashStack delivery, @Nullable DateTime expiry) {
        this.symbol = symbol;
        this.buyer = buyer;
        this.seller = seller;
        this.delivery = delivery;
        this.expiry = expiry;
    }

    public Bond(Bond other) {
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
    private final CashStack delivery;

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
    @Override
    public CashStack getDelivery() {
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
        return ContractType.BOND;
    }
}
