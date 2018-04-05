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
import nutrition.app.Food;
import nutrition.app.User;

/**
 *
 * @author dth5088
 */
public class foodListParser {
    User user;
    static final String fileName = "foodList.txt";
    static final String workingDirectory = System.getProperty("user.dir");
    File file;
    ArrayList<Food> list;
    
    public foodListParser(User user) throws IOException {
        this.user = user;
        list = new ArrayList<>();
        file = new File(workingDirectory + File.separator + fileName);
        if(file.createNewFile())
            System.out.println("File is created!");
        else 
            System.out.println("File already exists");
    }
    
    private void parseTextFile() {
        try {
            Stream<String> lines = Files.lines(Paths.get(workingDirectory,fileName));
            lines.forEach((line) -> {
                String[] splitLine = line.split(",");
                String foodName = splitLine[0];
                String portionSizeString = splitLine[1];
                int portionSize = Integer.parseInt(portionSizeString);
                
                String unitString = splitLine[2];
                
                String caloriesString = splitLine[3];
                double calories = Double.parseDouble(caloriesString);
                
                String carbString = splitLine[4];
                double carbs = Double.parseDouble(carbString);
                
                String proteinString = splitLine[5];
                double protein = Double.parseDouble(proteinString);
                
                String fatString = splitLine[6];
                double fat = Double.parseDouble(fatString);
                
                Food food = new Food(foodName,portionSize,unitString,calories,carbs,protein,fat);
                list.add(food);
            });
            
        } catch (IOException ex) {
          
        }
    }
    
    public void addFood(Food food) {
        list.add(food);
    }
    
    public void closeParser() throws IOException {
        if(!list.isEmpty())
        {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for(Food food : list) {
                String lineString = "";
                for(HashMap.Entry<String,Object> entry : food.getMapped())
                {
                    
                }
            }
        }
    }
}
