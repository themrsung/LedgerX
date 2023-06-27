package oasis.ledgerx.contract;

public enum ContractType {
    /**
     * A binding bilateral contract which denotes the transfer of cash on expiry
     */
    BOND,

    /**
     * A binding bilateral contract which denotes the transfer of a non-cash asset on expiry
     */
    FUTURES,

    /**
     * A unilateral contract which gives the owner the right, but not the obligation to buy a certain asset
     */
    OPTION;
}
