import model.MealVoucher;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class TestMealVouchers {

    @Test
    public void testVoucherValidity() throws ParseException {
        MealVoucher voucher = new MealVoucher(100, "2021-11-02");
        Assert.assertFalse(voucher.isExpired());
        Assert.assertEquals("2022-02-28", voucher.getEndDate());

        // Test bisextile year
        MealVoucher anotherVoucher = new MealVoucher(100, "2023-01-25");
        Assert.assertEquals("2024-02-29", anotherVoucher.getEndDate());
    }
}
