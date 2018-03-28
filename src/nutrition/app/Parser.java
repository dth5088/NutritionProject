/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class Parser {
    String jsonString = "";
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONArray nutritionArray;
    Macronutrients macros;
    
    
    public Parser(String jsonString) throws JSONException {
        jsonArray = new JSONArray("["+jsonString+"]");
        jsonObject = jsonArray.getJSONObject(0);
        JSONObject nutrients = jsonObject.optJSONObject("totalNutrients");
        double calories = jsonObject.getDouble("calories");
        double fat = getFat(nutrients);
        double carbs = getCarbohydrates(nutrients);
        double protein = getProtein(nutrients);
        macros = new Macronutrients(calories, carbs, protein, fat);
        
    }
    
    private Double getFat(JSONObject nutrients)  {
        Double ret = 0.0;
        try {
            JSONObject fat = (JSONObject)nutrients.get("FAT");
            ret = fat.getDouble("quantity");
        } catch (JSONException ignore) {
            
        }
        
        return ret;
    }
    
    private Double getCarbohydrates(JSONObject nutrients){
        Double ret = 0.0;
        try {
            JSONObject carbs = (JSONObject)nutrients.get("CHOCDF");
        
             ret = carbs.getDouble("quantity");
        } catch (JSONException ignore) {}
        return ret;
    }
    
    private Double getProtein(JSONObject nutrients) throws JSONException {
        Double ret = 0.0;
        try {
            
            JSONObject protein = (JSONObject) nutrients.getJSONObject("PROCNT");
            ret = protein.getDouble("quantity");
        } catch(JSONException ignore) {}
        return ret;
    }
    
    public String getMacros() {
        return macros.toString();
    }
}
