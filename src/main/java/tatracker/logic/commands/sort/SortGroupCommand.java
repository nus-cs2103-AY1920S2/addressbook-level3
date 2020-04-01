package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
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
            CommandWords.SORT_GROUP,
            "Sorts all students in the given group.",
            List.of(GROUP, MODULE, SORT_TYPE),
            List.of(),
            GROUP, MODULE, SORT_TYPE
    );

    public static final String MESSAGE_SUCCESS = "Group %s has been sorted.";
    public static final String MESSAGE_INVALID_GROUP_CODE = "This group doesn't exist in the TA-Tracker";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";

    private final String groupCode;
    private final String moduleCode;

    public SortGroupCommand(SortType type, String groupCode, String moduleCode) {
        super(type);
        this.groupCode = groupCode;
        this.moduleCode = moduleCode;
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

        switch(type) {
        case ALPHABETIC:
            model.sortModulesAlphabetically();
            break;
        case MATRIC:
            model.sortModulesByMatricNumber();
            break;
        case RATING_ASC:
            model.sortModulesByRatingAscending();
            break;
        case RATING_DESC:
            model.sortModulesByRatingDescending();
            break;
        default:
            throw new CommandException(SortType.MESSAGE_CONSTRAINTS);
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, group), Action.GOTO_STUDENT);
    }
}
