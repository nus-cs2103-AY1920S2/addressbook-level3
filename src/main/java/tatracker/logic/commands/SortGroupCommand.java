package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Sorts all students in the group.
 */
public class SortGroupCommand extends SortCommand {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            "Sorts all students in the given group.",
            List.of(SORT_TYPE, MODULE, GROUP),
            List.of(),
            SORT_TYPE, MODULE, GROUP
    );

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
