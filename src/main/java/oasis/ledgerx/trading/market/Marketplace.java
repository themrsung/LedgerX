package oasis.ledgerx.trading.market;

import oasis.ledgerx.trading.PriceProvider;
import oasis.ledgerx.trading.order.Order;
import oasis.ledgerx.state.LedgerState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * A marketplace can accept and process orders
 */
public interface Marketplace extends PriceProvider {
    /**
     * Gets the minimum tick size of this marketplace
     * All orders non-compliant will be cancelled
     */
    double getTickSize();

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

    /**
     * Gets structured buy tick data sorted by price descending
     */
    default List<MarketTick> getBidTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice() == o.getPrice().getQuantity()) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(true, o.getPrice().getQuantity(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));

        return ticks;
    }

    /**
     * Gets lowest bid
     */
    @Nullable
    default MarketTick getLowestBid() {
        List<MarketTick> bids = getBidTicks();
        return bids.size() > 0 ? bids.get(0) : null;
    }

    /**
     * Gets structured sell tick data sorted by price ascending
     */
    default List<MarketTick> getAskTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (!o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice() == o.getPrice().getQuantity()) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(false, o.getPrice().getQuantity(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));

        return ticks;
    }

    /**
     * Gets highest ask
     */
    @Nullable
    default MarketTick getHighestAsk() {
        List<MarketTick> asks = getAskTicks();
        return asks.size() > 0 ? asks.get(0) : null;
    }
}
