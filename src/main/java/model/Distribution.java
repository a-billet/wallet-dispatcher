package model;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

public class Distribution implements Jsonable {
    public final int id;
    public final int amount;
    public final int companyId;
    public final int userId;
    public final int walletId;
    public String startDate;
    public String endDate;

    public Distribution(int transId, int amount, int companyId, int userId, int walletId, String beginDate, String endDate) {
        this.id = transId;
        this.amount = amount;
        this.companyId = companyId;
        this.userId = userId;
        this.walletId = walletId;
        this.startDate = beginDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getUserId() {
        return userId;
    }

    public int getWalletId() {
        return walletId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distribution that = (Distribution) o;
        return id == that.id && amount == that.amount && companyId == that.companyId && userId == that.userId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, companyId, userId, startDate, endDate);
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
        json.put("wallet_id", this.getWalletId());
        json.put("amount", this.getAmount());
        json.put("start_date", this.getStartDate());
        json.put("end_date", this.getEndDate());
        json.put("company_id", this.getCompanyId());
        json.put("user_id", this.getUserId());
        json.toJson(writer);
    }
}
