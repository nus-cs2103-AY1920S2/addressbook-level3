package seedu.eylah.expensesplitter.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.parser.ParserUtil;
import seedu.eylah.expensesplitter.model.ModelStub;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReceiptStub;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
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
            + ". Amount owed decreased from " + "$3.50 to $2.50."), commandResult.getFeedbackToUser());

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
     * For this test case I am testing if the PaidCommand is working as expected with a
     * pre creatied personamount book, with TYPICALPERSON.ALICE as index 1 and deducting fully from ALICE.
     * The expected output should be ALICE's Amount dropping from $3.50
     * to $0.00.
     * @throws Exception
     */
    @Test
    public void execute_paidCommandAcceptedByModel_paidFullySuccessful() throws Exception {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();
        // Ensuring that the receipt is done before I can use PaidCommand.
        modelStub.receipt.makeDone();
        CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "3.50")
            .execute(modelStub);

        //System.out.println(modelStub.personAmountBook.getPersonList().size());

        assertEquals(String.format(PaidCommand.MESSAGE_SUCCESS + TypicalPersons.ALICE.getName()
            + ". Amount owed decreased from " + "$3.50 to $0.00."), commandResult.getFeedbackToUser());

    }

    /**
     * For this test case I am testing if the PaidCommand is working as expected with a
     * pre creatied personamount book, with TYPICALPERSON.ALICE as index 1 and deducting fully from ALICE.
     * ALICE should be deleted from the list since she owes $0.00.
     * Ensuring that the person at INDEX 0 is different.
     * Previously it was ALICE, after command its BOB.
     * @throws Exception
     */
    @Test
    public void execute_paidCommandAcceptedByModel_deleteOfPersonSuccessful() throws Exception {

        ModelStubPaidCommand modelStub = new ModelStubPaidCommand();
        // Ensuring that the receipt is done before I can use PaidCommand.
        int initialNumberOfPersons = modelStub.personAmountBook.getPersonList().size();
        Name initialPersonAtIndexZero = modelStub.personAmountBook.getPersonByIndex(0).getName();
        modelStub.receipt.makeDone();
        CommandResult commandResult = new PaidCommand(ParserUtil.parseIndex("1"), "3.50")
            .execute(modelStub);

        int finalNumberOfPersons = modelStub.personAmountBook.getPersonList().size();
        Name finalPersonAtIndexZero = modelStub.personAmountBook.getPersonByIndex(0).getName();

        //Since I am comparing both int primitives I can use ==
        assertTrue(initialNumberOfPersons - 1 == finalNumberOfPersons);


        assertFalse(initialPersonAtIndexZero.equals(finalPersonAtIndexZero));

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


    @Test
    public void equals() {

        PaidCommand paidCommand = new PaidCommand(Index.fromOneBased(1), "1.00");

        // same object -> returns true
        assertTrue(paidCommand.equals(paidCommand));

        // different types -> returns false
        assertFalse(paidCommand.equals(1));

        //null -> returns false
        assertFalse(paidCommand.equals(null));

    }

    /**
     * This tests ensures that if all fields are null then it is not a valid command.
     * And that a NullPointerException.class is thrown
     */
    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidCommand(null, null));
    }

    /**
     * This tests ensures that if Index field is negative then it is not a valid command.
     * And that a NullPointerException.class is thrown
     */
    @Test
    public void constructor_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> new PaidCommand(ParserUtil.parseIndex("-1"),
            "1"));
    }




    /**
     * This tests ensures that if all fields are invalid then it is not a valid command.
     * And that a NullPointerException.class is thrown
     */
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PaidCommand(null, "1"));
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
