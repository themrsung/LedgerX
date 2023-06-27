package oasis.ledgerx.listener;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.state.LedgerStateUser;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

/**
 * A base class for LedgerX event listeners
 */
public abstract class LedgerListener implements Listener, LedgerStateUser {
    public LedgerListener(@Nonnull LedgerX lx) {
        Bukkit.getPluginManager().registerEvents(this, lx);

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
