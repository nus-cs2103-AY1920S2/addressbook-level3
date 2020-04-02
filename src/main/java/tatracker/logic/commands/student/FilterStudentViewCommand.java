package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_STUDENTS;
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
public class FilterStudentViewCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.FILTER_MODEL,
            "Filters the students in the TA-Tracker.",
            List.of(),
            List.of(GROUP, MODULE),
            GROUP, MODULE
    );

    public static final String MESSAGE_SUCCESS = "Filtered Student List: %1$s ";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Invalid Module Code. "
                        + "There are no students in the module code.";
    public static final String MESSAGE_INVALID_GROUP_CODE = "Invalid Group Code. "
                        + "There are no students in the group code.";

    public static final int FIRST_GROUP_INDEX = 0;

    private final String moduleCode;
    private final String groupCode;

    public FilterStudentViewCommand(String moduleCode, String groupCode) {

        this.moduleCode = moduleCode;
        this.groupCode = groupCode;
    }

    public boolean contains_module_only() {
        return !this.moduleCode.equals("") && this.groupCode.equals("");
    }

    public boolean contains_both() {
        return !this.moduleCode.equals("") && !this.groupCode.equals("");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CommandResult returnMsg = new CommandResult(MESSAGE_INVALID_MODULE_CODE, Action.FILTER_STUDENT);

        if (contains_module_only()) {
            returnMsg = filterModule(model);
        } else if (contains_both()) {
            returnMsg = filterGroup(model);
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode + " " + groupCode), Action.FILTER_STUDENT);
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
                throw new CommandException(MESSAGE_INVALID_STUDENTS);
            } else {
                model.setCurrStudentFilter("Module Code: " + moduleCode);
                model.setFilteredStudentList(moduleCode, FIRST_GROUP_INDEX);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode), Action.FILTER_STUDENT);
    }

    /**
     *Creates a string consisting of all the params inputted by users.
     */
    public String buildParams(String group, String module) {
        StringBuilder builder = new StringBuilder();
        builder.append("Module Code: ").append(module).append("\n");
        builder.append("Group Code: ").append(group).append("\n");
        String result = builder.toString();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterStudentViewCommand // instanceof handles nulls
                && (moduleCode.equals(((FilterStudentViewCommand) other).moduleCode)
                      && groupCode.equals(((FilterStudentViewCommand) other).groupCode))); // state check
    }
}
