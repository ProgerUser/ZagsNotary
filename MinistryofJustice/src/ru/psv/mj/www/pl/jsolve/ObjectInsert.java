package ru.psv.mj.www.pl.jsolve;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * Insert for object variable.
 *
 * @author indvd00m (gotoindvdum[at]gmail[dot]com)
 *
 */
public class ObjectInsert extends Insert {

    /**
     * Paragraph which contains text variable
     */
    private XWPFParagraph paragraph;

    public ObjectInsert(Key key, XWPFParagraph paragraph) {
        super(key);
        this.paragraph = paragraph;
    }

    public XWPFParagraph getParagraph() {
        return paragraph;
    }

}