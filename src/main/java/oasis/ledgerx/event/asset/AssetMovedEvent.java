package oasis.ledgerx.event.asset;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.asset.AssetType;
import oasis.ledgerx.event.LedgerEvent;
import oasis.ledgerx.stack.asset.AssetStack;

import javax.annotation.Nonnull;

public final class AssetMovedEvent extends LedgerEvent {
    public AssetMovedEvent(
            @Nonnull Actor sender,
            @Nonnull Actor recipient,
            @Nonnull AssetStack payment,
            boolean checkSolvency
    ) {
        this.sender = sender;
        this.recipient = recipient;
        this.payment = payment;
        this.checkSolvency = checkSolvency;
    }

    private final Actor sender;
    private final Actor recipient;
    private final AssetStack payment;
    private final boolean checkSolvency;

    public Actor getSender() {
        return sender;
    }

    public Actor getRecipient() {
        return recipient;
    }

    public AssetStack getPayment() {
        return payment;
    }

    public boolean checkSolvency() {
        return checkSolvency;
    }

    public AssetType getPaymentType() {
        return payment.getAssetType();
    }
}
