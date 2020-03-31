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
    public static final String MESSAGE_INVALID_SORT = "The only sort types are alphabetical,"
            + "by rating asc, by rating desc and matric.";

    private final String groupCode;
    private final String moduleCode;
    private final String type;

    public SortGroupCommand(String groupCode, String moduleCode, String type) {
        super(type);
        this.groupCode = groupCode;
        this.moduleCode = moduleCode;
        this.type = type.toLowerCase();
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
        case "alphabetically":
        case "alpha":
        case "alphabetical":
            group.sortStudentsAlphabetically();
            break;
        case "matric":
            group.sortStudentsByMatricNumber();
            break;
        case "rating asc":
            group.sortStudentsByRatingAscending();
            break;
        case "rating desc":
            group.sortStudentsByRatingDescending();
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_SORT);
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
