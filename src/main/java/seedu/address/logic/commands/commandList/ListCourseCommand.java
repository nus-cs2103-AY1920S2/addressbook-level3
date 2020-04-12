package seedu.address.logic.commands.commandList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCourseCommand extends ListCommand {

    public static final String COMMAND_WORD = "list-course";

    public static final String MESSAGE_SUCCESS = "Listed all courses";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
        model.getMainWindow().callSwitchToCourse();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
