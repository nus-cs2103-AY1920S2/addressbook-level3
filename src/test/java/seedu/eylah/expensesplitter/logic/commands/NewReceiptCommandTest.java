package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.testutil.TypicalEntries.getTypicalReceiptBook;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;

import org.junit.jupiter.api.BeforeEach;

import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;

public class NewReceiptCommandTest {
    private SplitterModel splitterModel;
    private SplitterModel expectedSplitterModel;

    @BeforeEach
    public void setUp() {
        splitterModel = new SplitterModelManager(getTypicalReceiptBook(), getTypicalPersonAmountBook(),
                new UserPrefs());
        expectedSplitterModel = new SplitterModelManager(splitterModel.getReceiptBook(),
                splitterModel.getPersonAmountBook(), new UserPrefs());
    }




}
