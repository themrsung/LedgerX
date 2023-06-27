package oasis.ledgerx.timer.io;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.timer.LedgerTimer;

public final class LedgerAutoSaver extends LedgerTimer {
    public LedgerAutoSaver(LedgerX lx) {
        super(lx);
    }

    @Override
    public void run() {
        getState().save();
    }
}
