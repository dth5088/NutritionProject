/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import nutrition.app.Parsers.Measure;
import nutrition.app.Parsers.USDANutrient;

/**
 *
 * @author dth5088
 */
public class NutrientRow {
    private String nutrientName, quantity, label, measure;
    private USDANutrient nutrient;
    
//    public NutrientRow(String nutrientName, Measure measure) {
//        this.nutrientName = nutrientName;
//        this.quantity = measure.getQty();
//        this.label = measure.getLabel();
//        this.measure = measure.getValue();
//    }
    
    public NutrientRow(USDANutrient nutrient, Measure measure) {
        this.nutrientName = nutrient.getName();
        this.quantity = measure.getQty().replace(".0","");
        this.label = measure.getLabel();
        this.nutrient = nutrient;
        this.measure = measure.getValue();
    }
    
    public String getNutrientName() {
        return nutrientName;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getMeasure() {
        return measure;
    }
    
    public USDANutrient getNutrient() {
        return nutrient;
    }
}
