package oasis.ledgerx.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.asset.cash.Cash;
import oasis.ledgerx.asset.commodity.Commodity;
import oasis.ledgerx.asset.stock.Stock;

import javax.annotation.Nonnull;

/**
 * Assets are stored in their owner class.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cash.class, name = "CASH"),
        @JsonSubTypes.Type(value = Stock.class, name = "STOCK"),
        @JsonSubTypes.Type(value = Commodity.class, name = "COMMODITY")
})
public interface Asset {
    /**
     * Gets the unique symbol of this asset
     */
    @JsonProperty("symbol")
    @Nonnull
    String getSymbol();

    /**
     * Gets the type of this asset
     */
    @JsonProperty("type")
    @Nonnull
    default AssetType getType() {
        return getMeta().getType();
    }

    /**
     * Gets a copy of the metadata of this asset
     */
    @JsonProperty("meta")
    @Nonnull
    AssetMeta getMeta();

    /**
     * Sets the metadata of this asset
     */
    void setMeta(@Nonnull AssetMeta meta);

    /**
     * Whether this asset can have decimal places in its quantity
     */
    @JsonIgnore
    default boolean isFractional() {
        return getMeta().isFractional();
    }

    /**
     * Gets an identical copy of this asset
     */
    @JsonIgnore
    Asset copy();
}
