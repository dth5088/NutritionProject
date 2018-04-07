/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class USDAFood {
    String name;            // food name
    String ndbno;           // foods NDB Number
    String group = "";           // food group
    String ds = "";              // BL = Branded Food Products or SR = Standard Release
    String upc;
    String sd = "";              // short description
    String sn = "";              // scientific name
    String cn = "";              // commerical name
    String manu = "";            // manufacturer
    HashMap<String,String> map;
    ArrayList<USDANutrient> nutrients = new ArrayList<>();
    
    public USDAFood(String[] arr) throws JSONException {
        map = new HashMap<>();
        for (String index : arr)
        {
            System.out.println(index);
            String[] splitString = index.split(":");
            String key = splitString[0];
            String value = splitString[1];
            map.put(key, value);
            switch(key) {
                case "ndbno":
                    ndbno = value;
                    break;
                case "name":
                    parseName(value);
                    break;
                case "group":
                    group = value;
                    break;
                case "ds":
                    ds = value;
                    break;
                case "sd":
                    sd = value;
                    break;
                case "sn":
                    sn = value;
                    break;
                case "cn":
                    cn = value;
                    break;
                case "manufacturer":
                    manu = value;
                    break;
                case "upc":
                    upc = value;
                    break;
            }
        }
    }
   
    
    public USDAFood(JSONObject foodObject) {
        
        try {
            map = new HashMap<>();
            ndbno = foodObject.get("ndbno").toString();
            map.put("ndbno",ndbno);
            String str = foodObject.get("name").toString();
            parseName(str);
            map.put("name",name);
            map.put("upc",upc);
            if(foodObject.has("group"))
            {
                group += foodObject.get("group").toString();
                map.put("group", group);
            }
            if(foodObject.has("sn"))
            {
                System.out.println("Scientific Name: " + " " +foodObject.get("sn"));
                sn += foodObject.get("sn").toString();
                map.put("sn",sn);
            }
            if (foodObject.has("cn")){
                System.out.println("Commercial Name: " + " " +foodObject.get("cn"));
                cn += foodObject.get("cn").toString();
                map.put("cn",cn);
            }
            if(foodObject.has("manu")){
                System.out.println("Manufacturer: " + " " +foodObject.get("manu"));
                manu += foodObject.get("manu").toString();
                map.put("manufacturer",manu);
            }
            if(foodObject.has("sd"))
            {
                System.out.println("Short Discription: " + " " + foodObject.get("sd"));
                sd += foodObject.get("sd").toString();
                map.put("sd",sd);
            }
            if(foodObject.has("nutrients")) {
                JSONArray nutrientArray = foodObject.getJSONArray("nutrients");
                for(int i =0; i < nutrientArray.length();i++) {
                    nutrients.add(new USDANutrient(nutrientArray.getJSONObject(i)));
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    public HashMap.Entry<String,String> getEntry(String key) {
        for(HashMap.Entry<String,String> entry : map.entrySet())
        {
            if(entry.getKey().equalsIgnoreCase(key))
                return entry;
        }
        return null;
    }
    public String getFoodName() {
        return name;
    }
    
    public ArrayList<USDANutrient> getNutrients() {
        return nutrients;
    }
    
    public String getNDBNO() {
        return ndbno;
    }
    
    public String getManufacturer() {
        return manu;
    }
    
    public String getCommercialName() {
        return cn;
    }
    
    public String getShortDiscription() {
        return sd;
    }
    
    public String getGroup() {
        return group;
    }
    
    public String getScientificName() {
        return sn;
    }
    
    public String getUPC() {
        return upc;
    }
    
    public String toString() {
        String str = String.format("%s %s %s %s", "name:",name,"ndbno:",ndbno);
        return str;
    }

    private void parseName(String value) {
        String[] splitName = value.split(",");
        String temp = "";
        for(String str : splitName) 
        {
            
            if(str.toLowerCase().contains("upc:"))
            {
                String[] upcArray = str.split(":");
                upc = upcArray[1];
            }
            else {
                if(str.length() > 4)
                    temp += str;
            }
        }
        name = temp;
    }
}
