package ru.psv.mj.www.pl.jsolve;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Insert for table row variable. This insert contains list of table cell inserts
 * @author Lukasz Stypka
 */
public class TableRowInsert extends Insert {

    private List<TableCellInsert> cellInserts;
    /**
     * XWPFTableRow which contains found cell variables
     */
    private XWPFTableRow row;

    public TableRowInsert() {
        super(new Key("", VariableType.TABLE));
        this.cellInserts = new ArrayList<TableCellInsert>();
    }

    public void add(TableCellInsert cellInsert) {
        this.cellInserts.add(cellInsert);
        this.key.addSubKey(cellInsert.getKey());
        if (row == null) {
            row = cellInsert.getCell().getTableRow();
        }
    }

    public List<TableCellInsert> getCellInserts() {
        return cellInserts;
    }

    public XWPFTableRow getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "TableRowInsert [cellInserts=" + cellInserts + ", row=" + row + "]";
    }

}