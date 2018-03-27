/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

/**
 *
 * @author dth5088
 */
public class NutrientInfo {
    String uri;
    String label;
    float quantity;
    String unit;
    
    public NutrientInfo(String uri, String label, float quantity, String unit) {
        this.uri = uri;
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }
    
    public String getLabel() {
        return label;
    }
    
    public float getQuantity() {
        return quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    @Override
    public String toString() {
        return String.format("%s %5.2f %s", label, quantity, unit);
    }
}
