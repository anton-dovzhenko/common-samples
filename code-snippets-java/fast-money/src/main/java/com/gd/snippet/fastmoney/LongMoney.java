package com.gd.snippet.fastmoney;

import static java.lang.Math.pow;

/**
 * Created by anton on 3/1/17.
 */
public class LongMoney {

    private final long value;
    private final int precision;

    public LongMoney(long value, int precision) {
        this.value = value;
        this.precision = precision;
    }

    public LongMoney add(LongMoney other, int precision) {
        long thisVal = value;
        long otherVal = other.value;
        thisVal *= pow(10, 1 + precision - this.precision);
        otherVal *= pow(10, 1 + precision - other.precision);
        return new LongMoney((thisVal + otherVal) / 10, precision);
    }

    public LongMoney subtract(LongMoney other, int precision) {
        long thisVal = value;
        long otherVal = other.value;
        thisVal *= pow(10, 1 + precision - this.precision);
        otherVal *= pow(10, 1 + precision - other.precision);
        return new LongMoney((thisVal - otherVal) / 10, precision);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongMoney longMoney = (LongMoney) o;

        if (value != longMoney.value) return false;
        return precision == longMoney.precision;
    }

    @Override
    public int hashCode() {
        return (int) (31 * value + precision);
    }

    @Override
    public String toString() {
        return "LongMoney(" + value + "; " + precision + ")";
    }
}
