package oasis.ledgerx.timer.contract;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.event.asset.AssetMovedEvent;
import oasis.ledgerx.stack.asset.AssetStack;
import oasis.ledgerx.stack.contract.ContractStack;
import oasis.ledgerx.timer.LedgerTimer;
import org.bukkit.Bukkit;

public final class ContractExpirationEnforcer extends LedgerTimer {
    public ContractExpirationEnforcer(LedgerX lx) {
        super(lx);
    }

    @Override
    public void run() {
        for (ContractStack contract : getContracts()) {
            if (contract.getExpiry() != null && contract.getExpiry().isBeforeNow()) {
                AssetStack delivery = contract.getDelivery();

                Bukkit.getPluginManager().callEvent(new AssetMovedEvent(
                        contract.getSeller(),
                        contract.getBuyer(),
                        delivery,
                        false
                ));

                removeContract(contract);
            }
        }
    }
}
