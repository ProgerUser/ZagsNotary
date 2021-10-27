package ru.psv.mj.www.pl.jsolve;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

/**
 * Insert for bullet list. Numbered list is presented as BulletListInsert as well
 * @author Lukasz Stypka
 */
public class BulletListInsert extends ParagraphInsert {

    public BulletListInsert(Key key, XWPFParagraph paragraph, XWPFTableCell cellParent, XWPFDocument documentParent) {
        super(key, paragraph, cellParent, documentParent);
    }

}