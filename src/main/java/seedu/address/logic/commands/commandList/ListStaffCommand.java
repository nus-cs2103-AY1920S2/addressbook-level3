package seedu.address.logic.commands.commandList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STAFFS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListStaffCommand extends ListCommand {

    public static final String COMMAND_WORD = "list-staff";

    public static final String MESSAGE_SUCCESS = "Listed all staffs";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        model.getMainWindow().callSwitchToStaff();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
