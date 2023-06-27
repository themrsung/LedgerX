package oasis.ledgerx.market.bid;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.inventory.asset.CashStack;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface Bid {
    UUID getUniqueId();
    Actor getBidder();
    @Nonnull
    CashStack getPrice();
}
