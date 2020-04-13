package seedu.address.logic.commands.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
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
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Loan;
import seedu.address.model.transaction.TransactionList;

/**
 * Records that a debt that the user owe, identified using its displayed index in the Debts table, has been
 * returned to the person identified using its displayed index from the address book.
 */
public class PeopleReturnedCommand extends Command {
    public static final String COMMAND_WORD = "returned";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records that you have returned a debt that"
            + " you owed. Parameters: <person's index> (must be a positive integer) "
            + " i/[<debt's index> (must be a positive integer)]\n"
            + "Example: " + PEOPLE_COMMAND_TYPE + " "
            + COMMAND_WORD + " 4 1";

    public static final String MESSAGE_RETURNED_EXECUTION = "Removing debt(s) from %1$s...";
    public static final String MESSAGE_RETURNED_SUCCESS = "Reduced debt to %1$s by %2$s. You now owe %1$s "
            + "%3$s.";
    public static final String MESSAGE_NO_DEBT = "You don't owe %1$s any money :)";

    private static final Logger logger = LogsCenter.getLogger(PeopleReturnedCommand.class);

    private final Index targetPersonIndex;
    private final Index targetDebtIndex;

    public PeopleReturnedCommand(Index targetPersonIndex, Index targetDebtIndex) {
        this.targetPersonIndex = targetPersonIndex;
        this.targetDebtIndex = targetDebtIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetPersonIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personUserOwes = lastShownList.get(targetPersonIndex.getZeroBased());

        if (personUserOwes.getDebts().getTotal().isZero()) {
            throw new CommandException(String.format(MESSAGE_NO_DEBT, personUserOwes.getName()));
        }

        logger.info(String.format(MESSAGE_RETURNED_EXECUTION, personUserOwes.getName()));
        Person personReducedDebt = createPersonReturned(personUserOwes, targetDebtIndex);
        Amount amountReturned = getAmountReturned(personUserOwes, targetDebtIndex);
        model.setPerson(personUserOwes, personReducedDebt);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_RETURNED_SUCCESS, personUserOwes.getName(),
                amountReturned, personReducedDebt.getDebts().getTotal()));
    }

    /**
     * Returns the {@code Amount} that the user has returned {@code personUserOwes}.
     */
    public static Amount getAmountReturned(Person personUserOwes, Index targetDebtIndex) {
        Amount amountReturned;
        if (targetDebtIndex == null) {
            amountReturned = personUserOwes.getDebts().getTotal();
        } else {
            List<Debt> debts = personUserOwes.getDebts().asUnmodifiableObservableList();
            assert targetDebtIndex.getZeroBased() < debts.size();
            amountReturned = debts.get(targetDebtIndex.getZeroBased()).getAmount();
        }
        return amountReturned;
    }

    /**
     * Creates and returns a {@code Person} after returning the debt(s).
     */
    private static Person createPersonReturned(Person personUserOwes, Index targetDebtIndex)
            throws CommandException {
        assert personUserOwes != null;

        Name name = personUserOwes.getName();
        Phone phone = personUserOwes.getPhone();
        Email email = personUserOwes.getEmail();
        TransactionList<Debt> updatedDebts = new TransactionList<>();
        TransactionList<Loan> loans = personUserOwes.getLoans();
        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personUserOwes.getTags());

        if (targetDebtIndex != null) {
            List<Debt> debts = personUserOwes.getDebts().asUnmodifiableObservableList();

            if (targetDebtIndex.getZeroBased() >= debts.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
            }

            Debt debtReturned = debts.get(targetDebtIndex.getZeroBased());
            updatedDebts.setTransactions(personUserOwes.getDebts());
            updatedDebts.remove(debtReturned);
        }

        if (targetDebtIndex == null || updatedDebts.asUnmodifiableObservableList().isEmpty()) {
            updatedTags.remove(new Tag("Debt"));
        }

        return new Person(name, phone, email, updatedDebts, loans, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PeopleReturnedCommand // instanceof handles nulls
                && targetPersonIndex.equals(((PeopleReturnedCommand) other).targetPersonIndex))
                && (targetDebtIndex == ((PeopleReturnedCommand) other).targetDebtIndex
                || targetDebtIndex.equals(((PeopleReturnedCommand) other).targetDebtIndex)); // state check
    }
}
