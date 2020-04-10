package client.components;

import javax.swing.table.AbstractTableModel;

public class SelectableTableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;

    public SelectableTableModel(Object[][] data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
