package oasis.ledgerx.asset;

public enum AssetType {
    /**
     * A liquid asset that can be transferred without restrictions
     */
    CASH,

    /**
     * Proof of partial ownership to a corporation
     */
    STOCK,

    /**
     * A physical asset
     */
    COMMODITY;
}
