package oasis.ledgerx.asset;

import javax.annotation.Nonnull;

/**
 * Assets are stored in their owner class.
 */
public interface Asset {
    /**
     * Gets the unique symbol of this asset
     */
    @Nonnull
    String getSymbol();

    /**
     * Gets the type of this asset
     */
    @Nonnull
    AssetType getType();

    /**
     * Gets a copy of the metadata of this asset
     */
    @Nonnull
    AssetMeta getMeta();

    /**
     * Sets the metadata of this asset
     */
    void setMeta(@Nonnull AssetMeta meta);

    /**
     * Whether this asset can have decimal places in its quantity
     */
    default boolean isFractional() {
        return getMeta().isFractional();
    }

    /**
     * Gets an identical copy of this asset
     */
    Asset copy();
}
