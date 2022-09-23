import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Main {

    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String outputJsonFileName = "new_data.json";

        List<Employee> list = parseCSV(columnMapping, fileName); // Parsing CSV file
        System.out.println("Parsed objects from CSV - " + list);

        String json = listToJson(list); // Converting list of objects to Json data
        System.out.println("Json data - " + json);

        writeString(json, outputJsonFileName); // Write json data to file

    }

    // Method to parse csv into list of objects
    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {

        List<Employee> list = new ArrayList<>();

        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            list = csv.parse();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method to transfer list of objects to json
    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(list);
    }

    // Method to write json data into file
    public static void writeString(String stringToWrite, String fileName) {
        try (FileWriter file = new
                FileWriter(fileName)) {
            file.write(stringToWrite);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
