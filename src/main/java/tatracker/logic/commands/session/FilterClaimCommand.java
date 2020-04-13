//@@author Chuayijing

package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Filter Module based on user's inputs.
 * Used under TSS view.
 */
public class FilterClaimCommand extends Command {

    //@@author potatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.CLAIM,
            CommandWords.FILTER_MODEL,
            "Filters a claim of a particular module",
            List.of(MODULE),
            List.of(),
            MODULE
    );

    //@@author Chuayijing
    public static final String MESSAGE_FILTERED_CLAIMS_SUCCESS = "Filtered claims for module: %s";

    private final DoneSessionPredicate predicate;

    public FilterClaimCommand(DoneSessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String module = predicate.getModuleCode();
        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }
        model.setCurrClaimFilter("Module: " + module);
        model.updateFilteredDoneSessionList(predicate, module);
        return new CommandResult(String.format(MESSAGE_FILTERED_CLAIMS_SUCCESS, module),
                    Action.FILTER_CLAIMS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterClaimCommand // instanceof handles nulls
                && predicate.equals(((FilterClaimCommand) other).predicate)); // state check
    }
}
