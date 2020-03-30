package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Sorts all students in the group.
 */
public class SortGroupCommand extends SortCommand {

    public static final String COMMAND_WORD = CommandWords.SORT + " " + CommandWords.SORT_GROUP;

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in the given group. \n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP CODE "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TYPE + "SORT TYPE \n"
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

        module = model.getModule(module.getIdentifier());
        Group group = new Group(groupCode, null);

        if (!module.hasGroup(group)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        group = module.getGroup(groupCode);

        if (type.equalsIgnoreCase("alphabetically")
                || type.equalsIgnoreCase("alpha")) {
            group.sortStudentsAlphabetically();
        } else if (type.equalsIgnoreCase("matric")) {
            group.sortStudentsByMatricNumber();
        } else if (type.equalsIgnoreCase("rating asc")) {
            group.sortStudentsByRatingAscending();
        } else {
            group.sortStudentsByRatingDescending();
        }

        if (model.getFilteredModuleList().isEmpty()) {
            model.setFilteredGroupList();
            model.setFilteredStudentList();
        } else {
            model.updateFilteredGroupList(module.getIdentifier());
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.updateFilteredStudentList(group.getIdentifier(), module.getIdentifier());
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, group));
    }
}
