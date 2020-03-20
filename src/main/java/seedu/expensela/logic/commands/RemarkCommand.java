package seedu.expensela.logic.commands;

import seedu.expensela.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from remark");
    }
}