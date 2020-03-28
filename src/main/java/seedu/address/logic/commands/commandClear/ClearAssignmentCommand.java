package seedu.address.logic.commands.commandClear;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentAddressBook;

import static java.util.Objects.requireNonNull;

public class ClearAssignmentCommand extends ClearCommand {
    public static final String COMMAND_WORD = "clear-assignments";
    public static final String MESSAGE_SUCCESS = "All assignments in the database has been removed!";


    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        model.setAssignmentAddressBook(new AssignmentAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
