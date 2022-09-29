import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {


    @BeforeAll
    public static void started() {
        System.out.println("Tests Started");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("All tests completed!");
    }

    @AfterEach
    public void finished() {
        System.out.println("Test completed");
    }

    @BeforeEach
    public void init() {
        System.out.println("Test started");
    }

    @Test
    public void employeeFromMapTest() {

        Employee expected = new Employee(1, "Ruslan", "Taskayev", "POL", 33);

        Map<String, String> map = new HashMap<>() {{
           put("id", "1");
           put("firstName", "Ruslan");
           put("lastName", "Taskayev");
           put("country", "POL");
           put("age", "33");
        }};

        Employee result = new Employee(map);

        System.out.println("Expected - " + expected);
        System.out.println("Result - " + result);

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void employeeListToJsonTest() {

        Employee employee = new Employee(1, "Ruslan", "Taskayev", "POL", 33);
        List<Employee> list = new ArrayList<>();
        list.add(employee);

        String expected = "[{\"id\":1,\"firstName\":\"Ruslan\",\"lastName\":\"Taskayev\",\"country\":\"POL\",\"age\":33}]";
        String result = Main.listToJson(list);

        System.out.println("Expected - " + expected);
        System.out.println("Result - " + result);

        assertEquals(expected, result);
    }

    @Test
    public void fileWasCreatedTest() {

        String stringToWrite = "111";
        String fileName = "dataTest.csv";
        String stringFromFile = null;

        Main.writeString(stringToWrite, fileName);

        try {
            stringFromFile = Files.readString(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(new File(fileName).exists());
        assertEquals(stringToWrite, stringFromFile);

        try {
            Files.delete(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeToFileDoNotExistTest() {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "dataTest.csv";

        var expected = FileNotFoundException.class;

        assertThrows(expected,
                () -> Main.parseCSV(columnMapping, fileName));

    }

}
