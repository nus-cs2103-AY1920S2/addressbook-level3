// @@author cheyannesim
package seedu.address.logic.commands.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Loan;
import seedu.address.model.transaction.TransactionList;

/**
 * Records the amount of money the user owe to the person identified using its displayed index from the
 * address book.
 */
public class PeopleOweCommand extends Command {

    public static final String COMMAND_WORD = "owe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records the amount of money that you owe a"
            + " person.\nParameters: <index> (must be a positive integer) "
            + PREFIX_NAME + "<description> "
            + PREFIX_AMOUNT + "<amount> (must be positive) "
            + "[" + PREFIX_DATE + "<date:dd/mm/yyyy>]\n"
            + "Example: " + PEOPLE_COMMAND_TYPE + " "
            + COMMAND_WORD + " 4 "
            + PREFIX_NAME + "Supper "
            + PREFIX_AMOUNT + "5.00 "
            + PREFIX_DATE + "10/10/2020";

    public static final String MESSAGE_OWE_EXECUTION = "Adding debt (%1$s) to %2$s...";
    public static final String MESSAGE_OWE_SUCCESS = "Increased debt to %1$s by %2$s. You now owe %1$s %3$s.";

    private static final Logger logger = LogsCenter.getLogger(PeopleOweCommand.class);

    private final Index targetIndex;
    private final Debt debt;

    public PeopleOweCommand(Index targetIndex, Debt debt) {
        this.targetIndex = targetIndex;
        this.debt = debt;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personUserOwes = lastShownList.get(targetIndex.getZeroBased());
        logger.info(String.format(MESSAGE_OWE_EXECUTION, debt, personUserOwes.getName()));
        Person addedDebtPerson = createPersonOwed(personUserOwes, debt);
        model.setPerson(personUserOwes, addedDebtPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_OWE_SUCCESS, personUserOwes.getName(),
                debt.getAmount(), addedDebtPerson.getDebts().getTotal()));
    }

    /**
     * Creates and returns a {@code Person} after adding {@code debt} the user owes to the person.
     */
    private static Person createPersonOwed(Person personUserOwes, Debt debt) {
        assert personUserOwes != null;

        Name name = personUserOwes.getName();
        Phone phone = personUserOwes.getPhone();
        Email email = personUserOwes.getEmail();
        TransactionList<Debt> updatedDebts = new TransactionList<>();
        updatedDebts.setTransactions(personUserOwes.getDebts());
        updatedDebts.add(debt);
        TransactionList<Loan> loans = personUserOwes.getLoans();
        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personUserOwes.getTags());
        updatedTags.add(new Tag("Debt"));
        return new Person(name, phone, email, updatedDebts, loans, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PeopleOweCommand // instanceof handles nulls
                && targetIndex.equals(((PeopleOweCommand) other).targetIndex))
                && debt.equals(((PeopleOweCommand) other).debt); // state check
    }
}

