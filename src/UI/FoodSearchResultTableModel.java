/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;
import nutrition.app.Parsers.USDAFood;

/**
 *
 * @author dth5088
 */
public class FoodSearchResultTableModel extends AbstractTableModel{
    private static final long serialVersionUID = 1L;
    private List<USDAFood> rows;
    HashMap<String, USDAFood> mapLookup;
    String[] columnNames = {"Food Name", "UPC","NDB Number"};
    
    public FoodSearchResultTableModel() {
        rows = new ArrayList<>();
        mapLookup = new HashMap<>();
    }
    
    public void addRow(USDAFood food) {
        mapLookup.put(food.getFoodName(), food);
        rows.add(food);
        fireTableRowsInserted(rows.size()-1, rows.size()-1);
    }
    
    public USDAFood getRow(int row) {
        return rows.get(row);
    }
    /**
     * Overrides AbstractTableModel isCellEditable for
     * column 3 and for (buttonColumns).
     * 
     * This method is needed in order to allow the column
     * cells to be used as a button.
     * 
     * @param row
     * @param column
     * @return 
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    } // end method isCellEditable
    
     /**
     * Overrides AbstractTableModel getRowCount method
     * to return the size of the List<RowData> rows.
     * @return 
     */
    @Override
    public int getRowCount() {
        return rows.size();
    }// end method getRowCount
    
    /**
     * Overrides AbstractTableModel getColumnCount method
     * and returns the length of the local array columnNames
     * 
     * @return 
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    } // end method getColumnCount
    
    public void clearSearchResults() {
        rows.clear();
        mapLookup.clear();
        fireTableRowsDeleted(0, rows.size());
        
    }
    
    /**
     * Overrides AbstractTabelModel getColumnClass method.
     * 
     * Calls the getValueAt method to return the class of the column
     * on the table.
     * 
     * @param column
     * @return 
     */
    @Override
    public Class<?> getColumnClass(int column) {
        return String.class;
    } // end method getColumnClass
    
    /**
     * Overrides AbstractTableModel getColumnName method to return
     * the name of the column in the local array columnNames.
     * 
     * @param column
     * @return 
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    } // end method getColumnName
    
    /**
     * Overrides AbstractTableModel getValueAt method and returns 
     * a RowData object that is being stored in the table. 
     * 
     * @param rowIndex
     * @param columnIndex
     * @return 
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        USDAFood food = rows.get(rowIndex);
        Object value = null;
        switch(columnIndex) {
            case 0:
                value = food.getFoodName();
                break;
            case 1:
                value = food.getUPC();
                break;
            case 2:
                value = food.getNDBNO();
                break;
 
        }
        return value;
    } // end method getValueAt
    
}
