package oasis.ledgerx.asset.stock;

import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.cash.Cash;

import javax.annotation.Nullable;
import java.io.Serializable;

public final class StockMeta implements AssetMeta, Serializable {
    public StockMeta() {
        this.purchasePrice = null;
    }

    public StockMeta(@Nullable Cash purchasePrice) {
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
