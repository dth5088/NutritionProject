/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

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
    String group;           // food group
    String ds;              // BL = Branded Food Products or SR = Standard Release
    HashMap<String,String> map;
    
    public USDAFood(String[] arr) throws JSONException {
        map = new HashMap<>();
        for (String index : arr)
        {
            String[] splitString = index.split(":");
            String key = splitString[0];
            String value = splitString[1];
            map.put(key, value);
            switch(key) {
                case "ndbno":
                    ndbno = value;
                    break;
                case "name":
                    name = value;
                    break;
                case "group":
                    group = value;
                    break;
                case "ds":
                    ds = value;
                    break;
            }
        }
    }
    
    public USDAFood(JSONObject foodObject) throws JSONException {
        map = new HashMap<>();
        Iterator it = foodObject.keys();
        while(it.hasNext())
        {
            
            Object key = it.next();
            Object value = foodObject.get(key.toString());
            map.put(key.toString(), value.toString());
            switch(key.toString()) {
                case "ndbno":
                    ndbno = value.toString();
                    break;
                case "name":
                    name = value.toString();
                    break;
                case "group":
                    group = value.toString();
                    break;
                case "ds":
                    ds = value.toString();
                    break;
            }
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
    
    public String getNDBNO() {
        return ndbno;
    }
    
    public String toString() {
        return String.format("%s %s %s %s", "name:",name,"ndbno:",ndbno);
    }
}
