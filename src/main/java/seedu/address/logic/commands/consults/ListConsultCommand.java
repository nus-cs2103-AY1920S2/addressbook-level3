package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all consults in TAble.
 */
public class ListConsultCommand extends Command {

    public static final String COMMAND_WORD = "listConsult";

    public static final String MESSAGE_SUCCESS = COMMAND_WORD + ": Listed all consults. ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredConsultList(PREDICATE_SHOW_ALL_CONSULTS);

        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
