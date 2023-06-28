package oasis.ledgerx.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.classes.EconomicActor;
import oasis.ledgerx.stack.asset.AssetStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * An entity capable of holding assets and participating in economic events
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EconomicActor.class, name = "ACTOR")
})
public interface Actor {
    /**
     * Gets the unique ID of this actor
     */
    @JsonProperty("uniqueId")
    UUID getUniqueId();

    /**
     * Gets a copied list of every asset this actor owns
     */
    @JsonProperty("assets")
    List<AssetStack> getAssets();

    /**
     * Gets type of this actor
     */
    @JsonIgnore
    @Nonnull
    ActorType getType();

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
