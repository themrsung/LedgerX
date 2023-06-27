package oasis.ledgerx.timer.market;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.timer.LedgerTimer;

/**
 * Triggers one market tick for every registered market
 */
public final class MarketTimer extends LedgerTimer {
    public MarketTimer(LedgerX lx) {
        super(lx);
    }

    @Override
    public void run() {
        for (Market m : getMarkets()) {
            m.processOrders(getState());
        }
    }
}
