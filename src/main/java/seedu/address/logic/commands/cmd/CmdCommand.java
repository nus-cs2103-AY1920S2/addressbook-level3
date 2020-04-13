package seedu.address.logic.commands.cmd;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Represents a cmd command with hidden logic and ability to be executed.
 */
public abstract class CmdCommand extends Command {
    public static final String ALL_FORMAT = String.format("%s %s", COMMAND_GROUP_CMD, COMMAND_WORD_ALL);
    public static final String GROUP_FORMAT =
            String.format("%s %s COMMAND_GROUP", COMMAND_GROUP_CMD, COMMAND_WORD_GROUP);
    public static final List<String> ALL_COMMAND_FORMATS = List.of(
            ALL_FORMAT, GROUP_FORMAT);
    public static final String MESSAGE_USAGE = "There are 2 cmd commands:\n"
            + "cmd all\n"
            + "cmd group COMMAND_GROUP";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
