package seedu.address.logic.commands.major;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Major;

public class MajorSetCommand extends MajorCommand {
    public static final String COMMAND_WORD = "set";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "'" + getQualifiedCommand(COMMAND_WORD)
        + " command not implemented yet";
    public static final String MESSAGE_USAGE = getQualifiedCommand(COMMAND_WORD) + ": Sets the current student's Major";
    public static final String MESSAGE_SUCCESS = "Successfully updated Student's Major to: %1$s";

    private Major major;

    public MajorSetCommand(String major) {
        this.major = new Major(major);
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getActiveStudent().setMajor(major);
        return new CommandResult(String.format(MESSAGE_SUCCESS, major));
    }
}
