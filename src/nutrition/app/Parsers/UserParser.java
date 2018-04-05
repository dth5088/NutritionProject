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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.stream.Stream;
import nutrition.app.ActivityLevel;
import nutrition.app.Gender;
import nutrition.app.Goal;
import nutrition.app.User;

/**
 * Text file will be a csv file. 
 * 
 * firstName,lastName,height,weight,dateofbirth,age,goal,activity,gender
 */
public class UserParser {
    
    static final String FILE_PATH = "./app/assets/usr.txt";
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    User user = null;
    
    
    public UserParser()
    {
        String workingDirectory = System.getProperty("user.dir");
        
        String fName = "usr.txt";
        File file = new File(workingDirectory + File.separator + fName);
        if(!file.exists())
        {
            System.out.println(file + " does not exist!");
        }
        try {
            try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
                if(lines.count() == 0)
                    return;
                lines.forEach((line) -> {
                    String[] splitLine = line.split(",");
                    if(splitLine.length > 7)
                    {
                        String firstName = splitLine[0];
                        String lastName = splitLine[1];
                        
                        Double height = Double.parseDouble(splitLine[2]);
                        Double weight = Double.parseDouble(splitLine[3]);
                        
                        String dobString = splitLine[4];
                        LocalDate dateOfBirth = LocalDate.parse(dobString, formatter);
                        
                        String goal = splitLine[6];
                        Goal fitnessGoal = getFitnessGoal(goal);
                        
                        String activity = splitLine[7];
                        ActivityLevel activityLevel = getActivityLevel(activity);
                        
                        String genderString = splitLine[8];
                        Gender gender = getGender(genderString);
                        
                        user = new User(firstName,lastName,height,weight,dateOfBirth,gender,activityLevel,fitnessGoal);
                    }
                });
            }
        } catch(IOException e) { 
            
        }
    }
    
    public UserParser(User user) {
        this.user = user;
        String outString = user.getOutputString();
        try{
             String workingDirectory = System.getProperty("user.dir");
            String fName = "usr.txt";
            File file = new File(workingDirectory + File.separator + fName);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(outString);
            osw.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public boolean matchesUser(User user) {
        return this.user == user;
    }
    private Goal getFitnessGoal(String goalString) {
        Goal returnGoal = null;
        switch(goalString.toUpperCase()) {
            case "FAT_LOSS":
                returnGoal = Goal.FAT_LOSS;
                break;
            case "MAINTAIN":
                returnGoal = Goal.MAINTAIN;
                break;
            case "GAIN_MASS":
                returnGoal = Goal.GAIN_MASS;
                break;
        }
        return returnGoal;
    }
    
    private ActivityLevel getActivityLevel(String levelString) {
        ActivityLevel al = null;
        switch(levelString.toUpperCase())
        {
            case "SEDENTARY":
                al = ActivityLevel.SEDENTARY;
                break;
            case "LIGHT":
                al = ActivityLevel.LIGHT;
                break;
            case "MODERATE":
                al = ActivityLevel.MODERATE;
                break;
            case "HEAVY":
                al = ActivityLevel.HEAVY;
                break;
        }
        return al;
    }
    
    private Gender getGender(String genderString) {
        Gender returnGender = null;
        switch(genderString.toUpperCase())
        {
            case "MALE":
                returnGender = Gender.MALE;
                break;
            case "FEMALE":
                returnGender = Gender.FEMALE;
                break;
        }
        return returnGender;
    }
    
    public User getUser() {
        return user;
    }
    
}
