import java.util.Map;

public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {

    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    // Constructor to create object from map data structure
    public Employee(Map<String, String> params) {
        this.id = Long.parseLong(params.get("id"));
        this.firstName = params.get("firstName");
        this.lastName = params.get("lastName");
        this.country = params.get("country");
        this.age = Integer.parseInt(params.get("age"));

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}