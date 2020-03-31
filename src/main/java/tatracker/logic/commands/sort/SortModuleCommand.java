package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Sorts all students of all groups in a module.
 */
public class SortModuleCommand extends SortCommand {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            CommandWords.SORT_MODULE,
            "Sorts all students in all groups of the given module.",
            List.of(MODULE, SORT_TYPE),
            List.of(),
            MODULE, SORT_TYPE
    );

    public static final String MESSAGE_SUCCESS = "Module %s has been sorted.";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final String MESSAGE_INVALID_SORT = "The only sort types are alphabetical,"
        + "by rating asc, by rating desc and matric.";
    public static final int FIRST_GROUP_INDEX = 0;

    private final String moduleCode;
    private final String type;

    public SortModuleCommand(String moduleCode, String type) {
        super(type);
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

        switch(type) {
        case "alphabetically":
        case "alpha":
        case "alphabetical":
            module.sortGroupsAlphabetically();
            break;
        case "matric":
            module.sortGroupsByMatricNumber();
            break;
        case "rating asc":
            module.sortGroupsByRatingAscending();
            break;
        case "rating desc":
            module.sortGroupsByRatingDescending();
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
                model.setFilteredStudentList(module.getIdentifier(), FIRST_GROUP_INDEX);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, module), Action.GOTO_STUDENT);
    }
}
