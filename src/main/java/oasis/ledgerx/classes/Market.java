package oasis.ledgerx.classes;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.stack.asset.CashStack;
import oasis.ledgerx.state.LedgerState;
import oasis.ledgerx.trading.market.MarketTick;
import oasis.ledgerx.trading.market.Marketplace;
import oasis.ledgerx.trading.order.Order;
import oasis.ledgerx.trading.order.OrderType;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of Marketplace
 */
public final class Market implements Marketplace, Serializable {
    public Market(List<Order> orders, Asset asset, double tickSize, CashStack price) {
        this.orders = orders;
        this.asset = asset;
        this.tickSize = tickSize;
        this.price = price;
    }

    public Market(Market other) {
        this.orders = other.orders;
        this.asset = other.asset;
        this.tickSize = other.tickSize;
        this.price = other.price;
    }

    private final List<Order> orders;
    private final Asset asset;
    private final double tickSize;
    private CashStack price;

    @Override
    public double getTickSize() {
        return tickSize;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public void placeOrder(@Nonnull Order order, @Nonnull LedgerState state) {
        order.onSubmitted(state);
        orders.add(order);
    }

    @Override
    public void cancelOrder(@Nonnull Order order, @Nonnull LedgerState state) {
        order.onCancelled(state);
        orders.remove(order);
    }

    // Cancels orders non-compliant with tick size
    private void enforceTickSize(LedgerState state) {
        for (Order o : getOrders()) {
            double price = o.getPrice().getQuantity();

            if (price % getTickSize() != 0d) {
                cancelOrder(o, state);
            }
        }
    }

    // Cleans fulfilled orders
    private void cleanFulfilledOrders(LedgerState state) {
        for (Order o : getOrders()) {
            if (o.getQuantity() <= 0L) {
                cancelOrder(o, state);
            }
        }
    }

    // Triggers stop-loss
    private void triggerStopLoss() {
        for (Order o : getOrders()) {
            if (o.getOrderType() == OrderType.STOP_LOSS_SELL) {
                double orderPrice = o.getPrice().getQuantity();
                double currentPrice = getPrice().getQuantity();

                if (currentPrice <= orderPrice) {
                    o.setOrderType(OrderType.MARKET_SELL);
                }
            } else if (o.getOrderType() == OrderType.STOP_LOSS_BUY) {
                double orderPrice = o.getPrice().getQuantity();
                double currentPrice = getPrice().getQuantity();

                if (currentPrice >= orderPrice) {
                    o.setOrderType(OrderType.MARKET_BUY);
                }
            }
        }
    }

    // Triggers stop-limit
    private void triggerStopLimit() {
        for (Order o : getOrders()) {
            if (o.getOrderType() == OrderType.STOP_LIMIT_SELL) {
                double orderPrice = o.getPrice().getQuantity();
                double currentPrice = getPrice().getQuantity();

                if (currentPrice <= orderPrice) {
                    o.setOrderType(OrderType.LIMIT_SELL);
                }
            } else if (o.getOrderType() == OrderType.STOP_LIMIT_BUY) {
                double orderPrice = o.getPrice().getQuantity();
                double currentPrice = getPrice().getQuantity();

                if (currentPrice >= orderPrice) {
                    o.setOrderType(OrderType.LIMIT_BUY);
                }
            }
        }
    }

    // Kills unfulfilled immediate orders
    private void killImmediateOrders(LedgerState state) {
        for (Order o : getOrders()) {
            if (o.isImmediate()) {
                if (o.getTime().plusSeconds(1).isBeforeNow()) {
                    cancelOrder(o, state);
                }
            }
        }
    }

    // Reprices market orders to be processed first
    private void repriceMarketOrders() {
        MarketTick highestAsk = getHighestAsk();
        double askPrice = highestAsk != null ? highestAsk.getPrice() : Double.MAX_VALUE;
        CashStack buyMarketPrice = new CashStack(getCurrency(), askPrice);

        for (Order o : getBuyOrders()) {
            if (o.isMarket()) {
                o.setPrice(buyMarketPrice);
            }
        }

        MarketTick lowestBid = getLowestBid();
        double bidPrice = lowestBid != null ? lowestBid.getPrice() : 0d;
        CashStack sellMarketPrice = new CashStack(getCurrency(), bidPrice);

        for (Order o : getSellOrders()) {
            if (o.isMarket()) {
                o.setPrice(sellMarketPrice);
            }
        }
    }

    // Gets all buy orders sorted by time and price
    private List<Order> getBuyOrders() {
        List<Order> orders = new ArrayList<>();

        for (Order o : getOrders()) {
            if (o.isBuy()) orders.add(o);
        }

        orders.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        orders.sort((o1, o2) -> Double.compare(o2.getPrice().getQuantity(), o1.getPrice().getQuantity()));

        return orders;
    }

    // Gets all sell orders sorted by time and price
    private List<Order> getSellOrders() {
        List<Order> orders = new ArrayList<>();

        for (Order o : getOrders()) {
            if (!o.isBuy()) orders.add(o);
        }

        orders.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
        orders.sort((o1, o2) -> Double.compare(o1.getPrice().getQuantity(), o2.getPrice().getQuantity()));

        return orders;
    }

    @Override
    public void processOrders(LedgerState state) {
        enforceTickSize(state);

        cleanFulfilledOrders(state);

        triggerStopLoss();
        triggerStopLimit();

        killImmediateOrders(state);

        repriceMarketOrders();

        for (Order bo : getBuyOrders()) {
            for (Order so : getSellOrders()) {
                if (bo.getPrice().getQuantity() >= so.getPrice().getQuantity()) {
                    // Prices match
                    CashStack p = bo.getTime().isBefore(so.getTime()) ? bo.getPrice() : so.getPrice();
                    long q = Math.min(bo.getQuantity(), so.getQuantity());

                    if (q == bo.getQuantity() || bo.allowsPartialFulfillment()) {
                        if (q == so.getQuantity() || so.allowsPartialFulfillment()) {
                            bo.onFulfilled(state, p, q);
                            so.onFulfilled(state, p, q);

                            price = p;
                        }
                    }
                }
            }
        }

    }

    @Override
    public Asset getAsset() {
        return asset.copy();
    }

    @Override
    public CashStack getPrice() {
        return new CashStack(price);
    }
}
