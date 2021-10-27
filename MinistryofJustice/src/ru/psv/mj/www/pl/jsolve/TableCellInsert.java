package ru.psv.mj.www.pl.jsolve;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;

/**
 * Insert for table cell variable.
 * @author Lukasz Stypka
 */
public class TableCellInsert extends Insert {

    /**
     * Table cell which contains found variable
     */
    private XWPFTableCell cell;

    public TableCellInsert(Key key, XWPFTableCell cell) {
        super(key);
        this.cell = cell;
    }

    public void setCell(XWPFTableCell cell) {
        this.cell = cell;
    }

    public XWPFTableCell getCell() {
        return cell;
    }

}