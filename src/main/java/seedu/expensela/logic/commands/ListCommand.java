package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import seedu.expensela.model.Model;

/**
 * Lists all transactions in the transaction list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all transactions";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateUnfilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
