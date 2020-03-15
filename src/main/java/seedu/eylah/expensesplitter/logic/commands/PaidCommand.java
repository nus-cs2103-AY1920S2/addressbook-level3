package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.addressbook.model.person.Person; //change to .expensespliiter's Person
import seedu.eylah.expensesplitter.model.Model;

/**
 * Reduce an amount a Person owes you.
 */
public class PaidCommand extends Command {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_SUCCESS = "Successfully deducted amount from Person.";


    private final Person personPaid;

    /**
     * Creates an PaidCommand to add the specified {@code Person}
     */
    public PaidCommand (Person person) {
        requireNonNull(person);
        personPaid = person;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.paidPerson(personPaid);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
