package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Sorts all students of all groups in a module.
 */
public class SortModuleCommand extends SortCommand {

    public static final String COMMAND_WORD = "sort";

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in"
            + "all groups of the given module. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TYPE + "SORT TYPE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2100 "
            + PREFIX_TYPE + "alphabetically";

    public static final String MESSAGE_SUCCESS = "Module %s has been sorted.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

    private final String moduleCode;
    private String type;

    public SortModuleCommand(String moduleCode, String type) {
        super(type);
        this.moduleCode = moduleCode;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module module = new Module(moduleCode, "");

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        module = model.getModule(module);

        if (type.equalsIgnoreCase("alphabetically")
                || type.equalsIgnoreCase("alpha")) {
            module.sortGroupsAlphabetically();
        } else {
            module.sortGroupsByRating();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, module));
    }
}
