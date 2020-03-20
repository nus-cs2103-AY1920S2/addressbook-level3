package seedu.address.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listmanagers.activity.Activity;
/**
 * Adds a Activity to the Activity manager.
 */
public class AddActivityCommand extends Command {

    public static final String COMMAND_WORD = "addactivity";

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the scheduler";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the activity list"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE"
            + PREFIX_PRIORITY + "PRIORITY"
            + PREFIX_DURATION + "DURATION"
            + PREFIX_TAG + "TAGS...\n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Osaka Castle View "
            + PREFIX_PRIORITY + "1 "
            + PREFIX_DURATION + "1 "
            + PREFIX_LOCATION + "Osaka "
            + PREFIX_TAG + "expensive "
            + PREFIX_TAG + "weeb";;

    public static final String MESSAGE_SUCCESS = "New Activity added: %1$s";
    private final Activity toAdd;

    /**
     * Creates an AddActivityCommand to the specified {@code Activity}
     */
    public AddActivityCommand(Activity toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }
}
