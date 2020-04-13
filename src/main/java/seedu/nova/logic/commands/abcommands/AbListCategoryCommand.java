package seedu.nova.logic.commands.abcommands;

import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class AbListCategoryCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List contacts according to category. "
            + "Parameters: "
            + PREFIX_CATEGORY + "[classmate/teammate]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "classmate ";

    @Override
    public abstract CommandResult execute(Model model);
}
