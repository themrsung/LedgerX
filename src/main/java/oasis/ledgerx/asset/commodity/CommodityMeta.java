package oasis.ledgerx.asset.commodity;

import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.cash.Cash;

import javax.annotation.Nullable;

public final class CommodityMeta implements AssetMeta {
    public CommodityMeta() {
        this.purchasePrice = null;
    }

    public CommodityMeta(Cash purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public CommodityMeta(CommodityMeta other) {
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
