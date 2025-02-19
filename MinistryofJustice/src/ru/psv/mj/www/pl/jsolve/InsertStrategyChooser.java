package ru.psv.mj.www.pl.jsolve;

public class InsertStrategyChooser {

    private TextInsertStrategy textInsertStrategy;
    private ImageInsertStrategy imageInsertStrategy;
    private TableInsertStrategy tableInsertStrategy;
    private BulletListInsertStrategy bulletListInsertStrategy;
    private ObjectInsertStrategy objectInsertStrategy;
    private Variables variables;

    public InsertStrategyChooser(Variables variables, TableRowCleaner tableRowCleaner, ParagraphCleaner paragraphCleaner) {
        this.textInsertStrategy = new TextInsertStrategy();
        this.imageInsertStrategy = new ImageInsertStrategy();
        this.tableInsertStrategy = new TableInsertStrategy(variables, this, tableRowCleaner);
        this.bulletListInsertStrategy = new BulletListInsertStrategy(this, paragraphCleaner);
        this.objectInsertStrategy = new ObjectInsertStrategy();
        this.variables = variables;
    }

    public void replace(Insert insert, Variable variable) {
        switch (insert.getKey().getVariableType()) {
        case TEXT:
            textInsertStrategy.insert(insert, variable);
            break;
        case IMAGE:
            imageInsertStrategy.insert(insert, variable);
            break;
        case TABLE:
            tableInsertStrategy.insert(insert, variable);
            break;
        case BULLET_LIST:
            bulletListInsertStrategy.insert(insert, variable);
            break;
        case OBJECT:
            objectInsertStrategy.insert(insert, variable);
            break;
        }
    }

    public void replace(Insert insert) {
        replace(insert, variables.getVariable(insert.getKey()));
    }

    public void cleanUp() {
        tableInsertStrategy.cleanRows();
        bulletListInsertStrategy.cleanParagraphs();
    }
}
