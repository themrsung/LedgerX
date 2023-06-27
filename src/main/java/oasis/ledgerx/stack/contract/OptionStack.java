package oasis.ledgerx.stack.contract;

import oasis.ledgerx.contract.futures.Futures;
import oasis.ledgerx.contract.option.Option;

import javax.annotation.Nonnull;

public final class OptionStack implements ContractStack {
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
