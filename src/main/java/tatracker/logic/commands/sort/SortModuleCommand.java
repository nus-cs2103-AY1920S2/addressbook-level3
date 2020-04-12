//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
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
public class SortModuleCommand extends SortGroupCommand {


    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            CommandWords.SORT_MODULE,
            "Sorts all students in all groups of the given module",
            List.of(MODULE, SORT_TYPE),
            List.of(),
            MODULE, SORT_TYPE
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_SORT_MODULE_SUCCESS = "All students in %s have been sorted";
    public static final int FIRST_GROUP_INDEX = 0;

    private final String moduleCode;

    public SortModuleCommand(SortType type, String moduleCode) {
        super(type);
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module module = model.getModule(moduleCode);

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
                model.setFilteredStudentList(module.getIdentifier(), FIRST_GROUP_INDEX);
            }
        }

        return new CommandResult(String.format(MESSAGE_SORT_MODULE_SUCCESS, moduleCode), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof SortModuleCommand)) {
            return false; // instanceof handles nulls
        }

        SortModuleCommand otherCommand = (SortModuleCommand) other;
        return moduleCode.equals(otherCommand.moduleCode)
                && type.equals(otherCommand.type);
    }

}
