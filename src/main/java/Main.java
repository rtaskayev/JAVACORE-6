import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String fileName = "data.xml";
        String outputJsonFileName = "data2.json";

        List<Employee> list = parseXML(fileName);
        System.out.println("Parsed objects from XML - " + list);

        String json = listToJson(list); // Converting list of objects to Json data
        System.out.println("Json data - " + json);

        writeString(json, outputJsonFileName); // Write json data to file

    }

    //Method to parse xml into list of objects
    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();
        System.out.println("Root element: " + root.getNodeName());
        list = read(root, list);

        return list;
    }

    // Method to read xml document and parse into list of objects
    private static List<Employee> read(Node node, List<Employee> empList) {

        NodeList nodeList = node.getChildNodes();
        List<Employee> list = empList;
        Map<String, String> map_ = new HashMap<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                System.out.println("Name: " + node_.getNodeName());
                Element element = (Element) node_;
                NamedNodeMap map = element.getAttributes();
                // Checking if node is employee and it has at least 1 value inside
                if (node_.getNodeName().equals("employee") && map.getLength() > 0) {
                    for (int a = 0; a < map.getLength(); a++) {
                        String attrName = map.item(a).getNodeName();
                        String attrValue = map.item(a).getNodeValue();
                        System.out.println("Attribute: " + attrName + "; value: " + attrValue);
                        map_.put(attrName, attrValue); // Inserting data into map
                    }
                    Employee emp = new Employee(map_); // Creating new employee object from created map
                    list.add(emp);
                    map_.clear();
                }
                read(node_, list);
            }
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
