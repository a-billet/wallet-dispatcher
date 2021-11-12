package tools;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import model.*;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    public static JsonObject parse(String file) throws URISyntaxException {
        Reader reader = null;
        try {
            URL url = JsonParser.class.getResource(file);
            Path path = Paths.get(url.toURI());
            reader = Files.newBufferedReader(path);
            return (JsonObject) Jsoner.deserialize(reader);
        }
        catch (IOException | JsonException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Integer, Company> getCompanies(JsonObject json) {
        Map<Integer, Company> companyMap = new HashMap<>();
        JsonArray companies = (JsonArray) json.get("companies");
        companies.forEach(entry -> {
            JsonObject companyEntry = (JsonObject) entry;
            BigDecimal id = (BigDecimal) companyEntry.get("id");
            Company company = new Company(id.intValue(), (String) companyEntry.get("name"));
            company.setBalance(((BigDecimal) companyEntry.get("balance")).intValue());
            companyMap.put(id.intValue(), company);
        });
        return companyMap;
    }

    public static Map<Integer, User> getUsers(JsonObject json) {
        Map<Integer, User> userMap = new HashMap<>();
        JsonArray users = (JsonArray) json.get("users");
        users.forEach(entry -> {
            JsonObject userEntry = (JsonObject) entry;
            User user = buildUser(userEntry);
            userMap.put(user.getId(), user);
        });
        return userMap;
    }

    private static User buildUser(JsonObject userEntry) {
        BigDecimal id = (BigDecimal) userEntry.get("id");
        User user = new User(id.intValue());

        JsonArray balances = (JsonArray) userEntry.get("balance");
        balances.forEach(entry -> {
            JsonObject walletEntry = (JsonObject) entry;
            int walletId = ((BigDecimal) walletEntry.get("wallet_id")).intValue();
            int balance = ((BigDecimal) walletEntry.get("amount")).intValue();
            Wallet wallet = new Wallet(walletId);
            try {
                Voucher giftCard = new GiftCard(balance, "2021-11-02"); // ???
                wallet.addVoucher(giftCard);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            user.addWallet(wallet);
        });
        return user;
    }
}
