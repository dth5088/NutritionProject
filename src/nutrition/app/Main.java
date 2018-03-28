/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author dth5088
 */
public class Main {
    static double lbs_to_kg = 0.453592;
    static double kg_to_lbs = 2.20462;
    static double inch_to_cm = 2.54;
    static double cm_to_inch = 0.393701;
    
    
    public static void main(String[] args) {
        
         try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
         MainFrame mainFrame = new MainFrame();
    }
    
    private static User makeDustin() {
        double myWeightInLbs = 180;
        double myHeightInInches = 72;
        double myWeightInKg = myWeightInLbs * lbs_to_kg;
        double myHeightInCm = myHeightInInches * inch_to_cm;
        LocalDate dob = LocalDate.of(1992, Month.JUNE, 20);
        Gender gender = Gender.MALE;
        ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        Goal fitnessGoal = Goal.FAT_LOSS;
        
        return new User("Dustin","Huntoon",myHeightInCm,myWeightInKg,dob,gender,activityLevel,fitnessGoal);
    }
    
    private static User makeStefanie() {
        double weight = 155 * lbs_to_kg;
        double height = 63 * inch_to_cm;
        LocalDate dob = LocalDate.of(1991,Month.SEPTEMBER, 9);
        Gender gender = Gender.FEMALE;
        ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        Goal fitnessGoal = Goal.FAT_LOSS;
        return new User("Stefanie","Huntoon",height,weight,dob,gender,activityLevel,fitnessGoal);
    }
    
    /**
     * Method to create Ryan.
     * 
     * @param firstName             -> YOUR FIRST NAME
     * @param lastName              -> YOUR LAST NAME
     * @param yourHeightInInches    -> YOUR HEIGHT IN INCHES
     * @param yourWeightInLbs       -> YOUR WEIGHT IN POUNDS
     * @param dateOfBirth           -> STRING REPRESENTATION OF YOUR BIRTHDAY (MONTH/DAY/YEAR) EX. "6/20/1992"
     * @param activityLevel         -> CAN BE activityLevel.SEDENTARY, activityLevel.LIGHT, activityLevel.MODERATE, OR activityLevel.HEAVY
     * @param fitnessGoal           -> CAN BE Goal.FAT_LOSS, Goal.MAINTAIN, OR Goal.GAIN_MASS
     * @return 
     */
    private static User makeRyan(String firstName, String lastName, double yourHeightInInches, double yourWeightInLbs, String dateOfBirth, ActivityLevel activityLevel, Goal fitnessGoal)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");    // Create format for String date(Month/Day/Year) -> LocalDate conversion   
        double heightInCm = yourHeightInInches * inch_to_cm;                        //convert your height in inches to centimeters
        double weightInKg = yourWeightInLbs * lbs_to_kg;                            // convert your weight in lbs to kg
        LocalDate dob = LocalDate.parse(dateOfBirth, formatter);                    // convert String date to LocalDate date using MM/dd/yyyy format
        Gender gender = Gender.MALE;
        
