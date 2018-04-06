package nutrition.app;

import UI.FoodSearchResultTableModel;
import UI.AdvancedSearchPanel;
import UI.FoodPreferencePanel;
import UI.NutrientResultPanel;
import UI.SearchResultPanel;
import UI.UserDetailPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import nutrition.app.Parsers.FoodList;
import nutrition.app.Parsers.FoodParser;
import nutrition.app.Parsers.ReportParser;
import nutrition.app.Parsers.USDAFood;
import nutrition.app.Parsers.USDANutrient;
import nutrition.app.Parsers.UserParser;
import org.json.JSONException;


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
    
    
    private static class MainFrame extends JFrame {
        JTextArea textArea = new JTextArea(), foodResponseTextArea = new JTextArea();
        JTextField textField = new JTextField();
        JButton executeButton = new JButton("Lookup Food");
        Dimension panelSize = new Dimension(500,450);
        Dimension userPanelSize = new Dimension(950, 450);
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
        String[] columnNames = {"Food Name", "NDB Number"};
        JScrollPane tablePane;
        FoodSearchResultTableModel model = new FoodSearchResultTableModel();
        JTable table;
        HashMap<String,String> options;
        JTabbedPane mainTabbedPane = new JTabbedPane();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel,loweredbevel);
        AdvancedSearchPanel searchPanel = new AdvancedSearchPanel();
        NutrientResultPanel nutrientResults;
        UserParser userParser = new UserParser();
        SearchResultPanel searchResultPanel;
        
        public MainFrame() {
            options = new HashMap<>();
            if(userParser.getUser() != null)
                user = userParser.getUser();
            createAndShowGUI();
            
            
        }

        private void createAndShowGUI() {
            JFrame frame = new JFrame("Nutrition Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            init(frame.getContentPane());
            frame.setPreferredSize(new Dimension(1000, 600));
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
                double weightLBS = Double.parseDouble(weightString);
                double weight = weightLBS * lbs_to_kg;
                String ht_ft = heightFeetComboBox.getSelectedItem().toString().replace("'", "");
                String ht_in = heightInchesComboBox.getSelectedItem().toString().replace('"', ' ').trim();
                
                double heightInches = (Double.parseDouble(ht_ft) * 12.0) + (Double.parseDouble(ht_in));
                double heightCm = heightInches * inch_to_cm;
                LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
                String genderString = genderComboBox.getSelectedItem().toString();
                String goalString = goalComboBox.getSelectedItem().toString();
                String activityLevelString = activityLevelComboBox.getSelectedItem().toString();
                Gender gender = getGender(genderString);
                Goal goal = getGoal(goalString);
                ActivityLevel activityLevel = getActivityLevel(activityLevelString);
                
                
                if(goal != null && activityLevel != null && gender != null)
                {
                    user = new User(firstName,lastName,heightCm,weight,dateOfBirth,gender,activityLevel,goal);
                    searchResultPanel.setUser(user);
                    if(!userParser.matchesUser(user))
                    {
                        System.out.println("Writing User details to text file");
                        userParser = new UserParser(user);
                    }
                        
                    textArea.setText("");
                    textArea.append(user.toString());
                    System.out.println(user.toString());
                }
                
            });
            
            executeButton.addActionListener(e -> {
                nutrientResults.clearNutrients();
                String foodToSearch = textField.getText();
                String jsonString = "";
                FoodList searchResults = new FoodList(foodToSearch);
                try {
                    setOptions(foodToSearch);
                    String returnString = FoodService.fetchUSDA_FoodList(options);
                    FoodParser parser = new FoodParser(foodToSearch, returnString);
                    searchResults = new FoodList(parser.getSearchResults());
                    jsonString = returnString;
                    searchResultPanel.addFoodListToTable(searchResults);
                    
                } catch (IOException | JSONException ex) {
                    jsonString = ex.getMessage();
                }
            }); 
        }
        
        public void setNutrientsForSelectedFood(USDAFood food) {
            setOptionsForReport(food.getNDBNO(), "b");
            try {
                String reportString = FoodService.getNutrientsFromNDBno(options);
                ReportParser foodParser = new ReportParser(reportString);
                for(USDANutrient nutrient : foodParser.getNutrients())
                    nutrientResults.addNutrientToDisplay(nutrient);
            } catch(IOException | JSONException ignore) {
                
            }
        }
        private Gender getGender(String genderString) {
            switch(genderString) {
                case "Male":
                    return Gender.MALE;
                case "Female":
                    return Gender.FEMALE;
                default:
                    return Gender.MALE;
            }
        }
        
        private Goal getGoal(String goalString) {
            switch(goalString) {
                 case "Fat Loss":
                        return Goal.FAT_LOSS;
                    case "Maintain":
                        return Goal.MAINTAIN;
                    case "Gain Mass":
                        return Goal.GAIN_MASS;
                    default:
                        return Goal.FAT_LOSS;
            }
        }
        
        private ActivityLevel getActivityLevel(String activityLevelString) {
            switch(activityLevelString)
            {
                 case "Sedentary":
                        return ActivityLevel.SEDENTARY;
                    case "Light":
                        return ActivityLevel.LIGHT;
                    case "Moderate":
                        return ActivityLevel.MODERATE;
                    case "Heavy":
                        return ActivityLevel.HEAVY;
                    default:
                        return ActivityLevel.SEDENTARY;
            }
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
            
            setupResultTable();
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
            nutrientResults = new NutrientResultPanel();
            searchResultPanel = new SearchResultPanel(nutrientResults);
            JPanel foodPanel = createFoodPanel();
            JPanel textAreaPanel = createTextAreaPanel();
            JPanel tPanel = new JPanel();
            //nutrientResults.setPreferredSize(new Dimension(400,450));
            //nutrientResults.setMinimumSize(new Dimension(400,450));
           
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            
            
            if(user != null)
            {
                UserDetailPanel uPanel = new UserDetailPanel(user);
                FoodPreferencePanel prefPanel = new FoodPreferencePanel(user);
                splitPane.setLeftComponent(uPanel);
                splitPane.setRightComponent(prefPanel);
            }
            else
            {
                splitPane.setLeftComponent(createUserPanel());
                splitPane.setRightComponent(tPanel);
            }
            JPanel tabPanel1 = new JPanel();
            GroupLayout tabPanel1Layout = new GroupLayout(tabPanel1);
            tabPanel1.setLayout(tabPanel1Layout);
            tabPanel1Layout.setAutoCreateGaps(true);
            tabPanel1Layout.setAutoCreateContainerGaps(true);
           
            tabPanel1Layout.setHorizontalGroup(
                    tabPanel1Layout.createSequentialGroup()
                        .addComponent(foodPanel)
                        .addComponent(nutrientResults)
            );
            tabPanel1Layout.setVerticalGroup(tabPanel1Layout.createSequentialGroup()
                    .addGroup(tabPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(foodPanel)
                        .addComponent(nutrientResults))
            );
            
            
            
            
            
            mainTabbedPane.addTab("Nutrition Tool", tabPanel1);
            
            mainTabbedPane.addTab("User Details", splitPane);
            
            
            
            contentPane.add(mainTabbedPane);
            
        }
        
        private void setupResultTable() {
            table = new JTable(model) {
                @Override
                public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    if(column < 4)
                    {
                        ((JLabel)c).setHorizontalAlignment(SwingConstants.CENTER);
                    }
                    if(!isRowSelected(row)) {
                        String type = (String) getModel().getValueAt(row, 0);
                        c.setBackground( row % 2 == 0 ? null : Color.LIGHT_GRAY);
                        ((JComponent)c).setBorder(new LineBorder(Color.LIGHT_GRAY));
                    }
                    setRowHeight(row, 30);
                    return c;
                }
            };
        }
        private JPanel createFoodPanel() {
            JPanel foodPanel = new JPanel(new GridBagLayout());
            //Dimension d = new Dimension(375,50);
            //searchPanel.setPreferredSize(d);
//            foodPanel.setPreferredSize(panelSize);
//            foodPanel.setMinimumSize(panelSize);
//            foodPanel.setMaximumSize(panelSize);
            textField.setToolTipText("Enter food to search!");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 0;
            gbc.insets = new Insets(5,5,5,5);
            gbc.weightx = 0.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            foodPanel.add(searchPanel, gbc);
            
            gbc.weightx = 1.0;
            gbc.gridwidth = 1;
            gbc.gridy = 1;
            gbc.gridx = 0;
            foodPanel.add(textField, gbc);
            gbc.gridx = 1;
            foodPanel.add(executeButton, gbc);
            
            gbc.gridy = 2;
            //gbc.weightx = 0.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weighty = 1;
            gbc.gridx = 0;
            foodPanel.add(searchResultPanel, gbc);
            //tablePane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
//            GroupLayout layout = new GroupLayout(foodPanel);
//            foodPanel.setLayout(layout);
//            layout.setAutoCreateGaps(true);
//            layout.setAutoCreateContainerGaps(true);
//            
//            layout.setHorizontalGroup(
//                layout.createSequentialGroup()
//                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//                            .addComponent(searchPanel)
//                            .addGroup(layout.createSequentialGroup()
//                                    .addComponent(textField)
//                                    .addComponent(executeButton)
//                            )
//                    //.addComponent(searchPanel)
//                    .addComponent(tablePane)));
//                    
//                    
//            
//            layout.setVerticalGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                    .addComponent(searchPanel))
//                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                    .addComponent(textField)
//                    .addComponent(executeButton))
//                .addComponent(tablePane));

            
            return foodPanel;
        }
        
        private JPanel createTextAreaPanel() {
            JPanel textAreaPanel = new JPanel(new GridBagLayout());
            textAreaPanel.setMinimumSize(new Dimension(400,450));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(30,5,-45,5);
            JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createTitledBorder(compound,"Display"));
            Dimension test = new Dimension(375,350);
            scrollPane.setMinimumSize(test);
            scrollPane.setMaximumSize(test);
            scrollPane.setPreferredSize(test);
            
            textAreaPanel.add(scrollPane, gbc);
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

        private void setOptions(String searchTerm) throws UnsupportedEncodingException {
            options = new HashMap<>();
            String sortString = searchPanel.getSortOrderSelection();
            String foodGroupString = searchPanel.getFoodGroupSelection();
            String dataSourceString = searchPanel.getDataSourceSelection();
            String maxReturnString = searchPanel.getMaxReturnSelection();
            switch(sortString.toUpperCase()) {
                case  "FOOD NAME":
                    options.put(Constants.USDA_SORT_OPTION_KEY, "n");
                    break;
                case "RELEVANCE":
                     options.put(Constants.USDA_SORT_OPTION_KEY,"r");
                     break;
            }
            
            if(dataSourceString.equalsIgnoreCase("Standard Reference"))
                options.put(Constants.USDA_FOOD_GROUP_KEY, foodGroupString);
            
            options.put(Constants.USDA_FORMAT_KEY, Constants.USDA_FORMAT_VALUE);
            options.put(Constants.USDA_SEARCH_TERM_KEY, URLEncoder.encode(searchTerm, "UTF-8"));
            options.put(Constants.USDA_DATA_SOURCE_KEY, dataSourceString);
            options.put(Constants.USDA_MAX_KEY, maxReturnString);
            options.put(Constants.USDA_OFFSET_KEY, "0");
            options.put(Constants.USDA_API_KEY, Constants.USDA_API_KEYVALUE);
        }
        
        private void setOptionsForReport(String dbnoKey, String reportType) {
            options = new HashMap<>();
            options.put(Constants.USDA_DBNO_KEY, dbnoKey);
            options.put(Constants.USDA_TYPE_KEY, reportType);
            options.put(Constants.USDA_FORMAT_KEY, Constants.USDA_FORMAT_VALUE);
            options.put(Constants.USDA_API_KEY, Constants.USDA_API_KEYVALUE);
        }
        

    }
}
