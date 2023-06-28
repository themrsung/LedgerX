package oasis.ledgerx.contract.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.trading.PriceProvider;
import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.exception.DifferentCurrencyException;
import oasis.ledgerx.stack.asset.AssetStack;
import oasis.ledgerx.stack.asset.CashStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A unilateral contract which gives the owner only the right to exercise
 * Perpetual options are not supported
 */
public final class Option implements Contract {
    public Option() {
        this.symbol = "";
        this.buyer = new EconomicActor();
        this.seller = new EconomicActor();
        this.delivery = null;
        this.expiry = new DateTime();

        this.optionType = null;
        this.market = new Market();
        this.exercisePrice = new CashStack();
    }

    public Option(String symbol, Actor buyer, Actor seller, AssetStack delivery, @Nonnull DateTime expiry, OptionType optionType, PriceProvider market, CashStack exercisePrice) {
        this.symbol = symbol;
        this.buyer = buyer;
        this.seller = seller;
        this.delivery = delivery;
        this.expiry = expiry;

        this.optionType = optionType;
        this.market = market;
        this.exercisePrice = exercisePrice;
    }

    public Option(Option other) {
        this.symbol = other.symbol;
        this.buyer = other.buyer;
        this.seller = other.seller;
        this.delivery = other.delivery;
        this.expiry = other.expiry;

        this.optionType = other.optionType;
        this.market = other.market;
        this.exercisePrice = other.exercisePrice;
    }

    @JsonProperty("symbol")
    private final String symbol;
    @JsonProperty("buyer")
    private final Actor buyer;
    @JsonProperty("seller")
    private final Actor seller;
    @JsonProperty("delivery")
    private final AssetStack delivery;
    @JsonProperty("expiry")
    @Nullable
    private final DateTime expiry;

    @JsonProperty("optionType")
    private final OptionType optionType;
    @JsonProperty("market")
    private final PriceProvider market;
    @JsonProperty("exercisePrice")
    private final CashStack exercisePrice;

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
    public AssetStack getDelivery() {
        return delivery;
    }

    @Nonnull
    @Override
    public DateTime getExpiry() {
        return new DateTime(expiry);
    }

    @JsonIgnore
    public OptionType getOptionType() {
        return optionType;
    }

    @JsonIgnore
    public boolean isCall() {
        return getOptionType().isCall();
    }

    @JsonIgnore
    public boolean isAmerican() {
        return getOptionType().isAmerican();
    }

    @JsonIgnore
    public PriceProvider getMarket() {
        return market;
    }

    @JsonIgnore
    public CashStack getExercisePrice() {
        return exercisePrice;
    }

    @JsonIgnore
    public boolean isExercisable() throws DifferentCurrencyException {
        if (!exercisePrice.getSymbol().equalsIgnoreCase(market.getPrice().getSymbol())) {
            throw new DifferentCurrencyException();
        }

        double e = exercisePrice.getQuantity();
        double p = market.getPrice().getQuantity();


        boolean notOutOfTheMoney = getOptionType().isCall() ? e >= p : e <= p;
        boolean exercisableNow = getOptionType().isAmerican() || getExpiry().isBeforeNow();

        return notOutOfTheMoney && exercisableNow;
    }

    @Nonnull
    @Override
    @JsonIgnore
    public ContractType getType() {
        return ContractType.OPTION;
    }
}
