package com.gammadevs;

import java.util.Objects;

public record MatchRecord(Order bid, Order ask, long amount, long price, long time) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchRecord that = (MatchRecord) o;
        return amount == that.amount
                && price == that.price
                && time == that.time
                && bid.getId() == that.bid.getId()
                && ask.getId() == that.ask.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(bid, ask, amount, price, time);
    }

    @Override
    public String toString() {
        return "MatchRecord{" +
                "bid=" + bid.getId() +
                ", ask=" + ask.getId() +
                ", amount=" + amount +
                ", price=" + price +
                ", time=" + time +
                '}';
    }

}
