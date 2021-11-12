package model;

import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private final int id;
    private List<Voucher> vouchers;

    public Wallet(int id) {
        this.id = id;
        vouchers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public int getBalance() {
        int sum = 0;
        for (Voucher voucher : vouchers) {
            sum += voucher.getAmount();
        }
        return sum;
    }
}
