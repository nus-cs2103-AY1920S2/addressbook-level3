package seedu.address.logic.commands.commandList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListStudentCommand extends ListCommand {

    public static final String COMMAND_WORD = "list-student";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.getMainWindow().callSwitchToStudent();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
