package oasis.ledgerx.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * A base class for LedgerX events
 */
public abstract class LedgerEvent extends Event implements Cancellable {
    /**
     * Every LedgerX event is designed to be cancellable
     */
    private boolean cancelled;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private static final HandlerList handlers = new HandlerList();
    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
