package com.hql.TableModel;

import com.hql.entity.ElementBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OneHandImportTableModel extends AbstractTableModel {
    private List<ElementBean> allData;

    public OneHandImportTableModel(List<ElementBean> elementBeanList) {
        allData = elementBeanList;
    }

    public void setData(List<ElementBean> elementBeanList) {
        allData = null;
        allData = elementBeanList;
        fireTableDataChanged();
    }

    private String[] columnNames = {"", "View", "id", "viewName"};

    @Override
    public int getRowCount() {
        return allData == null ? 0 : allData.size();
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
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 3/* || columnIndex == 3*/;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object bean;
        switch (columnIndex) {
            case 0:
                bean = allData.get(rowIndex).isSelect();
                break;
            case 1:
                bean = allData.get(rowIndex).getViewName();
                break;
            case 2:
                bean = allData.get(rowIndex).getId();
                break;
            case 3:
                bean = allData.get(rowIndex).getAttrsName();
                break;
            default:
                bean = "";
                break;
        }
        return bean;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                allData.get(rowIndex).setSelect((boolean) aValue);
                break;
            case 1:
                allData.get(rowIndex).setViewName((String) aValue);
                break;
            case 2:
                allData.get(rowIndex).setId((String) aValue);
                break;
            case 3:
                allData.get(rowIndex).setAttrsName((String) aValue);
                break;
            default:
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void selectAllOrNull(boolean value) {
        for (int index = 0; index < getRowCount(); index++) {
            this.setValueAt(value, index, 0);
        }
    }

}
