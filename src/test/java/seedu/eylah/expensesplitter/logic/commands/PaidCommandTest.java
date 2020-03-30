package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;

import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.logic.parser.ParserUtil;
import seedu.eylah.addressbook.logic.parser.exceptions.ParseException;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.ModelManager;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReceiptBookStub;
import seedu.eylah.expensesplitter.model.UserPrefs;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class PaidCommandTest {

    private Model model = new ModelManager(new ReceiptBookStub(), getTypicalPersonAmountBook(), new UserPrefs());

    @Test
    public void execute_reducePersonAmount_success() throws ParseException {

        String amountPaid = "1";
        Index index = ParserUtil.parseIndex("1");
        model.getReceipt().makeDone();
        PaidCommand paidCommand = new PaidCommand(index, amountPaid);

        Model expectedModel = new ModelManager(new ReceiptBookStub(), new PersonAmountBook(model.getPersonAmountBook()),
            new UserPrefs());
        expectedModel.getReceipt().makeDone();
        expectedModel.paidPerson(TypicalPersons.ALICE, "1");

        String messageSuccess = PaidCommand.MESSAGE_SUCCESS + TypicalPersons.ALICE.getName()
            + ". Amount decreased from " + "2.50 to 1.50.";
        assertCommandSuccess(paidCommand, model, messageSuccess, expectedModel);

    }
}
