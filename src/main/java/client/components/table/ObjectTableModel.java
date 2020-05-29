package client.components.table;

import client.services.DataService;
import common.swing.Notification;

import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This class renders the Java Swing table model for the client.
 *
 * @author Jamie Martin
 */
public class ObjectTableModel<T> extends IObjectTableModel<T> {
    private Map<Integer, ColumnInfo> columnInfoMap;
    private DataService dataService;

    /**
     * Invokes a new Displayabl
     * @param tClass
     * @param dataService
     */
    public ObjectTableModel(Class<T> tClass, DataService dataService) {
        this.dataService = dataService;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
            this.columnInfoMap = new HashMap<>();

            // iterate through descriptors to build a information regarding table columns
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                // gets the getter method
                Method m = pd.getReadMethod();
                // only uses methods with DisplayAs
                // determines table variables for a column
                DisplayAs displayAs = m.getAnnotation(DisplayAs.class);

                if (displayAs == null) continue;

                // display values
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.displayName = displayAs.value();
                columnInfo.index = displayAs.index();
                columnInfo.editable = displayAs.editable();

                // get the setter method
                if (displayAs.editable()) columnInfo.setterMethod = pd.getWriteMethod();

                // get the getter method
                columnInfo.getterMethod = m;
                columnInfo.propertyName = pd.getName();

                // add to hashmap of column info
                columnInfoMap.put(columnInfo.index, columnInfo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes the getter method for that column
     * @param t
     * @param columnIndex
     * @return
     * @throws Exception
     */
    @Override
    public Object getValueAt(T t, int columnIndex) throws Exception {
        return columnInfoMap.get(columnIndex).getterMethod.invoke(t);
    }

    /**
     * Gets the column count
     * @return
     */
    @Override
    public int getColumnCount() {
        return columnInfoMap.size();
    }

    /**
     * Gets the name of a column using the display name as defined in the DisplayAs annotation
     * @param column
     * @return
     */
    @Override
    public String getColumnName(int column) {
        ColumnInfo columnInfo = columnInfoMap.get(column);

        if (columnInfo == null) throw new RuntimeException("No column found for index " + column);

        return columnInfo.displayName;
    }

    /**
     * Gets the class of a given column index
     * @param columnIndex
     * @return
     */
    public Class<?> getColumnClass(int columnIndex) {
        ColumnInfo columnInfo = columnInfoMap.get(columnIndex);
        return columnInfo.getterMethod.getReturnType();
    }

    /**
     * Gets the field name of a particular column
     * @param column
     * @return
     */
    @Override
    public String getFieldName(int column) {
        ColumnInfo columnInfo = columnInfoMap.get(column);
        return columnInfo.propertyName;
    }

    /**
     * Determines if the given columnIndex is editable or not
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isColumnEditable(int columnIndex) {
        ColumnInfo columnInfo = columnInfoMap.get(columnIndex);
        return columnInfo.editable;
    }

    /**
     * Attempts to update the value on the server and reflects the changes to the table
     * @param t
     * @param column
     * @param value
     * @return
     */
    @Override
    public boolean setObjectFieldValue(T t, int column, Object value) {
        ColumnInfo columnInfo = columnInfoMap.get(column);

        try {
            Object oldValue = columnInfo.getterMethod.invoke(t);

            if (columnInfo.setterMethod == null) return false;

            // invoke the setter method
            columnInfo.setterMethod.invoke(t, value);

            // attempt a Request to the server
            Boolean success = dataService.update(t);

            if (success) return true;
            else {
                // reinstate old value
                columnInfo.setterMethod.invoke(t, oldValue);
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper class to manage each column
     */
    private static class ColumnInfo {
        private Method setterMethod;
        private Method getterMethod;
        private int index;
        private String displayName;
        private String propertyName;
        private boolean editable;
    }
}
