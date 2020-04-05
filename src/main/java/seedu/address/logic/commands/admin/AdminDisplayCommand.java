package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays the last updated administrative version of the students list.
 */
public class AdminDisplayCommand extends AdminCommand {

    public static final String MESSAGE_SUCCESS = "The Student list now displays last updated ADMIN details";

    /**
     * Creates an AdminDisplayCommand.
     */
    public AdminDisplayCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) { //dummy method here
        return false;
    }
}
