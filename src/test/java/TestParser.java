import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import controller.Dispatcher;
import model.*;
import org.junit.Assert;
import org.junit.Test;
import tools.JsonParser;
import tools.JsonWriter;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestParser {
    private final String INPUT_FILE = "/input.json";
    private final String OUTPUT_FILE = "/output.json";

    @Test
    public void testParser() throws ParseException, URISyntaxException {
        JsonObject jsonData = JsonParser.parse(INPUT_FILE);
        Assert.assertEquals(2, JsonParser.getCompanies(jsonData).size());
        Assert.assertEquals(3, JsonParser.getUsers(jsonData).size());

        Company company = new Company(1, "company1");
        company.setBalance(1000);
        Assert.assertTrue(JsonParser.getCompanies(jsonData).containsValue(company));

        User user = new User(1);
        GiftCard giftCard = new GiftCard(100, "2021-11-02");
        user.addVoucher(1, giftCard);

        Map<Integer, User> users = JsonParser.getUsers(jsonData);
        Assert.assertTrue(users.containsValue(user));
        Assert.assertEquals(100, users.get(1).getWallets().get(1).getBalance());
    }

    @Test
    public void testWriterIdempotence() throws ParseException, URISyntaxException {
        JsonObject jsonData = JsonParser.parse(INPUT_FILE);
        Map<Integer, Company> companies = JsonParser.getCompanies(jsonData);
        Map<Integer, User> users = JsonParser.getUsers(jsonData);
        List<Distribution> distributions = getDistributionsFromJson(jsonData);

        DataSet dataSet = new DataSet(companies.values(), users.values(), distributions);
        JsonWriter.write(dataSet, OUTPUT_FILE);

        // parse the generated file to compare against the original values
        JsonObject generatedJson = JsonParser.parse(OUTPUT_FILE);

        Assert.assertEquals(2, JsonParser.getCompanies(generatedJson).size());
        Assert.assertEquals(3, JsonParser.getUsers(generatedJson).size());

        User user = new User(1);
        GiftCard giftCard = new GiftCard(100, "2021-11-02");
        user.addVoucher(1, giftCard);
        Assert.assertTrue(JsonParser.getUsers(generatedJson).containsValue(user));
    }

    @Test
    public void testGeneratedDistributions() throws ParseException, URISyntaxException, Dispatcher.NotEnoughFundsException, Dispatcher.UnknownWalletException {
        JsonObject jsonData = JsonParser.parse(INPUT_FILE);
        Map<Integer, Company> companies = JsonParser.getCompanies(jsonData);
        Map<Integer, User> users = JsonParser.getUsers(jsonData);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.distribute(companies.get(1), users.get(1), new GiftCard(50, "2020-09-16"));
        dispatcher.distribute(companies.get(1), users.get(2), new GiftCard(100, "2020-08-01"));
        dispatcher.distribute(companies.get(2), users.get(3), new GiftCard(1000, "2020-05-01"));

        DataSet dataSet = new DataSet(companies.values(), users.values(), dispatcher.getDistributions());
        JsonWriter.write(dataSet, OUTPUT_FILE);

        JsonObject generatedData = JsonParser.parse(OUTPUT_FILE);
        List<Distribution> distributions = getDistributionsFromJson(generatedData);

        Distribution firstTransaction = new Distribution(1, 50, 1, 1, 1, "2020-09-16", "2021-09-15");
        Assert.assertEquals(3, distributions.size());
        Assert.assertTrue(distributions.contains(firstTransaction));
    }

    public static List<Distribution> getDistributionsFromJson(JsonObject json) {
        List<Distribution> distributionsList = new ArrayList<>();
        JsonArray distributions = (JsonArray) json.get("distributions");
        distributions.forEach(entry -> {
            JsonObject project = (JsonObject) entry;
            BigDecimal id = (BigDecimal) project.get("id");
            BigDecimal walletId = (BigDecimal) project.get("wallet_id");
            BigDecimal amount = (BigDecimal) project.get("amount");
            String start_date = (String) project.get("start_date");
            String end_date = (String) project.get("end_date");
            BigDecimal companyId = (BigDecimal) project.get("company_id");
            BigDecimal userId = (BigDecimal) project.get("user_id");
            Distribution distribution = new Distribution(id.intValue(), amount.intValue(), companyId.intValue(), userId.intValue(), walletId.intValue(), start_date, end_date);
            distributionsList.add(distribution);
        });

        return distributionsList;
    }
}
