package seedu.address.logic.commands.commandAssign;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

public class AssignCommandBase extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_ASSIGNMENT_SUCCESS = "Assignment success";

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        return new CommandResult("");
    }
}
