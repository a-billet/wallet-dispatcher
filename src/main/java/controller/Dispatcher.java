package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Dispatcher {
    private AtomicInteger counter = new AtomicInteger(0);
    private List<Distribution> distributions = new ArrayList<>();

    public void distribute(Company company, User user, Voucher voucher) throws UnknownWalletException, NotEnoughFundsException {
        int balance = company.getBalance();
        int amount = voucher.getAmount();

        int walletId;
        try {
            checkCompanyBalance(company, balance, amount);
            walletId = getWalletId(voucher);
        }
        catch (NotEnoughFundsException | UnknownWalletException e) {
            throw e;
        }

        user.addVoucher(walletId, voucher);
        company.setBalance(balance - amount);
        Distribution distribution = new Distribution(counter.incrementAndGet(), amount, company.getId(), user.getId(), walletId, voucher.getBeginDate(), voucher.getEndDate());
        distributions.add(distribution);
    }

    private void checkCompanyBalance(Company company, int balance, int amount) throws NotEnoughFundsException {
        if (balance < amount) {
            throw new NotEnoughFundsException(company + " doesn't have enough funds.");
        }
    }

    private int getWalletId(Voucher voucher) throws UnknownWalletException {
        if (voucher instanceof GiftCard) {
            return 1;
        }
        else if (voucher instanceof MealVoucher) {
            return 2;
        }
        throw new UnknownWalletException();
    }

    public List<Distribution> getDistributions() {
        return distributions;
    }

    public static class NotEnoughFundsException extends Exception {
        public NotEnoughFundsException(String message) {
            super(message);
        }
    }

    public static class UnknownWalletException extends Exception {
        public UnknownWalletException() {
            super("Unknown wallet");
        }
    }
}