        return new User(firstName,lastName, heightInCm, weightInKg, dob, gender, activityLevel, fitnessGoal);
    }
    
    private static class MainFrame extends JFrame {
        JTextArea textArea = new JTextArea(), foodResponseTextArea = new JTextArea();
        JTextField textField;
        JButton executeButton;
        Dimension panelSize = new Dimension(300,700);
        Dimension userPanelSize = new Dimension(600, 700);
        final DefaultComboBoxModel<String> activityLevelComboBoxModel = new DefaultComboBoxModel<>(new String[] {"Sedentary","Light","Moderate","Heavy"});
        final DefaultComboBoxModel<String> genderComboBoxModel = new DefaultComboBoxModel<>(new String[] {"Male","Female"});
        final DefaultComboBoxModel<String> goalComboBoxModel = new DefaultComboBoxModel<>(new String[] {"Fat Loss","Maintain","Gain Mass"});
        JTextField firstNameField = new JTextField(10), lastNameField = new JTextField(10), weightTextField = new JTextField(5);
        ArrayList<String> years = new ArrayList<>();
        DefaultComboBoxModel<String> yearComboBoxModel, monthComboBoxModel, day29ComboBoxModel, day30ComboBoxModel, day31ComboBoxModel;
        JComboBox<String> activityLevelComboBox, genderComboBox, goalComboBox, yearComboBox, monthComboBox, heightFeetComboBox, heightInchesComboBox;
        JComboBox<String> dayComboBox = new JComboBox();
        User user;
        JButton submitUserButton = new JButton("Create User");
        
        
        
        
        public MainFrame() {
            createAndShowGUI();
            
            
        }

        private void createAndShowGUI() {
            JFrame frame = new JFrame("Nutrition Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            init(frame.getContentPane());
            frame.setPreferredSize(new Dimension(1200, 800));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        
        private void setActionListeners() {
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            monthComboBox.addActionListener(e -> {
                String selMonth = (String)((JComboBox<String>) e.getSource()).getSelectedItem();
                switch(selMonth) {
                    case "February":
                        dayComboBox.setModel(day29ComboBoxModel);
                        break;
                    case "April":
                    case "June":
                    case "September":
                    case "November":
                        dayComboBox.setModel(day30ComboBoxModel);
                        break;
                    case "January":
                    case "March":
                    case "May":
                    case "July":
                    case "August":
                    case "October":
                    case "December":
                        dayComboBox.setModel(day31ComboBoxModel);
                        break;
                    default:
                        dayComboBox.setModel(day31ComboBoxModel);
                        break;    
                }
            });
            
            submitUserButton.addActionListener(e -> {
                String dob = "";
                if(monthComboBox.getSelectedIndex() + 1 > 9)
                    dob = dayComboBox.getSelectedItem() + "/" + (monthComboBox.getSelectedIndex()+1) + "/" + yearComboBox.getSelectedItem();
                else 
                    dob = dayComboBox.getSelectedItem() + "/0" + (monthComboBox.getSelectedIndex()+1) + "/" + yearComboBox.getSelectedItem();
                
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String weightString = weightTextField.getText();
                double weight = Double.parseDouble(weightString);
                String ht_ft = heightFeetComboBox.getSelectedItem().toString().replace("'", "");
                String ht_in = heightInchesComboBox.getSelectedItem().toString().replace('"', ' ').trim();
                String heightString = ht_ft + "." + ht_in;
                double heightInches = Double.parseDouble(heightString);
                LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
                String genderString = genderComboBox.getSelectedItem().toString();
                String goalString = goalComboBox.getSelectedItem().toString();
                String activityLevelString = activityLevelComboBox.getSelectedItem().toString();
                Gender gender = null;
                Goal goal = null;
                ActivityLevel activityLevel = null;
                
                switch(genderString.toUpperCase()) {
                    case "MALE":
                        gender = Gender.MALE;
                        break;
                    case "FEMALE":
                        gender = Gender.FEMALE;
                        break;
                }
                
                switch(goalString.toUpperCase()) {
                    case "FAT LOSS":
                        goal = Goal.FAT_LOSS;
                        break;
                    case "MAINTAIN":
                        goal = Goal.MAINTAIN;
                        break;
                    case "GAIN MASS":
                        goal = Goal.GAIN_MASS;
                        break;
                }
                
                switch(activityLevelString.toUpperCase()) {
                    case "SEDENTARY":
                        activityLevel = ActivityLevel.SEDENTARY;
                        break;
                    
                    case "LIGHT":
                        activityLevel = ActivityLevel.LIGHT;
                        break;
                        
                    case "MODERATE":
                        activityLevel = ActivityLevel.MODERATE;
                        break;
                        
                    case "HEAVY":
                        activityLevel = ActivityLevel.HEAVY;
                        break;
                }
                
                if(goal != null && activityLevel != null && gender != null)
                {
                    user = new User(firstName,lastName,heightInches,weight,dateOfBirth,gender,activityLevel,goal);
                    textArea.setText("");
                    textArea.append(user.toString());
                    System.out.println(user.toString());
                }
                
            });
        }
        
        private void init(Container contentPane) {
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
            LocalDate date = LocalDate.now();
            String currentMonth = date.getMonth().toString();
            String currentYear = date.getYear() + "";
            String currentDay = date.getDayOfMonth() + "";
            String[] monthNames = dateFormatSymbols.getMonths();
            
            String[] days29 = getDays(29);
            day29ComboBoxModel = new DefaultComboBoxModel(days29);
            
            String[] days30 = getDays(30);
            day30ComboBoxModel = new DefaultComboBoxModel(days30);
            
            String[] days31 = getDays(31);
            day31ComboBoxModel = new DefaultComboBoxModel(days31);
            
            for(int y = 1970; y <=Calendar.getInstance().get(Calendar.YEAR);y++)
            {
                years.add(y+"");
            }
            String[] yearsArr = years.toArray(new String[years.size()]);
            yearComboBoxModel = new DefaultComboBoxModel<>(yearsArr);
            monthComboBoxModel = new DefaultComboBoxModel<>(monthNames);
            
            yearComboBoxModel.setSelectedItem(yearsArr[years.indexOf(currentYear)]);
            monthComboBoxModel.setSelectedItem(currentMonth);
            dayComboBox.setModel(day29ComboBoxModel);
            dayComboBox.setSelectedItem(currentDay);
            
            activityLevelComboBox = new JComboBox<>(activityLevelComboBoxModel);
            genderComboBox = new JComboBox<>(genderComboBoxModel);
            goalComboBox = new JComboBox<>(goalComboBoxModel);
            yearComboBox = new JComboBox<>(yearComboBoxModel);
            monthComboBox = new JComboBox<>(monthComboBoxModel);
            heightFeetComboBox = new JComboBox<>(getHeightFeetModel());
            heightInchesComboBox = new JComboBox<>(getHeightInchesModel());
            yearComboBox.setSize(new Dimension());
            
            
            setActionListeners();
            setLayout(contentPane);
        }
        private String[] getDays(int numDays) {
            String[] str = new String[numDays+1];
            for(int i = 0; i <= numDays - 1; i++) {
                str[i] = i+1+"";
            }
            return str;
        }
        

        
        private DefaultComboBoxModel<String> getHeightFeetModel() {
            String[] arr = new String[8];
            for(int i = 0; i < 8; i++) {
                arr[i] = i + "'";
            }
            
            return new DefaultComboBoxModel<>(arr);
        }
        
        private DefaultComboBoxModel<String> getHeightInchesModel() {
            String[] arr = new String[13];
            for(int i = 0; i < 13; i++)
            {
                arr[i] = i +""+'"';
            }
            return new DefaultComboBoxModel<>(arr);
        }
        
        private void setLayout(Container contentPane) {
            GroupLayout layout = new GroupLayout(contentPane);
            contentPane.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            JPanel foodPanel = createFoodPanel();
            JPanel textAreaPanel = createTextAreaPanel();
            JPanel userPanel = createUserPanel();
            
            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                        .addComponent(foodPanel)
                        .addComponent(textAreaPanel)
                        .addComponent(userPanel)
            );
            
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(foodPanel)
                        .addComponent(textAreaPanel)
                        .addComponent(userPanel))
            );
            
        }
        
        private JPanel createFoodPanel() {
            JPanel foodPanel = new JPanel();
            foodPanel.setPreferredSize(panelSize);
            foodPanel.setMinimumSize(panelSize);
            textField = new JTextField();
            textField.setToolTipText("Example: one large apple");
            textField.setText("textField");
            executeButton = new JButton("Lookup Food");
            JScrollPane scrollPane = new JScrollPane(foodResponseTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            GroupLayout layout = new GroupLayout(foodPanel);
            foodPanel.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(textField)
                                    .addComponent(executeButton)
                            )
                            .addComponent(scrollPane)));
                    
                    
            
            layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(textField)
                    .addComponent(executeButton))
                .addComponent(scrollPane));
            return foodPanel;
        }
        
        private JPanel createTextAreaPanel() {
            JPanel textAreaPanel = new JPanel();
            textAreaPanel.setPreferredSize(panelSize);
            textAreaPanel.setMaximumSize(panelSize);
            textAreaPanel.setLayout(new BorderLayout());
            textArea.setSize(foodResponseTextArea.getSize());
            JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            textArea.setText("Text Area Panel");
            textAreaPanel.add(scrollPane);
            return textAreaPanel;
        }
        
        private JPanel createUserPanel() {
            JPanel userPanel = new JPanel();
            userPanel.setPreferredSize(userPanelSize);
            
            JLabel firstNameLabel = new JLabel("First Name:"), lastNameLabel = new JLabel("Last Name:")
                    ,birthdayLabel = new JLabel("Birthday"), yearLabel = new JLabel("Year:"), heightLabel = new JLabel("Height (ft):")
                    , monthLabel = new JLabel("Month:"), inchesLabel = new JLabel("In:"), dayLabel = new JLabel("Day:"), weightLabel = new JLabel("Weight (lbs):")
                    , genderLabel = new JLabel("Gender:"), activityLabel = new JLabel("Activity Level:"), goalLabel = new JLabel("Fitness Goal:");
            
            GroupLayout layout = new GroupLayout(userPanel);
            userPanel.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(firstNameLabel)
                                .addComponent(firstNameField))
                            .addGroup(layout.createSequentialGroup()
                                    //.addComponent(birthdayLabel)
                                    .addComponent(monthLabel)
                                    .addComponent(monthComboBox))
                            .addGroup(layout.createSequentialGroup()
                                //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(heightLabel)
                                .addComponent(heightFeetComboBox)))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(lastNameLabel)
                                    .addComponent(lastNameField)
                                    .addComponent(genderLabel)
                                    .addComponent(genderComboBox))
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(dayLabel)
                                            .addComponent(inchesLabel)
                                            .addComponent(activityLabel)
                                    )
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(dayComboBox)
                                            .addComponent(heightInchesComboBox)
                                            .addComponent(activityLevelComboBox)
                                            
                                    )
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(yearLabel)
                                            .addComponent(weightLabel)
                                            .addComponent(goalLabel)
                                            
                                    )
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(yearComboBox)
                                            .addComponent(weightTextField)
                                            .addComponent(goalComboBox)
                                            .addComponent(submitUserButton)
                                    ))
                            
                    ));
            
            layout.linkSize(SwingConstants.HORIZONTAL, firstNameField, lastNameField);
            layout.linkSize(SwingConstants.HORIZONTAL, yearComboBox,monthComboBox,dayComboBox, heightFeetComboBox, heightInchesComboBox, weightTextField, genderComboBox, goalComboBox, activityLevelComboBox, submitUserButton);
            
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(firstNameLabel)
                            .addComponent(firstNameField)
                            .addComponent(lastNameLabel)
                            .addComponent(lastNameField)
                            .addComponent(genderLabel)
                            .addComponent(genderComboBox))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            //.addComponent(birthdayLabel)
                            .addComponent(yearLabel)
                            .addComponent(yearComboBox)
                            .addComponent(monthLabel)
                            .addComponent(monthComboBox)
                            .addComponent(dayLabel)
                            .addComponent(dayComboBox))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(heightLabel)
                            .addComponent(heightFeetComboBox)
                            .addComponent(inchesLabel)
                            .addComponent(heightInchesComboBox)
                            .addComponent(weightLabel)
                            .addComponent(weightTextField))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(activityLabel)
                            .addComponent(activityLevelComboBox)
                            .addComponent(goalLabel)
                            .addComponent(goalComboBox)
                    )
                    .addComponent(submitUserButton)
            );
            return userPanel;
        }

    }
}
