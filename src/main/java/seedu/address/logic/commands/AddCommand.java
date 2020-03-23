package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModuleManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Personal;

/**
 * Adds a profile to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a personal to the module. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_SEMESTER + "SEMESTER "
            + "(" + PREFIX_TASK + "TASK) "
            + "(" + PREFIX_DEADLINE + "DEADLINE) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_SEMESTER + "4"
            + "(" + PREFIX_TASK + "assignment) "
            + "(" + PREFIX_DEADLINE + "2020-03-16 23:59) ";

    private static final String MESSAGE_ADD_SUCCESS = "New Personal Object added: %1$s";
    private static final String MESSAGE_EDIT_SUCCESS = "Existing module updated: %1$s";

    private final Module toAdd;
    private int addSemester;
    private String addGrade;
    private String addTask;
    private String addDeadlineString;

    /**
     * Creates an AddCommand to add the specified {@code Profile}
     */
    /*public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    public AddCommand(Module module, boolean isUpdated) {
        requireNonNull(module);
        toAdd = module;
        MESSAGE_SUCCESS = "Existing module updated: %1$s";
    }*/

    public AddCommand(ModuleCode moduleCode, int semester, String grade, String task, String deadlineString) {
        requireNonNull(moduleCode);
        requireNonNull(semester);
        toAdd = ModuleManager.getModule(moduleCode);
        addSemester = semester;
        if (grade != null) {
            addGrade = grade;
        }
        if (task != null) {
            addTask = task;
        }
        if (deadlineString != null) {
            addDeadlineString = deadlineString;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Profile profile = model.getFirstProfile();
        boolean hasModule = false;
        Module module = toAdd;
        for (ArrayList<Module> list: profile.getHashMap().values()) {
            for (Module moduleItr: list) {
                if (toAdd.isSameModule(moduleItr)) {
                    hasModule = true;
                    module = moduleItr;
                }
            }
        }

        Personal personal;
        if (hasModule) { // Module exists
            personal = module.getPersonal();
            if (addGrade == null && addTask == null && addDeadlineString == null) {
                throw new CommandException(String.format("Error: Module already exists as "
                        + module.getPersonal().getStatus() + ", "
                        + "please specify date or add a deadline", AddCommand.MESSAGE_USAGE));
            }
        } else { // Module does not exist
            // Create Personal object
            personal = new Personal();
        }

        if (addGrade != null) {
            personal.setGrade(addGrade);
        }
        if (addTask != null) {
            Deadline deadline;
            if (addDeadlineString != null) {
                String date = addDeadlineString.split(" ")[0];
                String time = addDeadlineString.split(" ")[1];
                try {
                    deadline = new Deadline(addTask, date, time);
                    personal.addDeadline(deadline);
                } catch (DateTimeException e) {
                    throw new CommandException("Invalid date or time!");
                }
            } else {
                deadline = new Deadline(addTask);
            }
            personal.addDeadline(deadline);
        }

        int currentSemester = profile.getCurrentSemester();
        if (addSemester < currentSemester) {
            personal.setStatus("completed");
        } else if (addSemester == currentSemester) {
            personal.setStatus("in progress");
        } else {
            personal.setStatus("not taken");
        }

        module.setPersonal(personal);

        if (!hasModule) {
            profile.addModule(addSemester, module);
            return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, toAdd));
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, toAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
