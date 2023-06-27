package oasis.ledgerx.assets;

import oasis.ledgerx.actors.Actor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Assets are stored centrally, and has one owner.
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
     * Gets the actual owner of this asset
     */
    @Nullable
    Actor getOwner();

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
}
