package com.gammadevs;

import java.util.ArrayList;
import java.util.List;

public class OrderMatchBlotter {

    private final SystemUtils systemUtils;
    private final List<MatchRecord> records = new ArrayList<>();

    public OrderMatchBlotter(SystemUtils systemUtils) {
        this.systemUtils = systemUtils;
    }

    public void addRecord(Order bid, Order ask, long matchedAmount, long matchedPrice) {
        long time = systemUtils.currentTimeMillis();
        MatchRecord record = new MatchRecord(bid, ask, matchedAmount, matchedPrice, time);
        records.add(record);
    }

    public List<MatchRecord> getRecordsCopy() {
        return new ArrayList<>(records);
    }

}
