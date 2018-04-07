/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;
import nutrition.app.Food;
import nutrition.app.User;

/**
 *
 * @author dth5088
 */
public class FoodListParser {
    User user;
    static final String fileName = "foodList.txt";
    static final String workingDirectory = System.getProperty("user.dir");
    ArrayList<Food> list;
    
    public FoodListParser(User user) throws IOException {
        this.user = user;
        list = new ArrayList<>();
        try {
            try(Stream<String> lines = Files.lines(Paths.get(workingDirectory + File.separator + fileName)))
            {
                lines.forEach((line) -> {
                    String name = "", manufacturer = "", psizeStr = "", unitStr = "", calStr = "", carbStr = "", proteinStr = "", fatStr = "";
                    Double cals = 0.0, carbs = 0.0, protein = 0.0, fat = 0.0;
                    int pSize = 0;
                    if(!line.isEmpty())
                    {
                        String[] splitLine = line.split(",");
                        for(String each : splitLine) {
                            String[] e = each.split(":");
                            String key = e[0];
                            String value = e[1];
                            
                            switch(key) {
                                case "name":
                                    name = value;
                                    break;
                                case "manufacturer":
                                    manufacturer = value;
                                    break;
                                case "portionSize":
                                    psizeStr = value;
                                    pSize = Integer.parseInt(psizeStr);
                                    break;
                                case "calories":
                                    calStr = value;
                                    cals = Double.parseDouble(value);
                                    break;
                                case "unit":
                                    unitStr = value;
                                    break;
                                case "carbs":
                                    carbStr = value;
                                    carbs = Double.parseDouble(value);
                                    break;
                                case "protein":
                                    proteinStr = value;
                                    protein = Double.parseDouble(value);
                                    break;
                                case "fat":
                                    fatStr = value;
                                    fat = Double.parseDouble(value);
                                    break;
                            }
                        }

                        Food food = new Food(name,manufacturer,pSize,unitStr,cals,carbs,protein,fat);
                        list.add(food);
                    }
                    lines.close();
                    
                });
            }
            
        } catch(IOException e) {
            
        }
    }
    
    public ArrayList<Food> getParsedFood() {
        return list;
    }
    
    public void addFoodToList(Food food) {
        list.add(food);
    }
    
    public void addFoodToTextFile(Food food) {
        try {
            File file = new File(workingDirectory + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String lineString = "";
            HashMap<String,Object> foodMap = food.getMapped();
            String separator = ",";
            String nameStr = "", manuStr = "", psizeStr = "", uomStr = "", calStr = "", carbStr = "", proteinStr = "", fatStr = ""; 
            for(HashMap.Entry<String,Object> entry : foodMap.entrySet())
            {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                String joined = String.join(":", key,value);
                switch(key) {
                    case "name":
                        nameStr += joined;
                        break;
                    case "manufacturer":
                        manuStr += joined;
                        break;
                    case "portionSize":
                        psizeStr += joined;
                        break;
                    case "unit":
                        uomStr += joined;
                        break;
                    case "calories":
                        calStr += joined;
                        break;
                    case "carbs":
                        carbStr += joined;
                        break;
                    case "protein":
                        proteinStr += joined;
                        break;
                    case "fat":
                        fatStr += joined;
                        break;
                }
            }
            lineString += (nameStr + separator + manuStr + separator + psizeStr + separator + uomStr + separator + calStr + separator +carbStr + separator + proteinStr + separator + fatStr + "\n");
            osw.write(lineString);
            osw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void closeParser() throws IOException {
        File file = new File(workingDirectory + File.separator + fileName);
        if(!list.isEmpty())
        {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for(Food food : list) {
                String lineString = "";
                HashMap<String,Object> foodMap = food.getMapped();
                String separator = ",";
                for(HashMap.Entry<String,Object> entry : foodMap.entrySet())
                {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    String joined = String.join(":", key,value);
                    lineString = String.join(separator, lineString, joined);
                    
                }
                lineString += "\n";
                osw.write(lineString);
            }
            osw.close();
        }
    }
}
