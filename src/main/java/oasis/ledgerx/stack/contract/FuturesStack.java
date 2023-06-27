package oasis.ledgerx.stack.contract;

import oasis.ledgerx.contract.bond.Bond;
import oasis.ledgerx.contract.futures.Futures;

import javax.annotation.Nonnull;
import java.io.Serializable;

public final class FuturesStack implements ContractStack, Serializable {
    public FuturesStack(Futures contract, long quantity) {
        this.contract = contract;
        this.quantity = quantity;
    }

    public FuturesStack(FuturesStack other) {
        this.contract = other.contract;
        this.quantity = other.quantity;
    }

    private final Futures contract;
    private long quantity;

    @Nonnull
    @Override
    public Futures getContract() {
        return new Futures(contract);
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
