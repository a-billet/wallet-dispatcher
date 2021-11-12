package model;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

public class DataSet implements Jsonable {
    private final Collection<Company> companies;
    private final Collection<User> users;
    private final Collection<Distribution> distributions;

    public DataSet(Collection<Company> companies, Collection<User> users, Collection<Distribution> distributions) {
        this.companies = companies;
        this.users = users;
        this.distributions = distributions;
    }

    public Collection<Company> getCompanies() {
        return companies;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public Collection<Distribution> getDistributions() {
        return distributions;
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
        json.put("companies", this.getCompanies());
        json.put("users", this.getUsers());
        json.put("distributions", this.getDistributions());
        json.toJson(writer);
    }
}
