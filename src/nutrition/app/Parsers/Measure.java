/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class Measure {
    String qty, label, eqv, value, eunit;
    
    public Measure(JSONObject measureObject) throws JSONException{
        this.qty = measureObject.get("qty").toString();
        this.label = measureObject.get("label").toString();
        this.eqv = measureObject.get("eqv").toString();
        this.value = measureObject.get("value").toString();
        this.eunit = measureObject.get("eunit").toString();
    }
    
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s %s %s", "qty:",qty,"label:",label,"eqv:",eqv,"value:",value,"eunit:",eunit);
    }
}
