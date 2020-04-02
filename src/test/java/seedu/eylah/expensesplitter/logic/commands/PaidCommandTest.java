package seedu.eylah.expensesplitter.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.parser.ParserUtil;
import seedu.eylah.expensesplitter.model.ModelStub;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReceiptStub;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class PaidCommandTest {


    @Test
    public void constructor_nullPaidCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidCommand(null, null));
    }


    /**
     * For this test case I am testing if the PaidCommand is working as expected with a
     * pre creatied personamount book, with TYPICALPERSON.ALICE as index 1 and deducting $1 from ALICE.
     * The expected output should be ALICE's Amount dropping from $3.50
     * to $2.50.
     * @throws Exception
     */
    @Test
    public void execute_paidCommandAcceptedByModel_paidSuccessful() throws Exception {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();
        // Ensuring that the receipt is done before I can use PaidCommand.
        modelStub.receipt.makeDone();
        CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "1")
            .execute(modelStub);


        assertEquals(String.format(PaidCommand.MESSAGE_SUCCESS + TypicalPersons.ALICE.getName()
            + ". Amount decreased from " + "$3.50 to $2.50."), commandResult.getFeedbackToUser());

    }


    /**
     * For this test case I am testing if the PaidCommand is working as expected by throwing an exception when
     * the current receipt is still in process.
     * `donereceipt` command must be execute before PaidCommand can be executed
     * @throws Exception
     */
    @Test
    public void execute_paidCommandAcceptedByModel_paidUnsuccessful() throws ParseException, CommandException {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();
        // Ensuring that the receipt is NOT done so I cannot use PaidCommand.

        CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "1")
            .execute(modelStub);


        assertEquals(String.format(PaidCommand.MESSAGE_RECEIPT_UNDONE), commandResult.getFeedbackToUser());
    }

    /**
     * For this test case I am testing if the PaidCommand is working as expected by
     * inputting a wrong amount value. Such as -1.
     * @throws Exception
     */
    @Test
    public void execute_paidCommandFailureDueToIncorrectAmount() throws ParseException, CommandException {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();

        // Ensuring that the receipt is done so I can use PaidCommand.
        modelStub.receipt.makeDone();

        String test;

        try {
            CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "-1")
                .execute(modelStub);
            test = "pass";
        } catch (IllegalArgumentException ex) {
            // working as expected. Because amount paid cannot be negative
            test = "fail";

        }

        assertTrue(test.equals("fail"));

    }



    /**
     * For this test case I am testing if the PaidCommand is working as expected by
     * inputting a wrong amount value. Such as passing in an amount that
     * is more than what the ALICE owes.
     * @throws Exception
     */
    @Test
    public void execute_paidCommandFailureDueToTooMuchAmount() throws ParseException, CommandException {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();

        // Ensuring that the receipt is done so I can use PaidCommand.
        modelStub.receipt.makeDone();

        String test;

        try {
            CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "5.00")
                .execute(modelStub);
            test = "pass";
        } catch (CommandException ex) {
            // working as expected. Because amount paid cannot be more than what the person owes.
            test = "fail";

        }

        assertTrue(test.equals("fail"));

    }










    private class ModelStubPaidCommand extends ModelStub {

        private final PersonAmountBook personAmountBook;
        private final Receipt receipt;

        ModelStubPaidCommand() {
            personAmountBook = getTypicalPersonAmountBook();
            receipt = new ReceiptStub();
        }


        /**
         * In order to use PaidCommand, I need to ensure the receipt is done.
         * Hence for this simple stub, I checking if receipt is done.
         * @return
         */
        @Override
        public boolean isReceiptDone() {
            return receipt.isDone();
        }

        @Override
        public ReadOnlyPersonAmountBook getPersonAmountBook() {
            return this.personAmountBook;
        }

        /**
         * For this particular test case I am deducting ALICE's Amount by $1
         * @param person TypicalPerson.ALice
         * @param amountPaid
         */
        @Override
        public void paidPerson(Person person, String amountPaid) {
            Amount amount = new Amount(new BigDecimal(amountPaid));
            personAmountBook.removeAmount(person, amount);
        }

    }

}
