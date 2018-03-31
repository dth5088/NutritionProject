/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import nutrition.app.Parsers.Measure;
import nutrition.app.Parsers.USDANutrient;

/**
 *
 * @author dth5088
 */
public class NutrientResultTableModel extends AbstractTableModel{
    private static final long serialVersionUID = 1L;
    private List<NutrientRow> rows;
    private HashMap<USDANutrient, ArrayList<NutrientRow>> mapLookup;
    String[] columnNames = {"Nutrient","QTY","Label","Amount"};
    
    public NutrientResultTableModel() {
        rows = new ArrayList<>();
        mapLookup = new HashMap<>();
    }
    
    public void addNutrient(USDANutrient nutrient) {
        if(!mapLookup.containsKey(nutrient))
            mapLookup.put(nutrient,new ArrayList<>());
        for(Measure measure : nutrient.getMeasures())
        {
            NutrientRow row = new NutrientRow(nutrient, measure);
            mapLookup.get(nutrient).add(row);
            rows.add(row);
            fireTableRowsInserted(rows.size()-1, rows.size()-1);
        }
        
    }
    public void clearRows() {
        rows.clear();
        mapLookup.clear();
        fireTableRowsDeleted(0,rows.size());
    }
    public USDANutrient getRow(int r) {
        NutrientRow row = rows.get(r);
        return row.getNutrient();
    }

    @Override
    public int getRowCount() {
       return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NutrientRow nutrientRow = rows.get(rowIndex);
        Object value = null;
        switch(columnIndex) {
            case 0:
                value = nutrientRow.getNutrientName();
                break;
            case 1:
                value = nutrientRow.getQuantity();
                break;
            case 2:
                value = nutrientRow.getLabel();
                break;
            case 3:
                value = nutrientRow.getMeasure();
                break;
            
        }
        return value;
    }
    
    
}
