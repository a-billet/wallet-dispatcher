import model.GiftCard;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class TestGiftCards {

    private final static int USER_ID = 1;
    private final static int WALLET_ID = 1;

    @Test
    public void testVoucherValidity() throws ParseException {
        GiftCard giftCard = new GiftCard(100, "2020-09-16");
        Assert.assertTrue(giftCard.isExpired());

        giftCard = new GiftCard(50, "2021-09-16");
        Assert.assertFalse(giftCard.isExpired());
    }

    @Test
    public void testUserBalance() throws ParseException {
        User user = new User(USER_ID);
        user.addVoucher(WALLET_ID, new GiftCard(100, "2021-09-16"));
        Assert.assertEquals(100, user.getWallets().get(WALLET_ID).getBalance());

        user.addVoucher(1, new GiftCard(200, "2021-09-16"));
        Assert.assertEquals(300, user.getWallets().get(WALLET_ID).getBalance());
    }
}
