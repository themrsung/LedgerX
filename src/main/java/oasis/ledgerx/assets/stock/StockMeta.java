package oasis.ledgerx.assets.stock;

import oasis.ledgerx.assets.AssetMeta;
import oasis.ledgerx.assets.cash.Cash;

import javax.annotation.Nullable;

public final class StockMeta implements AssetMeta {
    public StockMeta() {
        this.purchasePrice = null;
    }

    public StockMeta(Cash purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public StockMeta(StockMeta other) {
        this.purchasePrice = other.purchasePrice;
    }

    @Nullable
    private Cash purchasePrice;

    @Nullable
    public Cash getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(@Nullable Cash purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean isFractional() {
        return false;
    }

    @Override
    public double getFractionalQuantity() {
        return 0;
    }
}
