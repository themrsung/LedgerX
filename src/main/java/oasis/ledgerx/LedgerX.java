package oasis.ledgerx;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.asset.cash.Cash;
import oasis.ledgerx.asset.cash.CashMeta;
import oasis.ledgerx.asset.stock.Stock;
import oasis.ledgerx.asset.stock.StockMeta;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.contract.bond.Bond;
import oasis.ledgerx.listener.asset.AssetTransferHandler;
import oasis.ledgerx.listener.contract.OptionExerciser;
import oasis.ledgerx.stack.asset.CashStack;
import oasis.ledgerx.stack.asset.StockStack;
import oasis.ledgerx.stack.contract.BondStack;
import oasis.ledgerx.stack.contract.ContractStack;
import oasis.ledgerx.stack.contract.OptionStack;
import oasis.ledgerx.state.LedgerXState;
import oasis.ledgerx.state.LedgerState;
import oasis.ledgerx.timer.contract.ContractExpirationEnforcer;
import oasis.ledgerx.timer.io.LedgerAutoSaver;
import oasis.ledgerx.timer.market.MarketTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

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
        this.state = LedgerXState.load();

        Actor a = new EconomicActor();
        getState().addActor(a);

        a.addAsset(new StockStack(new Stock("TEST", new StockMeta()), 10));

        Actor b = new EconomicActor();
        getState().addActor(b);

        b.addAsset(new CashStack(new Cash("CR", new CashMeta()), 300.2d));


        Cash cash = new Cash("sdsd", new CashMeta());
        Market m = new Market(new ArrayList<>(), cash, 1, new CashStack());

        getState().addMarket(m);

        OptionStack os = new OptionStack();
        getState().addContract(os);

        new AssetTransferHandler(this);
        new OptionExerciser(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MarketTimer(this), 0, 4);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ContractExpirationEnforcer(this), 0, 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LedgerAutoSaver(this), 0, 60 * 20);

        Bukkit.getLogger().info("[LedgerX] Loading complete!");
    }

    @Override
    public void onDisable() {
        getState().save();

        Bukkit.getLogger().info("[LedgerX] Shutting down.");
    }
}
