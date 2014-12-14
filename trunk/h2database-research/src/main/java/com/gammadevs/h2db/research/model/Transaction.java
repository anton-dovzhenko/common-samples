package com.gammadevs.h2db.research.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Anton on 12/14/2014.
 */
public class Transaction {

    private long id;
    private Date time;
    private long clientId;
    private String ccyPair;
    private BigDecimal volume;
    private BigDecimal clientRate;
    private BigDecimal exitRate;
    private BigDecimal usdRate;

    public Transaction(long id
            , Date time
            , long clientId
            , String ccyPair
            , BigDecimal volume
            , BigDecimal clientRate
            , BigDecimal exitRate
            , BigDecimal usdRate) {
        this.id = id;
        this.time = time;
        this.clientId = clientId;
        this.ccyPair = ccyPair;
        this.volume = volume;
        this.clientRate = clientRate;
        this.exitRate = exitRate;
        this.usdRate = usdRate;
    }

    public long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public long getClientId() {
        return clientId;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public BigDecimal getExitRate() {
        return exitRate;
    }

    public BigDecimal getUsdRate() {
        return usdRate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", time=" + time +
                ", clientId=" + clientId +
                ", ccyPair='" + ccyPair + '\'' +
                ", volume=" + volume +
                ", clientRate=" + clientRate +
                ", exitRate=" + exitRate +
                ", usdRate=" + usdRate +
                '}';
    }
}
