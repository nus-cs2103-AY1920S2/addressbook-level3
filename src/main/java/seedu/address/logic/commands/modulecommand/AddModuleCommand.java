package seedu.address.logic.commands.modulecommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES_TAKEN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.NusModule;

/**
 * Adds a module to our application.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "moduleAdd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for adding modules "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_GRADE + "GRADE [Optional] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_GRADE + "A+ ";

    public static final String MESSAGE_SUCCESS = "Module added: ";
    public static final String MESSAGE_DUPLICATE_NUS_MODULE = "module already exist";

    private final NusModule toAdd;

    /**
     * Creates an AddModuleCommand to add the specified {@code NusModule}
     */
    public AddModuleCommand(NusModule module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd.getModuleCode())) {
            throw new CommandException(MESSAGE_DUPLICATE_NUS_MODULE);
        }

        model.addModule(toAdd);
        model.updateModulesListTaken(PREDICATE_SHOW_ALL_MODULES_TAKEN);
        return new CommandResult(MESSAGE_SUCCESS + toAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }

}
