package mybeans;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DataSheetTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<DataSheetChangeListener> listenersList = new ArrayList<>();
    private DataSheetChangeEvent event = new DataSheetChangeEvent(this);

    private static final int columnCount = 3;
    private int rowCount = 1;
    private DataSheet dataSheet;
    String [] columnNames = {"Date", "X Value", "Y Value"};

    public DataSheetTableModel(DataSheet dataSheet){
        setDataSheet(dataSheet);
    }

    public DataSheet getDataSheet(){
        return dataSheet;
    }

    public void setDataSheet(DataSheet newDataSheet){
        dataSheet = newDataSheet;
        rowCount = dataSheet.dataCount();
        fireDataSheetChanges();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return columnIndex >= 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        try{
            double d;
            String stringValue = (String) value;
            if (dataSheet != null){
                if (columnIndex == 0)
                    dataSheet.getDataItem(rowIndex).setDate(stringValue);
                else if (columnIndex == 1){
                    double x = Double.parseDouble(stringValue);
                    dataSheet.getDataItem(rowIndex).setX(x);
                } else {
                    double y = Double.parseDouble(stringValue);
                    dataSheet.getDataItem(rowIndex).setY(y);
                }
            }
            fireDataSheetChanges();
        } catch (Exception ex){
            System.out.println("Не удалось измениь значение");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataSheet != null){
            Data dataItem = dataSheet.getDataItem(rowIndex);
            switch (columnIndex){
                case 0: return dataItem.getDate();
                case 1:
                    if (dataItem.getX() == 0 && dataSheet.dataCount() == 1)
                        return "";
                    else
                        return dataItem.getX();
                case 2:
                    if (dataItem.getY() == 0 && dataSheet.dataCount() == 1)
                        return "";
                    else
                        return dataItem.getY();
            }
        }

        return null;
    }

    public void setRowCount(int rowCount){
        if (rowCount > 0)
            this.rowCount = rowCount;
    }

    public void removeDataSheetChangeListener(DataSheetChangeListener listener){
        listenersList.remove(listener);
    }

    public void addDataSheetChangeListener(DataSheetChangeListener listener){
        listenersList.add(listener);
    }

    protected void fireDataSheetChanges(){
        for (DataSheetChangeListener aListenersList : listenersList)
            (aListenersList).dataChange(event);
    }

    public boolean isEmpty(){
        return dataSheet.dataCount() == 1 && dataSheet.getDataItem(0).getDate().equals("");
    }


}
