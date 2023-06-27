package oasis.ledgerx.classes;

import oasis.ledgerx.asset.Asset;
import oasis.ledgerx.inventory.asset.CashStack;
import oasis.ledgerx.market.Marketplace;
import oasis.ledgerx.market.order.Order;
import oasis.ledgerx.market.order.OrderType;
import oasis.ledgerx.state.LedgerState;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class Market implements Marketplace {
    public Market(Market other) {
        this.orders = other.orders;
        this.asset = other.asset;
        this.price = other.price;
    }

    private final List<Order> orders;
    private final Asset asset;
    private CashStack price;

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

    // Cleans orders with 0 quantity
    private void cleanFulfilledOrders(LedgerState state) {
        for (Order o : getOrders()) {
            if (o.getQuantity() <= 0L) {
                cancelOrder(o, state);
            }
        }
    }

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

    private void killImmediateOrders(LedgerState state) {
        for (Order o : getOrders()) {
            if (o.getOrderType() == OrderType.IMMEDIATE_OR_CANCEL_BUY || o.getOrderType() == OrderType.IMMEDIATE_OR_CANCEL_SELL || o.getOrderType() == OrderType.FILL_OR_KILL_BUY || o.getOrderType() == OrderType.FILL_OR_KILL_SELL) {
                if (o.getTime().plusSeconds(1).isBeforeNow()) {
                    cancelOrder(o, state);
                }
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

    }

    @Override
    public Asset getAsset() {
        return asset.copy();
    }

    @Override
    public CashStack getPrice() {
        return null;
    }
}
