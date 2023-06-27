package oasis.ledgerx.asset;

import javax.annotation.Nonnegative;

public interface AssetMeta {
    /**
     * Whether this asset's quantity can have decimal points
     */
    boolean isFractional();

    /**
     * Gets the fractional part of a fractional asset's quantity
     * Will return 0 for integral assets
     */
    @Nonnegative
    double getFractionalQuantity();
}
