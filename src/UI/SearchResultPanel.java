/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import nutrition.app.Constants;
import nutrition.app.Food;
import nutrition.app.FoodService;
import nutrition.app.Main;
import nutrition.app.Parsers.FoodList;
import nutrition.app.Parsers.ReportParser;
import nutrition.app.Parsers.USDAFood;
import nutrition.app.Parsers.USDANutrient;
import nutrition.app.User;
import org.json.JSONException;

/**
 *
 * @author dth5088
 */
public class SearchResultPanel extends JPanel{
    FoodSearchResultTableModel model;
    JTable table;
    JPopupMenu popup;
    USDAFood selectedFood = null;
    Food regFood = null;
    NutrientResultPanel nutrientResults;
    HashMap<String,String> options;
    User user = null;
    public SearchResultPanel(NutrientResultPanel nutrientResults) {
        this.nutrientResults = nutrientResults;
        this.nutrientResults.clearNutrients();
        model = new FoodSearchResultTableModel();
        popup = new JPopupMenu();
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
        setupLayout();
        setupListeners();
        
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public void addFoodListToTable(FoodList list) {
        for(USDAFood food : list.getFoodList())
        {
            model.addRow(food);
        }
    }
    private void setupListeners() {
        table.addMouseListener(new PopupListener());
        
        JMenuItem item = new JMenuItem("Add to preferences!");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(user != null && regFood != null)
                {
                    user.addFood(regFood);
                        
                }
            }
        });
        popup.add(item);
        
    }

    private void setupLayout() {
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
    }
    
    class PopupListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            togglePopup(e);
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            togglePopup(e);
        }
        
        private void togglePopup(MouseEvent e) {
            nutrientResults.clearNutrients();
            int row = table.getSelectedRow();
            if(model.getRow(row) == null)
                return;
            selectedFood = model.getRow(row);
            setOptionsForReport(selectedFood.getNDBNO(), "b");
            try {
                String reportString = FoodService.getNutrientsFromNDBno(options);
                ReportParser foodParser = new ReportParser(reportString);
                double carbs=0.2f, fat=0.2f, calories=0.2f, protein = 0.2f;
                for(USDANutrient nutrient : foodParser.getNutrients())
                {
                    switch(nutrient.getName().toLowerCase())
                    {
                        case "fat":
                            fat = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
                            break;
                        case "carb":
                            carbs = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
                            break;
                        case "protein":
                            protein = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
                            break;
                        case "calories":
                            calories = Double.parseDouble(nutrient.getDefaultMeasure().getValue());
                            break;
                    }
                    nutrientResults.addNutrientToDisplay(nutrient);
                }
                regFood = new Food(selectedFood.getFoodName(), 1, calories, carbs, protein, fat);
            } catch(IOException | JSONException ignore ) {
                
            }
            if(e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
            
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
              int row = table.getSelectedRow();
               if(model.getRow(row) == null)
                return;
              USDAFood food = model.getRow(row);
        }
    }
    
    private void setOptionsForReport(String dbnoKey, String reportType) {
            options = new HashMap<>();
            options.put(Constants.USDA_DBNO_KEY, dbnoKey);
            options.put(Constants.USDA_TYPE_KEY, reportType);
            options.put(Constants.USDA_FORMAT_KEY, Constants.USDA_FORMAT_VALUE);
            options.put(Constants.USDA_API_KEY, Constants.USDA_API_KEYVALUE);
        }
    
}
