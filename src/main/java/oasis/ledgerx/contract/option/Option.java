package oasis.ledgerx.contract.option;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.trading.PriceProvider;
import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.contract.Contract;
import oasis.ledgerx.contract.ContractType;
import oasis.ledgerx.exception.DifferentCurrencyException;
import oasis.ledgerx.inventory.asset.AssetStack;
import oasis.ledgerx.inventory.asset.CashStack;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A unilateral contract which gives the owner only the right to exercise
 * Perpetual options are not supported
 * @param <A> Underlying asset
 */
public final class Option<A extends Asset> implements Contract {
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

    public Option(Option<A> other) {
        this.symbol = other.symbol;
        this.buyer = other.buyer;
        this.seller = other.seller;
        this.delivery = other.delivery;
        this.expiry = other.expiry;

        this.optionType = other.optionType;
        this.market = other.market;
        this.exercisePrice = other.exercisePrice;
    }

    private final String symbol;
    private final Actor buyer;
    private final Actor seller;
    private final AssetStack delivery;
    @Nullable
    private final DateTime expiry;

    private final OptionType optionType;
    private final PriceProvider market;
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

    public OptionType getOptionType() {
        return optionType;
    }

    public boolean isCall() {
        return getOptionType().isCall();
    }

    public boolean isAmerican() {
        return getOptionType().isAmerican();
    }

    public PriceProvider getMarket() {
        return market;
    }

    public CashStack getExercisePrice() {
        return exercisePrice;
    }

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
    public ContractType getType() {
        return ContractType.OPTION;
    }
}
