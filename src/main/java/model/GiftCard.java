package model;

import java.text.ParseException;
import java.time.LocalDate;

public class GiftCard extends Voucher {
    private final static int LIFESPAN = 1;

    public GiftCard(int amount, String beginDate) throws ParseException {
        super(amount, beginDate);
    }

    @Override
    public void applyEndDate(String beginDate) {
        LocalDate beginning = LocalDate.parse(beginDate, formatter);
        this.endDate = beginning.plusYears(LIFESPAN).minusDays(1);
    }
}
