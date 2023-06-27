package oasis.ledgerx.contracts;

public enum ContractType {
    /**
     * A binding bilateral contract which denotes the transfer of cash on expiry
     * Derivatives include bonds, bills, etc
     */
    NOTE,

    /**
     * A binding bilateral contract which denotes the transfer of a non-cash asset on expiry
     * Derivatives include futures
     */
    FORWARD,

    /**
     * A unilateral contract which gives one party the right to buy/sell a certain asset
     * Derivatives include options
     */
    WARRANT,

    /**
     * A binding bilateral contract which exchanges the benefits and liabilities of two different assets
     */
    SWAP;
}
