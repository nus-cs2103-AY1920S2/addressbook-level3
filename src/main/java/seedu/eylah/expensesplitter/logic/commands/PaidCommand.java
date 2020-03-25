package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException;
import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Reduce an amount a Person owes you.
 */
public class PaidCommand extends Command {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_SUCCESS = "Successfully deducted amount from Person named: ";


    //private final Person personPaid;
    private final int indexOfPersonPaid;
    private final String amountPaid;

    /**
     * Creates an PaidCommand to add the specified {@code Person}
     */
    public PaidCommand (int indexOfPersonPaid, String amountPaid) {
        requireNonNull(indexOfPersonPaid);
        requireNonNull(amountPaid);
        this.indexOfPersonPaid = indexOfPersonPaid;
        this.amountPaid = amountPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyPersonAmountBook book = model.getPersonAmountBook();

        //This ensures that the indexOfPersonPaid is correct.
        if (indexOfPersonPaid < 0 || indexOfPersonPaid > book.getPersonList().size() - 1) {
            throw new CommandException("Index of Person is incorrect. Please use `listamount` to get the index.");
        }

        Person person = book.getPersonByIndex(indexOfPersonPaid);
        String initialAmount = person.getAmount().toString();

        //This ensures that amountPaid is correct. p.getAmount MUST be BIGGER OR EQUAL TO AMOUNT PAID
        if (person.getAmount().amount.compareTo(new BigDecimal(amountPaid)) == -1) {
            throw new CommandException("Person cannot pay more than what he owes you. Please use `listamount` "
                + "to get the index.");
        }

        model.paidPerson(person, amountPaid);
        String finalAmount = person.getAmount().toString();

        return new CommandResult(MESSAGE_SUCCESS + person.getName() + ". Amount decreased from "
            + initialAmount + " to " + finalAmount + ".");
    }

}
