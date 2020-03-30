package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.group.DeleteGroupCommand.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.ArrayList;
import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
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

    public static final int CONTAINS_MODULE_ONLY = 1;
    public static final int CONTAINS_MODULE_AND_GROUP = 2;

    public static final int MODULE_INDEX = 0;
    public static final int GROUP_INDEX = 1;

    public static final int FIRST_GROUP_INDEX = 0;

    private List<String> argsList = new ArrayList<>();

    public FilterStudentViewCommand(List<String> argsList) {
        this.argsList = argsList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CommandResult returnMsg = new CommandResult("");

        if (argsList.size() == CONTAINS_MODULE_ONLY) {
            returnMsg = filterModule(model);
        } else if (argsList.size() == CONTAINS_MODULE_AND_GROUP) {
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

        String moduleCode = argsList.get(MODULE_INDEX);
        String groupCode = argsList.get(GROUP_INDEX);

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
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Filter Students if users only give Module Code.
     * Module's first group will automatically be used.
     * @return filtered students
     * @throws CommandException
     */
    public CommandResult filterModule(Model model) throws CommandException {
        requireNonNull(model);

        String moduleCode = argsList.get(MODULE_INDEX);
        String groupCode = argsList.get(GROUP_INDEX);

        Module module = new Module(moduleCode);
        Group group = new Group(groupCode);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        } else {
            model.updateFilteredStudentList(groupCode, moduleCode);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterStudentViewCommand // instanceof handles nulls
                && argsList.equals(((FilterStudentViewCommand) other).argsList)); // state check
    }
}
