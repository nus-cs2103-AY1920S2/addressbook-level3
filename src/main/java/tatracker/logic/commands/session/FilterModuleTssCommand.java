package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.DoneSessionPredicate;

/**
 * Filter Module based on user's inputs.
 * Used under TSS view.
 */
public class FilterModuleTssCommand extends Command {

    public static final String COMMAND_WORD = String.format("%s %s", CommandWords.MODULE, CommandWords.FILTER_MODEL);

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters module."
        + "Parameters: "
        + "[" + PREFIX_MODULE + "MODULE] "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Filtered Module List";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no sessions with the given module code.";

    private final DoneSessionPredicate predicate;

    public FilterModuleTssCommand(DoneSessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDoneSessionList(predicate, predicate.getModuleCode());
        return new CommandResult(
                String.format(Messages.MESSAGE_SESSIONS_LISTED_OVERVIEW,
                                model.getFilteredDoneSessionList().size()),
                Action.FILTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterModuleTssCommand // instanceof handles nulls
                && predicate.equals(((FilterModuleTssCommand) other).predicate)); // state check
    }

}
