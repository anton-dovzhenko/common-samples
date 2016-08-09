package vertx.exchange.core.model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public static class MsgCodec implements MessageCodec<Order, Order> {

        private final Logger log = LoggerFactory.getLogger(MsgCodec.class);

        @Override
        public void encodeToWire(Buffer buffer, Order order) {
            buffer.appendLong(order.id);
            buffer.appendInt(order.instrument.length());
            buffer.appendString(order.instrument);
            buffer.appendInt(order.side.ordinal());
            buffer.appendInt(order.size);
            buffer.appendInt(order.price);

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(this);
                final byte[] bytes = bos.toByteArray();
                buffer.appendInt(bytes.length);
                buffer.appendBytes(bytes);
            } catch (IOException e) {
                log.error("Serialization failed", e);
            }
        }

        @Override
        public Order decodeFromWire(int pos, Buffer buffer) {
            try {
                int length = buffer.getInt(pos += 4);
                byte[] bytes = buffer.getBytes(pos, pos + length);
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInput in = new ObjectInputStream(bis);
                return (Order) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                log.error("Serialization failed", e);
                throw new RuntimeException("This is bad practice");
            }
        }

        @Override
        public Order transform(Order order) {
            return order;
        }

        @Override
        public String name() {
            return Order.class.getSimpleName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }

    }

}
