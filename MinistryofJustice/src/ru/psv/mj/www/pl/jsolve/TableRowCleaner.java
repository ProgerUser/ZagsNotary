package ru.psv.mj.www.pl.jsolve;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Container class which contains information about rows intended to remove.
 * @author Lukasz Stypka
 */
public class TableRowCleaner {

    private List<XWPFTableRow> rows;

    public TableRowCleaner() {
        this.rows = new ArrayList<XWPFTableRow>();
    }

    /**
     * Add table row which should be removed from the table
     * @param row XWPFTableRow
     */
    public void add(XWPFTableRow row) {
        this.rows.add(row);
    }

    /**
     * Return list of rows which should be removed
     * @return list of rows
     */
    public List<XWPFTableRow> getRows() {
        return rows;
    }

}
