package ru.psv.mj.www.pl.jsolve;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * Insert for image variable.
 * @author Lukasz Stypka
 */
public class ImageInsert extends Insert {

    /**
     * Paragraph which contains image variable
     */
    private XWPFParagraph paragraph;

    public ImageInsert(Key key, XWPFParagraph paragraph) {
        super(key);
        this.paragraph = paragraph;
    }

    public XWPFParagraph getParagraph() {
        return paragraph;
    }

}
