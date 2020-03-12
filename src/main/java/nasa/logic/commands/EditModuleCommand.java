package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

<<<<<<< HEAD:src/main/java/nasa/logic/commands/EditModuleCommand.java
import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
=======
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.commons.core.index.Index;
>>>>>>> cf98652144e57734b46653f44f8db289a6e30a49:src/main/java/NASA/logic/commands/EditModuleCommand.java

public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "medit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the index number used in the displayed NASA application. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE" +
            "INDEX (must be a positive integer)"
            + "[" + PREFIX_MODULE + "MODULE CODE] "
            + "[" + PREFIX_MODULE_NAME + "MODULE NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_MODULE + "CS2030"
            + "1"
            + PREFIX_MODULE + "CS2020"
            + PREFIX_MODULE_NAME + "Data Structures and Algorithms II";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This activity already exists in the module activity list.";

    private final Index index;

    public EditModuleCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditModuleCommand // instanceof handles nulls
                && index.equals(((EditModuleCommand) other).index));
    }
}
