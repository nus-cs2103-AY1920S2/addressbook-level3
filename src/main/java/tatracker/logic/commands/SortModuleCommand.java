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
    public static final int FIRST_GROUP_INDEX = 0;

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

        module = model.getModule(module.getIdentifier());

        if (type.equalsIgnoreCase("alphabetically")
                || type.equalsIgnoreCase("alpha")) {
            module.sortGroupsAlphabetically();
        } else if (type.equalsIgnoreCase("matric")) {
            module.sortGroupsByMatricNumber();
        } else if (type.equalsIgnoreCase("rating asc")) {
            module.sortGroupsByRatingAscending();
        } else {
            module.sortGroupsByRatingDescending();
        }

        if (model.getFilteredModuleList().isEmpty()) {
            model.setFilteredGroupList();
            model.setFilteredStudentList();
        } else {
            model.updateFilteredGroupList(module.getIdentifier());
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.setFilteredStudentList(module.getIdentifier(), FIRST_GROUP_INDEX);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, module));
    }
}
