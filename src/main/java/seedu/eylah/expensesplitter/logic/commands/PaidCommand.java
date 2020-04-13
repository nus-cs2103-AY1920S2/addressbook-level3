package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Reduce the amount a Person owes you.
 */
public class PaidCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_SUCCESS = "Successfully deducted amount from person named: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deducts the amount of Person identified by the index number of the Person in listamount.\n"
        + "Parameters: INDEX and AMOUNT (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1" + " 3.30";

    public static final String PROPER_AMOUNT = "Amount paid should only contains numerical characters."
            + "It should not contain any alphabets and only has a maximum of 2 decimal places.";

    public static final String MESSAGE_RECEIPT_UNDONE = "The current receipt is marked as incomplete. You may not "
            + "use the paid command.\n"
            + "To mark the current receipt as done, please use the donereceipt command.";

    //private final Person personPaid;
    private final Index indexOfPersonPaid;
    private final String amountPaid;

    /**
     * Creates an PaidCommand to add the specified {@code Person}
     */
    public PaidCommand (Index indexOfPersonPaid, String amountPaid) {
        requireNonNull(indexOfPersonPaid);
        requireNonNull(amountPaid);
        this.indexOfPersonPaid = indexOfPersonPaid;
        this.amountPaid = amountPaid;

    }

    @Override
    public CommandResult execute(SplitterModel splitterModel) throws CommandException {
        requireNonNull(splitterModel);

        if (!splitterModel.isReceiptDone()) {

            return new CommandResult(MESSAGE_RECEIPT_UNDONE);

        } else {
            ReadOnlyPersonAmountBook book = splitterModel.getPersonAmountBook();
            //This ensures that the indexOfPersonPaid is correct.
            if (indexOfPersonPaid.getZeroBased() < 0
                || indexOfPersonPaid.getZeroBased() > book.getPersonList().size() - 1) {
                throw new CommandException("Index of person is incorrect. Please use `listamount` to get the index.");
            }

            Person person = book.getPersonByIndex(indexOfPersonPaid.getZeroBased());

            String initialAmount = person.getAmount().toString();


            // This cases handles when user key in `paid 1` which stands for paid the full amount Person in Index 1
            // owes.
            if (amountPaid.equals("all")) {
                PaidCommand newPaidCommand = new PaidCommand(indexOfPersonPaid, initialAmount.substring(1));
                return newPaidCommand.execute(splitterModel);

            } else {
                //This ensures that amountPaid is correct. p.getAmount MUST be BIGGER OR EQUAL TO AMOUNT PAID
                if (person.getAmount().amount.compareTo(new BigDecimal(amountPaid)) == -1) {
                    throw new CommandException("Person cannot pay more than what he owes you. Please use `listamount` "
                        + "to get the index of person and amount he owes you.");
                }

                splitterModel.paidPerson(person, amountPaid);
                String finalAmount = person.getAmount().toString();
                return new CommandResult(MESSAGE_SUCCESS + person.getName() + ". Amount owed decreased "
                        + "from " + initialAmount + " to " + finalAmount + ".");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PaidCommand
            && indexOfPersonPaid.equals(((PaidCommand) other).indexOfPersonPaid)
            && amountPaid.equals(((PaidCommand) other).amountPaid));
    }

}
