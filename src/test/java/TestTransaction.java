import controller.Dispatcher;
import model.*;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestTransaction {
    @Test
    public void testSimpleTransaction() throws ParseException, Dispatcher.NotEnoughFundsException, Dispatcher.UnknownWalletException {
        Company company = new Company(1, "companyOne");
        company.setBalance(1000);
        User user = new User(1);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.distribute(company, user, new GiftCard(150, "2021-11-02"));
        int walletId = 1;
        assertEquals(150, user.getWallets().get(walletId).getBalance());
        assertEquals(850, company.getBalance());
    }

    @Test
    public void testTransactionFailed() throws ParseException {

        Company company = new Company(1, "companyOne");
        company.setBalance(1000);
        User user = new User(1);

        Dispatcher dispatcher = new Dispatcher();
        GiftCard giftCard = new GiftCard(1500, "2021-11-02");
        assertThrows(Dispatcher.NotEnoughFundsException.class, () -> dispatcher.distribute(company, user, giftCard));
    }

    @Test
    public void testComplexTransactions() throws ParseException, Dispatcher.NotEnoughFundsException, Dispatcher.UnknownWalletException {

        int WALLET_ID_ONE = 1;
        int WALLET_ID_TWO = 2;

        Company companyOne = new Company(1, "companyOne");
        companyOne.setBalance(1000);
        Company companyTwo = new Company(1, "companyOne");
        companyTwo.setBalance(3000);

        User userOne = new User(1);
        Wallet wallet = new Wallet(WALLET_ID_ONE);
        wallet.addVoucher(new GiftCard(100, "2020-09-16"));
        userOne.addWallet(wallet);

        User userTwo = new User(2);
        User userThree = new User(3);

        Dispatcher dispatcher = new Dispatcher();

        dispatcher.distribute(companyOne, userOne, new GiftCard(50, "2020-09-16"));
        dispatcher.distribute(companyOne, userTwo, new GiftCard(100, "2020-08-01"));
        dispatcher.distribute(companyTwo, userThree, new GiftCard(1000, "2020-05-01"));
        dispatcher.distribute(companyOne, userOne, new MealVoucher(250, "2020-05-01"));

        assertEquals(150, userOne.getWallets().get(WALLET_ID_ONE).getBalance());
        assertEquals(250, userOne.getWallets().get(WALLET_ID_TWO).getBalance());
        assertEquals(1000, userThree.getWallets().get(WALLET_ID_ONE).getBalance());
        assertEquals(1000, userThree.getWallets().get(WALLET_ID_ONE).getBalance());
        assertEquals(600, companyOne.getBalance());
        assertEquals(2000, companyTwo.getBalance());
    }
}
