package model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User implements Jsonable {
    private final int id;
    private Map<Integer, Wallet> wallets = new HashMap<>();

    public User(int userId) {
        this.id = userId;
    }

    public void addWallet(Wallet wallet) {
        wallets.put(wallet.getId(), wallet);
    }

    public void addVoucher(int walletId, Voucher voucher) {
        Wallet wallet = wallets.get(walletId);
        if (wallet != null) {
            wallet.addVoucher(voucher);
        }
        else {
            Wallet newWallet = new Wallet(walletId);
            newWallet.addVoucher(voucher);
            wallets.put(walletId, newWallet);
        }
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Wallet> getWallets() {
        return wallets;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(wallets.keySet(), user.wallets.keySet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wallets);
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        }
        catch (final IOException e) {
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("id", this.getId());
        JsonArray balances = new JsonArray();
        this.getWallets().forEach((k, v) -> {
            JsonObject balance = new JsonObject();
            balance.put("wallet_id", k);
            balance.put("amount", v.getBalance());
            balances.add(balance);
        });
        json.put("balance", balances);
        json.toJson(writer);
    }
}
