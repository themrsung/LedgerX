package oasis.ledgerx.trading.order;

/**
 * Type of order
 * All types are automatically handled via classes.trading.Market
 */
public enum OrderType {
    LIMIT_BUY,
    LIMIT_SELL,

    MARKET_BUY,
    MARKET_SELL,

    /**
     * Becomes a trading buy order when price reached
     */
    STOP_LOSS_BUY,
    /**
     * Becomes a trading sell order when price reached
     */
    STOP_LOSS_SELL,

    /**
     * Becomes a limit buy order when price reached
     */
    STOP_LIMIT_BUY,
    /**
     * Becomes a limit sell order when price reached
     */
    STOP_LIMIT_SELL,

    /**
     * Partial fulfillment is not allowed
     */
    ALL_OR_NONE_BUY,

    /**
     * Partial fulfillment is not allowed
     */
    ALL_OR_NONE_SELL,

    /**
     * Order will be cancelled after 1 second if not fulfilled
     */
    IMMEDIATE_OR_CANCEL_BUY,
    /**
     * Order will be cancelled after 1 second if not fulfilled
     */
    IMMEDIATE_OR_CANCEL_SELL,

    /**
     * All or none + Immediate or cancel
     */
    FILL_OR_KILL_BUY,
    /**
     * All or none + Immediate or cancel
     */
    FILL_OR_KILL_SELL;

    public boolean isBuy() {
        switch (this) {
            case LIMIT_BUY:
            case MARKET_BUY:
            case STOP_LOSS_BUY:
            case STOP_LIMIT_BUY:
            case ALL_OR_NONE_BUY:
            case IMMEDIATE_OR_CANCEL_BUY:
            case FILL_OR_KILL_BUY:
                return true;
        }

        return false;
    }

    public boolean isMarket() {
        switch (this) {
            case MARKET_BUY:
            case MARKET_SELL:
                return true;
        }

        return false;
    }

    public boolean isImmediate() {
        switch (this) {
            case IMMEDIATE_OR_CANCEL_BUY:
            case IMMEDIATE_OR_CANCEL_SELL:
            case FILL_OR_KILL_BUY:
            case FILL_OR_KILL_SELL:
                return true;
        }
        return false;
    }

    public boolean allowsPartialFulfillment() {
        switch (this) {
            case ALL_OR_NONE_BUY:
            case ALL_OR_NONE_SELL:
            case FILL_OR_KILL_BUY:
            case FILL_OR_KILL_SELL:
                return false;
        }

        return true;
    }
}
