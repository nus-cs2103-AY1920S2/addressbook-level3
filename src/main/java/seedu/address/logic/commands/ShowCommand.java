package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

/**
 * Displays details requested by user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows modules taken in the semester. "
            + "Parameters: "
            + PREFIX_SEMESTER + "SEMESTER" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SEMESTER + "4";

    public static final String MESSAGE_SUCCESS = "All modules taken in this semester are shown.";

    private final ArrayList<Module> toShow;

    /**
     * Creates an ShowCommand to show the specified {@code Profile}
     * @param modules
     */
    public ShowCommand(ArrayList<Module> modules) {
        requireNonNull(modules);
        toShow = modules;


    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && toShow.equals(((ShowCommand) other).toShow));
    }
}
