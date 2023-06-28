package oasis.ledgerx.asset;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.ledgerx.asset.cash.CashMeta;
import oasis.ledgerx.asset.commodity.CommodityMeta;
import oasis.ledgerx.asset.stock.StockMeta;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CashMeta.class, name = "CASH"),
        @JsonSubTypes.Type(value = CommodityMeta.class, name = "COMMODITY"),
        @JsonSubTypes.Type(value = StockMeta.class, name = "STOCK")
})
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

    /**
     *
     * @return
     */
    @Nonnull
    AssetType getType();
}
