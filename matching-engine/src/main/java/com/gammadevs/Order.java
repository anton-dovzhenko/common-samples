package com.gammadevs;

import java.util.Comparator;
import java.util.Objects;

public class Order {

    public static final Comparator<Order> BID_COMPARATOR = (o1, o2) -> {
        int comparison = -1 * Long.compare(o1.price, o2.price);
        if (comparison != 0) {
            return comparison;
        }
        comparison = -1 * Long.compare(o1.sequenceNumber, o2.sequenceNumber);
        return comparison;
    };

    public static final Comparator<Order> ASK_COMPARATOR = (o1, o2) -> {
        int comparison = Long.compare(o1.price, o2.price);
        if (comparison != 0) {
            return comparison;
        }
        comparison = -1 * Long.compare(o1.sequenceNumber, o2.sequenceNumber);
        return comparison;
    };

    private final long id;
    private final long sequenceNumber;
    private final long amount;
    private final long price;

    private long amountLeft;
    private boolean cancelled = false;

    public Order(long id, long amount, long price, long sequenceNumber) {
        if (amount <= 0 || price <= 0 || id <= 0) {
            throw new IllegalArgumentException("Invalid order parameters");
        }
        this.id = id;
        this.amount = amount;
        this.amountLeft = amount;
        this.price = price;
        this.sequenceNumber = sequenceNumber;
    }

    public void doMatch(long matchedAmount) {
        if (matchedAmount > amountLeft) {
            //TODO: create custom Exception
            throw new IllegalArgumentException(String.format("MatchedAmount %s must be <= than AmountLeft %s", matchedAmount, amountLeft));
        }
        amountLeft -= matchedAmount;
    }

    public void doCancel() {
        cancelled = true;
    }

    public long getId() {
        return id;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public long getAmount() {
        return amount;
    }

    public long getPrice() {
        return price;
    }

    public long getAmountLeft() {
        return amountLeft;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
