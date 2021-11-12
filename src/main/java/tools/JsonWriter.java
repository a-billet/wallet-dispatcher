package tools;

import com.github.cliftonlabs.json_simple.Jsoner;
import model.DataSet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
    public static void write(DataSet dataSet, String fileName) {
        String json = Jsoner.serialize(dataSet);
        json = Jsoner.prettyPrint(json);

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
