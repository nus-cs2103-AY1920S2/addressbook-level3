package seedu.address.logic.commands.commandList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

public class ListAssignmentCommand extends ListCommand {
    public static final String COMMAND_WORD = "list-assignment";

    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        if (model.getMainWindow() != null) {
            model.getMainWindow().callSwitchToAssignment();
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
