package oasis.ledgerx.stack.contract;

import oasis.ledgerx.contract.futures.Futures;
import oasis.ledgerx.contract.option.Option;

import javax.annotation.Nonnull;
import java.io.Serializable;

public final class OptionStack implements ContractStack, Serializable {
    public OptionStack() {
        this.contract = new Option();
        this.quantity = 0L;
    }
    public OptionStack(Option contract, long quantity) {
        this.contract = contract;
        this.quantity = quantity;
    }

    public OptionStack(OptionStack other) {
        this.contract = other.contract;
        this.quantity = other.quantity;
    }

    private final Option contract;
    private long quantity;

    @Nonnull
    @Override
    public Option getContract() {
        return new Option(contract);
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) throws IllegalArgumentException {
        if (quantity < 0L) throw new IllegalArgumentException();
        this.quantity = quantity;
    }
}
