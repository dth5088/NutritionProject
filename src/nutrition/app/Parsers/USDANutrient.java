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
public class USDANutrient {
    String name;
    String nutrientId;
    String derivation;
    String value;
    String unit;
    ArrayList<Measure> measures;
    
    public USDANutrient(JSONObject nutrient) throws JSONException {
        measures = new ArrayList<>();
        this.name = nutrient.get("name").toString();
        this.unit = nutrient.get("unit").toString();
        this.nutrientId = nutrient.get("nutrient_id").toString();
        this.derivation = nutrient.get("derivation").toString();
        JSONArray arr = (JSONArray)nutrient.get("measures");
        for(int i = 0; i < arr.length();i++)
        {
            measures.add(new Measure((JSONObject) arr.get(i)));
        }
    }
    public String getName() {
        return name;
    }
    public String getUnit() {
        return unit;
    }
    public String getNutrientId() {
        return nutrientId;
    }
    public String getDerivation() {
        return derivation;
    }
    
    public ArrayList<Measure> getMeasures() {
        return measures;
    }
    public String toString() {
        String str = name + " (" +unit+") \n";
        for(Measure measure : measures)
        {
            str += measure.qty + " " + measure.label + " has " + measure.value;
            switch(unit)
            {
                case "g":
                    str += " grams\n";
                    break;
                case "mg":
                    str += " milligrams\n";
                    break;
            }
        }
        return str;
    }
}
