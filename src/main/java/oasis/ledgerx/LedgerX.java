package oasis.ledgerx;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.asset.stock.Stock;
import oasis.ledgerx.asset.stock.StockMeta;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.listener.asset.AssetTransferHandler;
import oasis.ledgerx.listener.contract.OptionExerciser;
import oasis.ledgerx.stack.asset.StockStack;
import oasis.ledgerx.state.LedgerRunState;
import oasis.ledgerx.state.LedgerState;
import oasis.ledgerx.timer.contract.ContractExpirationEnforcer;
import oasis.ledgerx.timer.io.LedgerAutoSaver;
import oasis.ledgerx.timer.market.MarketTimer;
import org.bukkit.Bukkit;
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
        LedgerSaveState save = LedgerSaveState.load();
        if (save != null) {
            this.state = new LedgerRunState(save);
        } else {
            this.state = new LedgerRunState();
        }

        Actor a = new EconomicActor();
        getState().addActor(a);

        a.addAsset(new StockStack(new Stock("TEST", new StockMeta()), 10));

        new AssetTransferHandler(this);
        new OptionExerciser(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MarketTimer(this), 0, 4);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ContractExpirationEnforcer(this), 0, 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 0, 60 * 20);

        Bukkit.getLogger().info("[LedgerX] Loading complete!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[LedgerX] Shutting down.");
    }
}
