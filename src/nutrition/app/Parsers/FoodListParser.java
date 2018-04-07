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
                    if(!line.isEmpty())
                    {
                        String[] splitLine = line.split(",");
                        String foodName = splitLine[0];
                        String manufacturer = splitLine[1];
                        String portionSizeString = splitLine[2];
                        int portionSize = Integer.parseInt(portionSizeString);

                        String unitString = splitLine[3];

                        String caloriesString = splitLine[4];
                        double calories = Double.parseDouble(caloriesString);

                        String carbString = splitLine[5];
                        double carbs = Double.parseDouble(carbString);

                        String proteinString = splitLine[6];
                        double protein = Double.parseDouble(proteinString);

                        String fatString = splitLine[7];
                        double fat = Double.parseDouble(fatString);

                        Food food = new Food(foodName,manufacturer,portionSize,unitString,calories,carbs,protein,fat);
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
