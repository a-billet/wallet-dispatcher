package model;

import java.text.ParseException;
import java.time.LocalDate;

public class MealVoucher extends Voucher {

    private static final int FEBRUARY_MAX_DAYS = 29;

    public MealVoucher(int amount, String beginDate) throws ParseException {
        super(amount, beginDate);
    }

    @Override
    public void applyEndDate(String beginDate) {
        LocalDate beginning = LocalDate.parse(beginDate, formatter);
        int year = beginning.getYear() + 1;
        String newDate = year + "-" + "02" + "-" + FEBRUARY_MAX_DAYS;
        this.endDate = LocalDate.parse(newDate, formatter); // shrinks february to 28 days if regular year
    }
}
