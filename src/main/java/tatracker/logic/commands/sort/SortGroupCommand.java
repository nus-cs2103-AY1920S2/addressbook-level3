//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;

import java.util.List;

import tatracker.logic.commands.Command;
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
public class SortGroupCommand extends Command {

    //@@author potatocombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SORT,
            CommandWords.SORT_GROUP,
            "Sorts all students in the given group",
            List.of(GROUP, MODULE, SORT_TYPE),
            List.of(),
            GROUP, MODULE, SORT_TYPE
    );

    //@@author aakanksha-rai
    public static final String MESSAGE_SORT_GROUP_SUCCESS = "All students in %s [%s] have been sorted";

    protected final SortType type;

    private final String groupCode;
    private final String moduleCode;

    public SortGroupCommand(SortType type, String groupCode, String moduleCode) {
        this.type = type;
        this.groupCode = groupCode;
        this.moduleCode = moduleCode;
    }

    public SortGroupCommand(SortType type) {
        this.type = type;
        this.groupCode = "";
        this.moduleCode = "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module module = model.getModule(moduleCode);

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

        return new CommandResult(String.format(MESSAGE_SORT_GROUP_SUCCESS, moduleCode, groupCode), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof SortGroupCommand)) {
            return false; // instanceof handles nulls
        }

        SortGroupCommand otherCommand = (SortGroupCommand) other;
        return type.equals(otherCommand.type)
                && groupCode.equals(otherCommand.groupCode)
                && moduleCode.equals(((SortGroupCommand) other).moduleCode);
    }

}
