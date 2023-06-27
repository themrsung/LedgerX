package oasis.ledgerx;

import oasis.ledgerx.listener.asset.AssetTransferHandler;
import oasis.ledgerx.listener.contract.OptionExerciser;
import oasis.ledgerx.state.LedgerState;
import org.bukkit.plugin.java.JavaPlugin;

public final class LedgerX extends JavaPlugin {
    /**
     * Current state of LedgerX
     */
    private LedgerState state;

    /**
     * Gets the current LedgerX state
     */
    public LedgerState getState() {
        return state;
    }

    @Override
    public void onEnable() {
        new AssetTransferHandler(this);
        new OptionExerciser(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
