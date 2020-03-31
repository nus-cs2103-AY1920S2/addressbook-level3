package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.group.DeleteGroupCommand.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 *Filters by Group and/or Module under Student View.
 * A module can contains many groups.
 * A group contains students related it its group and module.
 */
public class FilterStudentViewCommand extends Command {

    public static final String COMMAND_WORD = String.format("%s %s", CommandWords.STUDENT, CommandWords.FILTER_MODEL);

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters students."
            + "Parameters: "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_MODULE + "MODULE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "G06"
            + PREFIX_MODULE + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Filtered Student List";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no students in the "
                            + "given group and/or module code.";

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

        CommandResult returnMsg = new CommandResult("", Action.FILTER_STUDENT);

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
     * @throws CommandException
     */
    public CommandResult filterGroup(Model model) throws CommandException {
        requireNonNull(model);

        Module module = new Module(moduleCode);
        Group group = new Group(groupCode);

        if (!model.hasModule(module)) {
            throw new CommandException((MESSAGE_INVALID_MODULE_CODE));
        } else {
            if (!model.hasGroup(group, module)) {
                throw new CommandException(((MESSAGE_INVALID_GROUP_CODE)));
            } else {
                model.updateFilteredStudentList(groupCode, moduleCode);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS), Action.FILTER_STUDENT);
    }

    /**
     * Filter Students if users only give Module Code.
     * Module's first group will automatically be used.
     * @return filtered students
     * @throws CommandException
     */
    public CommandResult filterModule(Model model) throws CommandException {
        requireNonNull(model);

        Module module = new Module(moduleCode);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        } else {
            model.updateFilteredGroupList(moduleCode);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredGroupList();
            } else {
                model.setFilteredStudentList(moduleCode, FIRST_GROUP_INDEX);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS), Action.FILTER_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterStudentViewCommand // instanceof handles nulls
                && (moduleCode.equals(((FilterStudentViewCommand) other).moduleCode)
                      && groupCode.equals(((FilterStudentViewCommand) other).groupCode))); // state check
    }
}
