package com.gammadevs.h2db.research.model;

import java.math.BigDecimal;

/**
 * Created by Anton on 12/14/2014.
 */
public class PnLTrend {

    private long id;
    private BigDecimal pnl_5000;
    private BigDecimal pnl_2000;
    private BigDecimal pnl_1000;
    private BigDecimal pnl0;
    private BigDecimal pnl1000;
    private BigDecimal pnl2000;
    private BigDecimal pnl5000;

    public PnLTrend(long id
            , BigDecimal pnl_5000
            , BigDecimal pnl_2000
            , BigDecimal pnl_1000
            , BigDecimal pnl0
            , BigDecimal pnl1000
            , BigDecimal pnl2000
            , BigDecimal pnl5000) {
        this.id = id;
        this.pnl_5000 = pnl_5000;
        this.pnl_2000 = pnl_2000;
        this.pnl_1000 = pnl_1000;
        this.pnl0 = pnl0;
        this.pnl1000 = pnl1000;
        this.pnl2000 = pnl2000;
        this.pnl5000 = pnl5000;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getPnl_5000() {
        return pnl_5000;
    }

    public BigDecimal getPnl_2000() {
        return pnl_2000;
    }

    public BigDecimal getPnl_1000() {
        return pnl_1000;
    }

    public BigDecimal getPnl0() {
        return pnl0;
    }

    public BigDecimal getPnl1000() {
        return pnl1000;
    }

    public BigDecimal getPnl2000() {
        return pnl2000;
    }

    public BigDecimal getPnl5000() {
        return pnl5000;
    }


    @Override
    public String toString() {
        return "PnLTrend{" +
                "id=" + id +
                ", pnl_5000=" + pnl_5000 +
                ", pnl_2000=" + pnl_2000 +
                ", pnl_1000=" + pnl_1000 +
                ", pnl0=" + pnl0 +
                ", pnl1000=" + pnl1000 +
                ", pnl2000=" + pnl2000 +
                ", pnl5000=" + pnl5000 +
                '}';
    }
}
