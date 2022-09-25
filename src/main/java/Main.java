import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        String fileName = "new_data.json";

        String json = readString(fileName);
        System.out.println("JSON String is - " + json);

        List<Employee> list = jsonToList(json);

        printList(list);


    }

    public static void printList(List<Employee> list) {

        for (int i = 0; i < list.size(); i++) {
            System.out.println("Employee [" + (i + 1) + "] = " + list.get(i));
        }

    }

    public static String readString(String fileName) throws IOException {
        FileInputStream fileInputStream = null;
        String data="";
        StringBuilder stringBuffer = new StringBuilder();
        try{
            fileInputStream=new FileInputStream(fileName);
            int i;
            while((i=fileInputStream.read())!=-1)
            {
                stringBuffer.append((char)i);
            }
            data = stringBuffer.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
        return data;
    }

    public static List<Employee> jsonToList(String json) throws ParseException {

        List<Employee> list = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(json);

        for (int i = 0; i < array.size(); i++) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONObject jsonObject = (JSONObject) array.get(i);
            Employee employee = gson.fromJson(String.valueOf(jsonObject), Employee.class);

            list.add(employee);
        }
        return list;
    }


}
