package ru.psv.mj.www.pl.jsolve;

import java.util.List;

/**
 * This class uses variableFinder to generate list of Insert, and then begins the process of filling a template.
 * @author Lukasz Stypka
 */
public class DocumentExecutor {

    private VariableFinder variableFinder;
    private Variables variables;

    public DocumentExecutor(Variables variables) {
        this.variableFinder = new VariableFinder(variables);
        this.variables = variables;
    }

    /**
     * This method replaces variables in docx template. Note, that before executing this method you should invoke
     * DocumentCleaner.clean method
     * @param docx Docx object with opened .docx file
     */
    public void execute(Docx docx) {
        List<Insert> inserts = variableFinder.find(docx.getXWPFDocument(), variables);
        variableFinder.replace(inserts);
    }

}
