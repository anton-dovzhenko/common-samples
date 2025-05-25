package com.gammadevs;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBook {

    private final OrderMatchBlotter blotter;
    private final PriorityQueue<Order> bids = new PriorityQueue<>(Order.BID_COMPARATOR);
    private final PriorityQueue<Order> asks = new PriorityQueue<>(Order.ASK_COMPARATOR);
    private final Map<Long, Order> bidsMap = new HashMap<>();
    private final Map<Long, Order> asksMap = new HashMap<>();

    public OrderBook(OrderMatchBlotter blotter) {
        this.blotter = blotter;
    }

    public void addBid(Order bid) {
        while (!asks.isEmpty() && bid.getAmountLeft() > 0) {
            Order head = asks.peek();
            if (head.isCancelled()) {
                asks.poll();
            } else {
                if (head.getPrice() <= bid.getPrice()) {
                    long matchedAmount = Long.min(head.getAmountLeft(), bid.getAmountLeft());
                    head.doMatch(matchedAmount);
                    bid.doMatch(matchedAmount);
                    blotter.addRecord(bid, head, matchedAmount, head.getPrice());
                    if (head.getAmountLeft() == 0) {
                        asks.poll();
                        asksMap.remove(head.getId());
                    }
                } else {
                    break;
                }
            }
        }

        if (bid.getAmountLeft() > 0) {
            bidsMap.put(bid.getId(), bid);
            bids.add(bid);
        }

    }

    public void addAsk(Order ask) {
        while (!bids.isEmpty() && ask.getAmountLeft() > 0) {
            Order head = bids.peek();
            if (head.isCancelled()) {
                bids.poll();
            } else {
                if (head.getPrice() >= ask.getPrice()) {
                    long matchedAmount = Long.min(head.getAmountLeft(), ask.getAmountLeft());
                    head.doMatch(matchedAmount);
                    ask.doMatch(matchedAmount);
                    blotter.addRecord(head, ask, matchedAmount, head.getPrice());
                    if (head.getAmountLeft() == 0) {
                        bids.poll();
                        bidsMap.remove(head.getId());
                    }
                } else {
                    break;
                }
            }
        }

        if (ask.getAmountLeft() > 0) {
            asksMap.put(ask.getId(), ask);
            asks.add(ask);
        }
    }

    public void cancelBid(long id) {
        Order o = bidsMap.get(id);
        if (o != null) {
            o.doCancel();
            bidsMap.remove(id);
        }
    }

    public void cancelAsk(long id) {
        Order o = asksMap.get(id);
        if (o != null) {
            o.doCancel();
            asksMap.remove(id);
        }
    }

    public int getBidCount() {
        return bids.size();
    }

    public int getAskCount() {
        return asks.size();
    }

    public int getBidMapCount() {
        return bidsMap.size();
    }

    public int getAskMapCount() {
        return asksMap.size();
    }

}
