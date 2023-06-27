package oasis.ledgerx.listener.asset;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.event.asset.AssetMovedEvent;
import oasis.ledgerx.listener.LedgerListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * Handles the transfer of assets between actors
 */
public final class AssetTransferHandler extends LedgerListener {
    public AssetTransferHandler(LedgerX lx) {
        super(lx);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAssetMoved(AssetMovedEvent e) {
        if (e.isCancelled()) return;

        if (!e.checkSolvency() || e.getSender().hasAsset(e.getPayment())) {
            e.getSender().removeAsset(e.getPayment());
            e.getRecipient().addAsset(e.getPayment());
        }
    }
}
