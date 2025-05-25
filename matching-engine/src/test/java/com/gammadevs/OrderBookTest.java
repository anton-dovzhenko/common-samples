package com.gammadevs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderBookTest {

    @Mock
    private SystemUtils systemUtils;
    private OrderBook orderBook;
    private OrderMatchBlotter blotter;

    private Order getEmptyOrder(long id) {
        return new Order(id, 0, 0, 0);
    }

    @BeforeEach
    public void beforeEach() {
        when(systemUtils.currentTimeMillis()).thenReturn(0L);
        blotter = new OrderMatchBlotter(systemUtils);
        orderBook = new OrderBook(blotter);
    }

    @Test
    public void test2Ask1Bid1MatchedBid() {
        orderBook.addAsk(new Order(1, 10, 100, 1));
        orderBook.addAsk(new Order(2, 10, 99, 2));
        orderBook.addBid(new Order(3, 5, 95, 3));
        orderBook.addBid(new Order(4, 15, 105, 4));
        List<MatchRecord> expected = new ArrayList<>();
        expected.add(new MatchRecord(getEmptyOrder(4), getEmptyOrder(2), 10, 99, 0));
        expected.add(new MatchRecord(getEmptyOrder(4), getEmptyOrder(1), 5, 100, 0));
        assertEquals(expected, blotter.getRecordsCopy());
        assertEquals(1, orderBook.getBidCount());
        assertEquals(1, orderBook.getAskCount());
    }

    @Test
    public void test2Bid1Ask1MatchedAsk() {
        orderBook.addBid(new Order(1, 10, 95, 1));
        orderBook.addBid(new Order(2, 10, 96, 2));
        orderBook.addAsk(new Order(3, 5, 100, 3));
        orderBook.addAsk(new Order(4, 15, 90, 4));
        List<MatchRecord> expected = new ArrayList<>();
        expected.add(new MatchRecord(getEmptyOrder(2), getEmptyOrder(4), 10, 96, 0));
        expected.add(new MatchRecord(getEmptyOrder(1), getEmptyOrder(4), 5, 95, 0));
        assertEquals(expected, blotter.getRecordsCopy());
        assertEquals(1, orderBook.getBidCount());
        assertEquals(1, orderBook.getAskCount());
    }

    @Test
    public void testCancelledBid() {
        orderBook.addBid(new Order(1, 10, 95, 1));
        orderBook.cancelBid(1);
        assertTrue(blotter.getRecordsCopy().isEmpty());
        assertEquals(1, orderBook.getBidCount());
        assertEquals(0, orderBook.getBidMapCount());
    }

    @Test
    public void testCancelledAsk() {
        orderBook.addAsk(new Order(1, 10, 95, 1));
        orderBook.cancelAsk(1);
        assertTrue(blotter.getRecordsCopy().isEmpty());
        assertEquals(1, orderBook.getAskCount());
        assertEquals(0, orderBook.getAskMapCount());
    }


    @Test
    public void testCancelledBidNoMatchedAsk() {
        orderBook.addBid(new Order(1, 10, 95, 1));
        orderBook.cancelBid(1);
        orderBook.addAsk(new Order(2, 10, 90, 2));
        assertTrue(blotter.getRecordsCopy().isEmpty());
        assertEquals(0, orderBook.getBidCount());
        assertEquals(0, orderBook.getBidMapCount());
        assertEquals(1, orderBook.getAskCount());
    }

    @Test
    public void testCancelledAskNoMatchedBid() {
        orderBook.addAsk(new Order(1, 10, 95, 1));
        orderBook.cancelAsk(1);
        orderBook.addBid(new Order(2, 10, 100, 2));
        assertTrue(blotter.getRecordsCopy().isEmpty());
        assertEquals(1, orderBook.getBidCount());
        assertEquals(0, orderBook.getAskCount());
        assertEquals(0, orderBook.getAskMapCount());
    }

    @Test
    public void testLargeOrderBook() {
        for (int i = 1; i <= 1000; i++) {
            orderBook.addBid(new Order(i, 10, 100 + i, i));
            orderBook.addAsk(new Order(1000 + i, 10, 100 + i, 1000 + i));
        }
        orderBook.addBid(new Order(2001, 100, 1100, 2001));
        List<MatchRecord> records = blotter.getRecordsCopy();
        assertEquals(1_000, records.size());
        assertEquals(1, orderBook.getBidCount());
        assertEquals(0, orderBook.getAskCount());
    }


}