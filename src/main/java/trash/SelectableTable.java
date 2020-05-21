package trash;

import trash.SelectableTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class SelectableTable extends JTable {
    public SelectableTable(SelectableTableModel model, ListSelectionListener e) {
        setModel(model);

        setAutoCreateRowSorter(true);
        setCellSelectionEnabled(true);

        ListSelectionModel cellSelectionModel = getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e);
    }
}
