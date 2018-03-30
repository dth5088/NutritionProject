/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author dth5088
 */
public class AdvancedSearchPanel extends JPanel{
    HashMap<String, DefaultComboBoxModel<String>> mappedComboBoxModels;
    JComboBox foodGroupComboBox, dataSourceComboBox, sortOrderComboBox, maxReturnComboBox;
    JLabel foodGroupLabel, dataSourceLabel, sortOrderLabel,maxReturnLabel;
    
    public AdvancedSearchPanel() {
        
        
        mappedComboBoxModels = new HashMap<>();
        mappedComboBoxModels.putIfAbsent("fg", getFoodGroups());
        mappedComboBoxModels.putIfAbsent("ds", getDataSource());
        mappedComboBoxModels.putIfAbsent("sort",getSortOrder());
        mappedComboBoxModels.putIfAbsent("max",getMaxReturn());
        setupComboBoxes();
        setupPanelLayoutGBC();
    }
    
    private void setupPanelLayoutGBC() {
        foodGroupLabel = new JLabel("Select Food Group");
        dataSourceLabel = new JLabel("Select Source");
        sortOrderLabel = new JLabel("Select Sort Order");
        maxReturnLabel = new JLabel("Select Max #");
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,10,5,10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(foodGroupLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(foodGroupComboBox, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(dataSourceLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dataSourceComboBox, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(sortOrderLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(sortOrderComboBox, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(maxReturnLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(maxReturnComboBox, gbc); 
    }
    private void setupPanelLayout() {
        foodGroupLabel = new JLabel("Select Food Group");
        dataSourceLabel = new JLabel("Select Source");
        sortOrderLabel = new JLabel("Select Sort Order");
        maxReturnLabel = new JLabel("Select # Results to Return");
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodGroupLabel)
                                .addComponent(dataSourceLabel)
                                .addComponent(sortOrderLabel)
                                .addComponent(maxReturnLabel)
                            )
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodGroupComboBox)
                                .addComponent(dataSourceComboBox)
                                .addComponent(sortOrderComboBox)
                                .addComponent(maxReturnComboBox)   
                            )
                    )
        );
        
         layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(foodGroupLabel)
                        .addComponent(dataSourceLabel)
                        .addComponent(sortOrderLabel)
                        .addComponent(maxReturnLabel))
                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(foodGroupComboBox)
                        .addComponent(dataSourceComboBox)
                        .addComponent(sortOrderComboBox)
                        .addComponent(maxReturnComboBox)   
                 )
         );
         
         layout.linkSize(SwingConstants.HORIZONTAL, foodGroupLabel, dataSourceLabel, sortOrderLabel, maxReturnLabel, foodGroupComboBox, dataSourceComboBox, sortOrderComboBox, maxReturnComboBox);
         layout.linkSize(SwingConstants.VERTICAL, foodGroupLabel, dataSourceLabel, sortOrderLabel, maxReturnLabel, foodGroupComboBox, dataSourceComboBox, sortOrderComboBox, maxReturnComboBox);
    }
    private void setupComboBoxes() {
        for(HashMap.Entry<String,DefaultComboBoxModel<String>> entry : mappedComboBoxModels.entrySet())
        {
            switch(entry.getKey())
            {
                case "fg":
                    foodGroupComboBox = new JComboBox(entry.getValue());
                    foodGroupComboBox.setPrototypeDisplayValue("XXXXXXXXXXX");
                    break;
                case "ds":
                    dataSourceComboBox = new JComboBox(entry.getValue());
                    dataSourceComboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXX");
                    break;  
                case "sort":
                    sortOrderComboBox = new JComboBox(entry.getValue());
                    sortOrderComboBox.setPrototypeDisplayValue("XXXXXXXXXX");
                    break;
                case "max":
                    maxReturnComboBox = new JComboBox(entry.getValue());
                    maxReturnComboBox.setPrototypeDisplayValue("XXXXX");
                    break;
                
            }
        }

    }
    
    private DefaultComboBoxModel<String> getFoodGroups() {
        DefaultComboBoxModel<String> temp = new DefaultComboBoxModel<>(new String[] {
            "Baby Foods","American Indian/Alaskan Native Foods","Baked Products","Beef Products","Beverages"
            , "Breakfast Cereals","Cereal Grains and Pasta","Dairy and Egg Products"
            , "Fast Foods","Fats and Oils","Finfish and Shellfish Products","Fruits and Fruit Juices"
            , "Lamb, Veal, and Game Products","Legumes and Legume Products","Meals Entrees and Side Dishes"
            , "Nut and Seed Products","Pork Products","Poultry Products","Restaurant Foods","Sausages and Luncheon Meats"
            , "Snacks","Soups, Sauces, and Gravies","Spices and Herbs","Sweets","Vegetables and Vegetable Products"
        });
        return temp;
    }
    
    private DefaultComboBoxModel<String> getDataSource() {
        DefaultComboBoxModel<String> temp = new DefaultComboBoxModel<>(new String[] {
            "Branded Food Products","Standard Reference"
        });
        return temp;
    }
    
    private DefaultComboBoxModel<String> getSortOrder() {
         DefaultComboBoxModel<String> temp = new DefaultComboBoxModel<>(new String[] {
             "Food name","Relevance"
         });
         return temp;
     }
     
    private DefaultComboBoxModel<String> getMaxReturn() {
        String[] nums = new String[20];
        for(int i = 1; i < 20; i++) {
            nums[i] = (i*5) + "";
        }
        DefaultComboBoxModel<String> temp = new DefaultComboBoxModel<>(nums);
        return temp;
    }

    public String getFoodGroupSelection() {
        return foodGroupComboBox.getSelectedItem().toString();
    }
    
    public String getDataSourceSelection() {
        return dataSourceComboBox.getSelectedItem().toString();
    }
    
    public String getSortOrderSelection() {
        return sortOrderComboBox.getSelectedItem().toString();
    }
    
    public String getMaxReturnSelection() {
        return maxReturnComboBox.getSelectedItem().toString();
    }
    
    
    
}
