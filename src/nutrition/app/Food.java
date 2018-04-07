/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.util.ArrayList;
import java.util.HashMap;
import nutrition.app.Parsers.USDAFood;
import nutrition.app.Parsers.USDANutrient;

/**
 *
 * @author dth5088
 */
public class Food {
    String name;
    int portionSize;
    Macronutrients macro;
    UnitOfMeasurement unitOfMeasurement;
    String uom;
    String manufacturer = "";
    
    public Food(String name, int portionSize, double calories, double carbs, double protein, double fat)
    {
        this.name = name;
        this.portionSize = portionSize;
        uom = "";
        macro = new Macronutrients(calories, carbs, protein, fat);
    }
    
    public Food(String name, String manufacturer, int portionSize, double calories, double carbs, double protein, double fat)
    {
        this.name = name;
        this.portionSize = portionSize;
        uom = "";
        macro = new Macronutrients(calories, carbs, protein, fat);
        this.manufacturer = manufacturer;
    }
    
    public Food(String name, String manufacturer, int portionSize, String uom, double calories, double carbs, double protein, double fat) {
        this.name = name;
        this.portionSize = portionSize;
        this.uom = uom;
        this.manufacturer = manufacturer;
        macro = new Macronutrients(calories, carbs, protein, fat);
        setUnitOfMeasurement(uom);
    }
    
    public Food(String name, String manufacturer, int portionSize, String uom, Macronutrients macro) {
        this.name = name;
        this.portionSize = portionSize;
        this.macro = macro;
        this.manufacturer = manufacturer;
        setUnitOfMeasurement(uom);
    }
    
    public Food(USDAFood food) {
        this.name = food.getFoodName();
        this.portionSize = 1;
        double fat = 0.0, carbs = 0.0, protein = 0.0, calories = 0.0;
        for(USDANutrient nutrient : food.getNutrients())
        {
            String nutrientName = nutrient.getName();
            if(nutrient.getName().contains("fat"))
                fat = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
            else if(nutrient.getName().contains("Carbohydrate"))
                carbs = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
            else if(nutrient.getName().contains("Protein"))
                protein = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
            else if(nutrient.getName().contains("Energy"))
                calories = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
            this.macro = new Macronutrients(calories, carbs, protein, fat);
            this.uom = nutrient.getDefaultMeasure().getLabel();
        }
        this.manufacturer += food.getManufacturer();
    }
    
    
    private void setUnitOfMeasurement(String stringUnitOfMeasurement) {
        switch(stringUnitOfMeasurement.toLowerCase())
        {
            case "oz":
                unitOfMeasurement = UnitOfMeasurement.OUNCE;
                break;
            case "gram":
                unitOfMeasurement = UnitOfMeasurement.GRAM;
                break;
            case "cup":
                this.unitOfMeasurement = UnitOfMeasurement.CUP;
                break;
            case "tsp":
                this.unitOfMeasurement = UnitOfMeasurement.TEASPOON;
                break;
            case "tbsp":
                this.unitOfMeasurement = UnitOfMeasurement.TABLESPOON;
                break;
            case "bar":
                this.unitOfMeasurement = UnitOfMeasurement.BAR;
                break;
            case "mg":
                
            default:
                System.out.println(stringUnitOfMeasurement);
                break;
        }
    }
    
    public HashMap<String,Object> getMapped() {
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.putIfAbsent("name", name);
        returnMap.putIfAbsent("unit", uom);
        returnMap.putIfAbsent("portionSize", portionSize);
        returnMap.putIfAbsent("calories", macro.getCalories());
        returnMap.putIfAbsent("carbs", macro.getCarbohydrates());
        returnMap.putIfAbsent("protein", macro.getProtein());
        returnMap.putIfAbsent("fat", macro.getFat());
        returnMap.putIfAbsent("manufacturer", manufacturer);
        
        return returnMap;
    }
    
    public String toString() {
        return String.format("%s %s",name, macro);
    }
}
