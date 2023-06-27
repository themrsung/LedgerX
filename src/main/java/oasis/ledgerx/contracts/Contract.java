package oasis.ledgerx.contracts;

import oasis.ledgerx.actors.Actor;
import oasis.ledgerx.assets.Asset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public interface Contract {
    @Nonnull
    String getSymbol();
    @Nonnull
    Actor getBuyer();
    @Nonnull
    Actor getSeller();
    @Nonnull
    Asset getDelivery();

    @Nullable
    Date getExpiry();

    @Nonnull
    ContractType getType();
}
