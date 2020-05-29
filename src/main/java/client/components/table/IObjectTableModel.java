package client.components.table;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * This class makes the Java Swing table model for the client.
 *
 * @author Jamie Martin
 */
public abstract class IObjectTableModel<T> extends AbstractTableModel {
    // The list of objects
    private List<T> objectRows = new ArrayList<>();

    /**
     * Get the list
     * @return
     */
    public List<T> getObjectRows() {
        return objectRows;
    }

    /**
     * Set the list
     * @param objectRows
     */
    public void setObjectRows(List<T> objectRows) {
        this.objectRows = objectRows;
    }

    /**
     * Get row count
     * @return
     */
    @Override
    public int getRowCount() {
        return objectRows.size();
    }

    /**
     * Get the value of the given cell row, col integers
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            T t = objectRows.get(rowIndex);
            return getValueAt(t, columnIndex);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Determines if the cell is editable
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        if(t instanceof Editable){
            if(!((Editable) t).isEditable()){
                return false;
            }
        }
        return isColumnEditable(columnIndex);
    }

    /**
     * Sets the value using the given object at the cell row, col integers
     * @param value
     * @param row
     * @param column
     */
    public void setValueAt(Object value, int row, int column) {
        if(!isCellEditable(row, column)){
            return;
        }
        T t = objectRows.get(row);
        if(setObjectFieldValue(t, column, value)){
            fireTableCellUpdated(row, column);
        }
    }

    /**
     * Must implement if a cell is editable
     * @param columnIndex
     * @return
     */
    public abstract  boolean isColumnEditable(int columnIndex);

    /**
     * Must implement a getter for the list value
     * @param t
     * @param columnIndex
     * @return
     * @throws Exception
     */
    public abstract Object getValueAt(T t, int columnIndex) throws Exception;

    /**
     * Must implement a setter for a list value
     * @param t
     * @param column
     * @param value
     * @return
     */
    public abstract boolean setObjectFieldValue(T t, int column, Object value);

    @Override
    public abstract String getColumnName(int column);
    public abstract String getFieldName(int column);
}
