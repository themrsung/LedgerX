package oasis.ledgerx.contract.option;

public enum OptionType {
    /**
     * Option is exercisable at any time before expiry
     */
    AMERICAN_CALL,
    /**
     * Option is exercisable at any time before expiry
     */
    AMERICAN_PUT,

    /**
     * Option is only exercisable when expired
     */
    EUROPEAN_CALL,

    /**
     * Option is only exercisable when expired
     */
    EUROPEAN_PUT;

    public boolean isCall() {
        switch (this) {
            case AMERICAN_CALL:
            case EUROPEAN_CALL:
                return true;
        }
        return false;
    }

    public boolean isAmerican() {
        switch (this) {
            case AMERICAN_CALL:
            case AMERICAN_PUT:
                return true;
        }

        return false;
    }
}
