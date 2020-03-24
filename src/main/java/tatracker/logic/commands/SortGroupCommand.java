package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Sorts all students in the group.
 */
public class SortGroupCommand extends SortCommand {

    public static final String COMMAND_WORD = "sort";

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in the given group. "
            + "Parameters: "
            + PREFIX_GROUP + "GROUP CODE "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TYPE + "SORT TYPE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "G06 "
            + PREFIX_MODULE + "CS2100 "
            + PREFIX_TYPE + "alphabetically";

    public static final String MESSAGE_SUCCESS = "Group %s has been sorted.";
    public static final String MESSAGE_INVALID_GROUP_CODE = "This group doesn't exist in the TA-Tracker";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

    private final String groupCode;
    private final String moduleCode;
    private final String type;

    public SortGroupCommand(String groupCode, String moduleCode, String type) {
        super(type);
        this.groupCode = groupCode;
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
        Group group = new Group(groupCode, null);

        if (!module.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        group = module.getGroup(groupCode);

        if (type.equalsIgnoreCase("alphabetically")
                || type.equalsIgnoreCase("alpha")) {
            group.sortStudentsAlphabetically();
        } else {
            //TODO: Sort students by rating
            //group.sortStudentsByRating();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, group));
    }
}
