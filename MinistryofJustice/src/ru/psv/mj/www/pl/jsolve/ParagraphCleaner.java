package ru.psv.mj.www.pl.jsolve;

import java.util.ArrayList;
import java.util.List;

/**
 * Container class which contains information about paragraphs intended to remove.
 * @author Lukasz Stypka
 */
public class ParagraphCleaner {

    private List<ParagraphInsert> paragraphs;

    public ParagraphCleaner() {
        this.paragraphs = new ArrayList<ParagraphInsert>();
    }

    /**
     * Add new paragraph which should be removed from document
     * @param paragraph ParagraphInsert
     */
    public void add(ParagraphInsert paragraph) {
        this.paragraphs.add(paragraph);
    }

    /**
     * Get list of paragraphs which should be removed from document
     * @return List of paragraphs
     */
    public List<ParagraphInsert> getParagraphs() {
        return paragraphs;
    }

}
