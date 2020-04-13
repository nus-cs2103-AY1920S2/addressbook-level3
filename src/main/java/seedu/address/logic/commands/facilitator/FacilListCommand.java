package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITATORS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;

/**
 * Lists all facilitators in Mod Manager to the user.
 */
public class FacilListCommand extends FacilCommand {

    public static final String MESSAGE_SUCCESS = "Listed all facilitators";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);
        return new CommandResult(MESSAGE_SUCCESS, CommandType.FACILITATOR);
    }
}
