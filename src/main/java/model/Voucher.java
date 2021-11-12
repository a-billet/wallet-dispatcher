package model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Voucher {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final int amount;
    protected LocalDate beginDate;
    protected LocalDate endDate;

    public Voucher(int amount, String beginDate) throws ParseException {
        this.amount = amount;
        this.beginDate = LocalDate.parse(beginDate, formatter);
        applyEndDate(beginDate);
    }

    public abstract void applyEndDate(String beginDate) throws ParseException;

    public String getBeginDate() {
        return beginDate.toString();
    }

    public String getEndDate() {
        return endDate.toString();
    }

    public int getAmount() {
        return amount;
    }

    public boolean isExpired() {
        LocalDate today = LocalDate.now();
        return today.isBefore(beginDate) || today.isAfter(endDate);
    }
}

