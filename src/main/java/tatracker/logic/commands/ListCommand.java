package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tatracker.model.Model;

/**
 * Lists all students in the TA-Tracker to the user.
 */
public class ListCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            "list",
            "Listed all students",
            List.of(),
            List.of()
    );

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
