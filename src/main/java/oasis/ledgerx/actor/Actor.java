package oasis.ledgerx.actor;

import oasis.ledgerx.inventory.asset.AssetStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface Actor {
    /**
     * Gets the unique ID of this actor
     */
    UUID getUniqueId();

    /**
     * Gets a copied list of every asset this actor owns
     */
    List<AssetStack> getAssets();

    /**
     * Registers an asset
     */
    void addAsset(AssetStack asset);

    /**
     * Removes an asset
     */
    void removeAsset(AssetStack asset);

    /**
     * Checks whether actor has an asset
     */
    default boolean hasAsset(AssetStack asset) {
        double q = 0;

        for (AssetStack stack : getAssets()) {
            if (stack.getSymbol().equalsIgnoreCase(asset.getSymbol())) {
                q += stack.getQuantity();
            }
        }

        return q >= asset.getQuantity();
    }

    /**
     * Gets an asset by symbol
     * @param symbol Symbol to search for
     * @return Search result
     */
    @Nullable
    default AssetStack getAsset(String symbol) {
        for (AssetStack stack : getAssets()) {
            if (stack.getSymbol().equalsIgnoreCase(symbol)) {
                return stack;
            }
        }

        return null;
    }
}
