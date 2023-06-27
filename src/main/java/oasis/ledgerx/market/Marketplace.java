package oasis.ledgerx.market;

import oasis.ledgerx.market.order.Order;
import oasis.ledgerx.state.LedgerState;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A marketplace can accept and process orders
 */
public interface Marketplace extends PriceProvider {
    /**
     * Gets a copied list of all unfulfilled orders
     */
    List<Order> getOrders();

    /**
     * Places a new order
     */
    void placeOrder(@Nonnull Order order, @Nonnull LedgerState state);

    /**
     * Cancels an existing order
     */
    void cancelOrder(@Nonnull Order order, @Nonnull LedgerState state);

    /**
     * Processes all orders
     */
    void processOrders(LedgerState state);
}
