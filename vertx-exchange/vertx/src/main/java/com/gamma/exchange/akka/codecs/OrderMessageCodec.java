package com.gamma.exchange.akka.codecs;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vertx.exchange.core.model.Order;

import java.io.*;

public class OrderMessageCodec implements MessageCodec<Order, Order> {

    private final Logger log = LoggerFactory.getLogger(OrderMessageCodec.class);

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
