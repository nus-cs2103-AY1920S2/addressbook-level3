package seedu.address.logic.commands.academics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays an administrative version of the students list.
 */
public class AcademicsDisplayExamCommand extends AcademicsCommand {

    public static final String MESSAGE_SUCCESS = "Academics now displays all EXAM assessments";

    /**
     * Creates an AcademicsDisplayExamCommand.
     */
    public AcademicsDisplayExamCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }
}
