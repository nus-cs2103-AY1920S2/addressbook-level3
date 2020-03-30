package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;
import static tatracker.logic.parser.Prefixes.TYPE;

import java.util.List;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Sorts all students in the group.
 */
public class SortGroupCommand extends SortCommand {

    public static final String COMMAND_WORD = CommandWords.SORT;

    public static final List<Prefix> PARAMETERS = List.of(SORT_TYPE, MODULE, GROUP);

    public static final String INFO = "Sorts all students in the given group.";
    public static final String USAGE = PrefixDictionary.getPrefixesWithInfo(PARAMETERS);
    public static final String EXAMPLE = PrefixDictionary.getPrefixesWithExamples(SORT_TYPE, MODULE, GROUP);

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in the given group. "
            + "Parameters: "
            + GROUP + "GROUP CODE "
            + MODULE + "MODULE CODE "
            + TYPE + "SORT TYPE "
            + "Example: " + COMMAND_WORD + " "
            + GROUP + "G06 "
            + MODULE + "CS2100 "
            + TYPE + "alphabetically";

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
