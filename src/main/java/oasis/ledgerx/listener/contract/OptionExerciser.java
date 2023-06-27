package oasis.ledgerx.listener.contract;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.contract.option.Option;
import oasis.ledgerx.event.asset.AssetMovedEvent;
import oasis.ledgerx.event.contract.OptionExercisedEvent;
import oasis.ledgerx.inventory.asset.AssetStack;
import oasis.ledgerx.inventory.asset.CashStack;
import oasis.ledgerx.listener.LedgerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class OptionExerciser extends LedgerListener {
    public OptionExerciser(LedgerX lx) {
        super(lx);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOptionExercised(OptionExercisedEvent e) {
        if (e.isCancelled()) return;

        Option<?> option = e.getOption();

        AssetStack delivery = option.getDelivery();
        CashStack settlement = option.getExercisePrice();

        Actor deliverer = option.isCall() ? option.getSeller() : option.getBuyer();
        Actor recipient = option.isCall() ? option.getBuyer() : option.getSeller();

        Bukkit.getPluginManager().callEvent(new AssetMovedEvent(
                deliverer,
                recipient,
                delivery,
                false
        ));

        Bukkit.getPluginManager().callEvent(new AssetMovedEvent(
                recipient,
                deliverer,
                settlement,
                false
        ));
    }
}
