package com.gammadevs.quoteviewer.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Anton on 9/1/2014.
 */
public class Quote {

    private final Long id;
    private final Integer version;
    private final Date creationDate;
    private final Date updateDate;
    private final Status status;
    private final String ccyPair;
    private final Direction direction;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final Long requesterId;

    public Quote(Long id
            , Integer version
            , Date creationDate
            , Date updateDate
            , Status status
            , String ccyPair
            , Direction direction
            , BigDecimal price
            , BigDecimal amount
            , Long requesterId) {
        this.id = id;
        this.version = version;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.ccyPair = ccyPair;
        this.direction = direction;
        this.price = price;
        this.amount = amount;
        this.requesterId = requesterId;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public Direction getDirection() {
        return direction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getRequesterId() {
        return requesterId;
    }

}
