/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class ReportParser {
    public ArrayList<USDANutrient> nutrientsList;
    
    public ReportParser(String jsonResponseString) throws JSONException{
        nutrientsList = new ArrayList<>();
        JSONObject responseObject = new JSONObject(jsonResponseString);
        JSONObject report = (JSONObject) responseObject.get("report");
        JSONObject food = (JSONObject) report.get("food");
        JSONArray nutrients = (JSONArray) food.get("nutrients");
        for(int i = 0; i < nutrients.length();i++) {
           nutrientsList.add(new USDANutrient((JSONObject) nutrients.get(i)));
        }
    }
    
    public ArrayList<USDANutrient> getNutrients() {
        return nutrientsList;
    }
    public String toString() {
        String str = "";
        for(USDANutrient nutrient : nutrientsList) {
            str += nutrient + "\n";
        }
        return str;
    }
}
