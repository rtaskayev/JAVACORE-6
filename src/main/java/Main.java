import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.xml";
        String outputJsonFileName = "data2.json";

        parseXML(fileName);


    }

    //Method to parse csv into list of objects
    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();
        System.out.println("Root element: " + root.getNodeName());
        read(root);


        return list;
    }

    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                System.out.println("Name: " + node_.getNodeName());
                System.out.println("Content: " + node_.getTextContent());
                //System.out.println("Value: " + node_.getNodeValue());
                Element element = (Element) node_;
                NamedNodeMap map = element.getAttributes();
                for (int a = 0; a < map.getLength(); a++) {
                    String attrName = map.item(a).getNodeName();
                    String attrValue = map.item(a).getNodeValue();
                    System.out.println("Attribute: " + attrName + "; value: " + attrValue);
                }
                read(node_);
            }

        }
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
