//@@author Chuayijing
package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 *Filters by Group and/or Module under Student View.
 * A module can contains many groups.
 * A group contains students related it its group and module.
 */
public class FilterStudentCommand extends Command {

    //@@author PotatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.FILTER_MODEL,
            "Filters the students inside TA-Tracker",
            List.of(),
            List.of(GROUP, MODULE),
            GROUP, MODULE
    );

    // @@author Chuayijing

    public static final String MESSAGE_FILTERED_MODULES_SUCCESS = "Filtered all students in module: %s";
    public static final String MESSAGE_FILTERED_GROUPS_SUCCESS = "Filtered all students in module group: %s [%s]";

    public static final String MESSAGE_NO_STUDENTS_IN_MODULE = "There are no students in the module"
            + " with the given module code";
    public static final String MESSAGE_NO_STUDENTS_IN_GROUP = "There are no students in the module group"
            + " with the given group code";

    public static final int FIRST_GROUP_INDEX = 0;

    private final String moduleCode;
    private final String groupCode;

    public FilterStudentCommand(String moduleCode, String groupCode) {

        this.moduleCode = moduleCode;
        this.groupCode = groupCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CommandResult returnMsg = new CommandResult(MESSAGE_INVALID_MODULE_CODE, Action.FILTER_STUDENT);

        boolean hasModule = !moduleCode.isBlank();
        boolean hasGroup = hasModule && !groupCode.isBlank();

        if (hasGroup) {
            returnMsg = filterGroup(model);
        } else if (hasModule) {
            returnMsg = filterModule(model);
        }
        return returnMsg;
    }

    /**
     * Filter Students when both Group code and Module Code given by User.
     * @return a Successful Command Result
     * @throws CommandException if the module or group code is invalid.
     */
    public CommandResult filterGroup(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        } else {
            if (!model.hasGroup(groupCode, moduleCode)) {
                model.setFilteredStudentList();
                throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
            } else {
                String result = buildParams(groupCode, moduleCode);
                model.setCurrStudentFilter(result);
                model.updateFilteredGroupList(moduleCode);
                model.updateFilteredStudentList(groupCode, moduleCode);
            }
        }
        return new CommandResult(String.format(MESSAGE_FILTERED_GROUPS_SUCCESS, moduleCode, groupCode),
                Action.FILTER_STUDENT);
    }

    /**
     * Filter Students if users only give Module Code.
     * Module's first group will automatically be used.
     * @return filtered students
     * @throws CommandException if the module code is invalid.
     */
    public CommandResult filterModule(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        } else {
            model.updateFilteredGroupList(moduleCode);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
                throw new CommandException(MESSAGE_NO_STUDENTS_IN_MODULE);
            } else {
                model.setCurrStudentFilter("Module Code: " + moduleCode);
                model.setFilteredStudentList(moduleCode, FIRST_GROUP_INDEX);
            }
        }
        return new CommandResult(String.format(MESSAGE_FILTERED_MODULES_SUCCESS, moduleCode), Action.FILTER_STUDENT);
    }

    /**
     *Creates a string consisting of all the params inputted by users.
     */
    public String buildParams(String group, String module) {
        return new StringBuilder()
                .append("Module Code: ").append(module).append("\n")
                .append("Group Code: ").append(group).append("\n")
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterStudentCommand // instanceof handles nulls
                && (moduleCode.equals(((FilterStudentCommand) other).moduleCode)
                      && groupCode.equals(((FilterStudentCommand) other).groupCode))); // state check
    }
}
