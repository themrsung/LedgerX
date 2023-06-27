package oasis.ledgerx.stack.asset;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.asset.AssetMeta;
import oasis.ledgerx.asset.AssetType;
import oasis.ledgerx.asset.commodity.Commodity;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.text.NumberFormat;

/**
 * A stack of assets
 * Quantity cannot be negative; Use contracts to denote liabilities
 */
public interface AssetStack {
    /**
     * Gets a copy of the asset this stack is holding
     */
    @Nonnull
    Asset getAsset();

    /**
     * Gets the quantity of this asset
     */
    @Nonnegative
    default double getQuantity() {
        return getIntegralQuantity() + getFractionalQuantity();
    }

    /**
     * Sets quantity of this stack
     * @param quantity Will be rounded if asset is not fractional
     * @return Quantity after change
     * @throws IllegalArgumentException When negative quantity is given
     */
    @Nonnegative
    double setQuantity(@Nonnegative double quantity) throws IllegalArgumentException;

    /**
     * Adds quantity to this stack
     * @param delta Will be floored if asset is not fractional
     * @return Quantity after change
     * @throws IllegalArgumentException When quantity after change is negative
     */
    @Nonnegative
    default double addQuantity(@Nonnegative double delta) throws IllegalArgumentException {
        double before = getQuantity();
        double after = before + (isFractional() ? delta : Math.floor(delta));

        try {
            return setQuantity(after);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes quantity from this stack
     * @param delta Will be floored is asset is not fractional
     * @return Quantity after change
     * @throws IllegalArgumentException When quantity after change is negative
     */
    @Nonnegative
    default double removeQuantity(@Nonnegative double delta) throws IllegalArgumentException {
        double before = getQuantity();
        double after = before - (isFractional() ? delta : Math.floor(delta));

        try {
            return setQuantity(after);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets the integral part of quantity
     */
    @Nonnegative
    long getIntegralQuantity();

    /**
     * Gets the fractional part of quantity
     */
    @Nonnegative
    default double getFractionalQuantity() {
        AssetMeta meta = getAsset().getMeta();
        return meta.isFractional() ? meta.getFractionalQuantity() : 0d;
    }

    /**
     * Gets the symbol of this asset
     */
    @Nonnull
    default String getSymbol() {
        return getAsset().getSymbol();
    }

    /**
     * Formats the stack into human-readable form
     */
    default String format() {
        return getSymbol() + " " + NumberFormat.getInstance().format(getQuantity());
    }

    /**
     * Gets the type of asset this stack is holding
     */
    @Nonnull
    default AssetType getAssetType() {
        return getAsset().getType();
    }

    /**
     * Gets the copy of the asset's metadata
     */
    @Nonnull
    AssetMeta getMeta();

    /**
     * Whether this asset if fractional
     */
    default boolean isFractional() {
        return getAsset().isFractional();
    }
}
