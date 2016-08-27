package vertx.exchange.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testFullOneStepConstPriceMatch() throws Exception {
        final Order order = new Order(-1, "a", Side.BID, 100, 100);
        order.match(100, 100);
        assertEquals(0, order.getSizeLeft());
        assertEquals(100, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.FILLED, order.getStatus());
    }

    @Test
    public void testFullThreeStepConstPriceMatch() throws Exception {
        final Order order = new Order(-1, "a", Side.BID, 100, 100);
        order.match(30, 100);
        assertEquals(70, order.getSizeLeft());
        assertEquals(100, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.PART_FILLED, order.getStatus());

        order.match(30, 100);
        assertEquals(40, order.getSizeLeft());
        assertEquals(100, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.PART_FILLED, order.getStatus());

        order.match(40, 100);
        assertEquals(0, order.getSizeLeft());
        assertEquals(100, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.FILLED, order.getStatus());
    }

    @Test
    public void testFullMixedMatch() throws Exception {
        final Order order = new Order(-1, "a", Side.BID, 100, 100);
        order.match(30, 105);
        assertEquals(70, order.getSizeLeft());
        assertEquals(105, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.PART_FILLED, order.getStatus());

        order.match(30, 110);
        assertEquals(40, order.getSizeLeft());
        assertEquals(107.5, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.PART_FILLED, order.getStatus());

        order.match(40, 115);
        assertEquals(0, order.getSizeLeft());
        assertEquals(110.5, order.getAvgExecPrice(), 1e-6);
        assertEquals(Status.FILLED, order.getStatus());
    }

}