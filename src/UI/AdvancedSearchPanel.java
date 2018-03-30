/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author dth5088
 */
public class AdvancedSearchPanel extends JPanel{
    HashMap<String, DefaultComboBoxModel<String>> mappedComboBoxModels;
    JComboBox foodGroupComboBox, dataSourceComboBox, sortOrderComboBox, maxReturnComboBox;
    JLabel foodGroupLabel, dataSourceLabel, sortOrderLabel,maxReturnLabel;
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border compound = BorderFactory.createCompoundBorder(raisedbevel,loweredbevel);
    int style = Font.BOLD | Font.ITALIC;
    Font font = new Font ("Arial", style , 14);
    
    public AdvancedSearchPanel() {
        
        
        mappedComboBoxModels = new HashMap<>();
        mappedComboBoxModels.putIfAbsent("fg", getFoodGroups());
        mappedComboBoxModels.putIfAbsent("ds", getDataSource());
        mappedComboBoxModels.putIfAbsent("sort",getSortOrder());
        mappedComboBoxModels.putIfAbsent("max",getMaxReturn());
        setupComboBoxes();
        setupPanelLayoutGBC();
        setFont(font);
    }
    
    private void setupPanelLayoutGBC() {
        foodGroupLabel = new JLabel("Food Group");
        dataSourceLabel = new JLabel("Source");
        sortOrderLabel = new JLabel("Sort Order");
        maxReturnLabel = new JLabel("# Results");
        
        foodGroupLabel.setFont(font);
        dataSourceLabel.setFont(font);
        sortOrderLabel.setFont(font);
        maxReturnLabel.setFont(font);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,1,5,1);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createBorderedPanel(dataSourceLabel, dataSourceComboBox), gbc);
        
        gbc.gridx = 1;
        add(createBorderedPanel(foodGroupLabel,foodGroupComboBox), gbc);
        
        gbc.gridx = 2;
        add(createBorderedPanel(sortOrderLabel,sortOrderComboBox), gbc);
        
        gbc.gridx = 3;
        add(createBorderedPanel(maxReturnLabel, maxReturnComboBox), gbc);
        setBorder(BorderFactory.createTitledBorder(loweredbevel, "Search Options"));
    }
    private void setupComboBoxes() {
        for(HashMap.Entry<String,DefaultComboBoxModel<String>> entry : mappedComboBoxModels.entrySet())
        {
            switch(entry.getKey())
            {
                case "fg":
                    foodGroupComboBox = new JComboBox(entry.getValue());
                    foodGroupComboBox.setPrototypeDisplayValue("XXXXXXXXXXX");
                    foodGroupComboBox.setEnabled(false);
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
        
        setActionListener();

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
        for(int i = 1; i < 21; i++) {
            nums[i-1] = (i*5) + "";
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

    private void setActionListener() {
       dataSourceComboBox.addActionListener(e -> {
           foodGroupComboBox.setEnabled(dataSourceComboBox.getSelectedIndex() == 1);
       });
    }
    
    private JPanel createBorderedPanel(JLabel label, JComboBox combo) {
        label.setHorizontalAlignment((JLabel.CENTER));
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(label);
        panel.add(combo);
        panel.setBorder(raisedbevel);
        return panel;
        
    }
    
    
    
}
