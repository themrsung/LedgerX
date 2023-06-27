package oasis.ledgerx.stack.contract;

import oasis.ledgerx.contract.bond.Bond;

import javax.annotation.Nonnull;
import java.io.Serializable;

public final class BondStack implements ContractStack, Serializable {
    public BondStack(Bond contract, long quantity) {
        this.contract = contract;
        this.quantity = quantity;
    }

    public BondStack(BondStack other) {
        this.contract = other.contract;
        this.quantity = other.quantity;
    }

    private final Bond contract;
    private long quantity;

    @Nonnull
    @Override
    public Bond getContract() {
        return new Bond(contract);
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
