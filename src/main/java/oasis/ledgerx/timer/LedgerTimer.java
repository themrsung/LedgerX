package oasis.ledgerx.timer;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.state.LedgerStateUser;

import javax.annotation.Nonnull;

/**
 * A base class for LedgerX timers
 */
public abstract class LedgerTimer implements Runnable, LedgerStateUser {
    public LedgerTimer(@Nonnull LedgerX lx) {
        this.lx = lx;
    }

    @Nonnull
    private final LedgerX lx;

    @Override
    @Nonnull
    public LedgerX getLx() {
        return lx;
    }
}
