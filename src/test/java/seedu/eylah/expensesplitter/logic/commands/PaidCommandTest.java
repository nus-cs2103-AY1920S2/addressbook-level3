package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.expensesplitter.logic.parser.ParserUtil;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReceiptBookStub;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class PaidCommandTest {

    private SplitterModel splitterModel = new SplitterModelManager(new ReceiptBookStub(), getTypicalPersonAmountBook(),
            new UserPrefs());

    @Test
    public void execute_reducePersonAmount_success() throws ParseException {

        String amountPaid = "1";
        Index index = ParserUtil.parseIndex("1");
        splitterModel.getReceipt().makeDone();
        PaidCommand paidCommand = new PaidCommand(index, amountPaid);

        SplitterModel expectedSplitterModel = new SplitterModelManager(new ReceiptBookStub(),
                new PersonAmountBook(splitterModel.getPersonAmountBook()), new UserPrefs());
        expectedSplitterModel.getReceipt().makeDone();
        expectedSplitterModel.paidPerson(TypicalPersons.ALICE, "1");

        String messageSuccess = PaidCommand.MESSAGE_SUCCESS + TypicalPersons.ALICE.getName()
            + ". Amount decreased from " + "$2.50 to $1.50.";
        assertCommandSuccess(paidCommand, splitterModel, messageSuccess, expectedSplitterModel);

    }
}
