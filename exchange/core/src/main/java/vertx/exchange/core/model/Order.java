package vertx.exchange.core.model;

import java.io.Serializable;
import java.util.Comparator;

public class Order implements Serializable {

    public final long id;
    public final String instrument;
    public final Side side;
    public final int size;
    public final int price;
    private int sizeLeft;
    private double avgExecPrice;
    private Status status;

    public Order(long id, String instrument, Side side, int size, int price) {
        this.id = id;
        this.instrument = instrument;
        this.side = side;
        this.size = size;
        this.sizeLeft = size;
        this.price = price;
        this.status = Status.OPEN;
    }

    public void match(int matchedSize, double matchedPrice) {
        avgExecPrice = (avgExecPrice * (size - sizeLeft) + matchedPrice * matchedSize) / (size - sizeLeft + matchedSize);
        sizeLeft = sizeLeft - matchedSize;
        status = sizeLeft == 0 ? Status.FILLED : Status.PART_FILLED;
    }

    public int getSizeLeft() {
        return sizeLeft;
    }

    public double getAvgExecPrice() {
        return avgExecPrice;
    }

    public Status getStatus() {
        return status;
    }

    public static class BidComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int comparison = -Integer.compare(o1.price, o2.price);
            if (comparison == 0) {
                comparison = Long.compare(o1.id, o2.id);
            }
            return comparison;
        }
    }

    public static class AskComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int comparison = Integer.compare(o1.price, o2.price);
            if (comparison == 0) {
                comparison = Long.compare(o1.id, o2.id);
            }
            return comparison;
        }
    }

}
