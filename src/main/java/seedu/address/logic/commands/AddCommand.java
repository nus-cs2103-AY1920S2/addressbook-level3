package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleList;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
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

    public static final String MESSAGE_ADD_SUCCESS = "New Personal Object added: %1$s";
    public static final String MESSAGE_EDIT_SUCCESS = "Existing module updated: %1$s";
    public static final String MESSAGE_DEADLINE_INVALID_SEMESTER = "Error: Deadlines must be added to modules taken in "
            + "the current semester";
    public static final String MESSAGE_DUPLICATE_MODULE = "Error: Module already exists as %1$s, "
            + "please specify date or add a deadline";
    public static final String MESSAGE_UNFULFILLED_PREREQS = "NOTE: You may not have fulfilled the prerequisites of "
            + "%1$s before semester %2$s\nPrerequisites: %3$s";

    private final ModuleCode toAdd;
    private int addSemester;
    private String addGrade;
    private String addTask;
    private String addDeadlineString;

    public AddCommand(ModuleCode moduleCode, int semester, String grade,
                      String task, String deadlineString) {
        requireNonNull(moduleCode);
        requireNonNull(semester);

        toAdd = moduleCode;
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
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);


        Profile profile = profileManager.getFirstProfile();
        boolean hasModule = false;
        Module moduleToAdd = moduleManager.getModule(toAdd);
        int semesterOfModule = 0;

        // Check whether this module has been added to Profile semester HashMap
        for (ModuleList semesterList: profile.getSemModHashMap().values()) {
            for (Module moduleInSem: semesterList) {
                if (moduleToAdd.isSameModule(moduleInSem)) {
                    hasModule = true;
                    moduleToAdd = moduleInSem;
                    HashMap<Integer, ModuleList> hashMap = profile.getSemModHashMap();
                    semesterOfModule = getKey(hashMap, semesterList);
                }
            }
        }

        Personal personal;
        if (hasModule) { // Module already added to semester
            personal = moduleToAdd.getPersonal();
            if (addGrade == null && addTask == null && addDeadlineString == null) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE,
                        moduleToAdd.getPersonal().getStatus()));
            }
        } else { // Module does not exist
            if (addSemester == 0) {
                throw new CommandException("Error: Please specify semester.");
            }
            // Create Personal object
            personal = new Personal();

        }

        int currentSemester = profile.getCurrentSemester();

        if (addGrade != null) {
            personal.setGrade(addGrade);
        }
        if (addTask != null) {
            // Check if the deadline is added to a module in the current semester
            if (addSemester != currentSemester) {
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }
            Deadline deadline;
            String moduleCode = toAdd.toString();
            if (addDeadlineString != null) {
                String date = addDeadlineString.split(" ")[0];
                String time = addDeadlineString.split(" ")[1];
                try {
                    deadline = new Deadline(moduleCode, addTask, date, time);
                } catch (DateTimeException e) {
                    throw new CommandException("Invalid date or time!");
                }
            } else {
                deadline = new Deadline(moduleCode, addTask);
            }

            personal.addDeadline(deadline);
            profileManager.addDeadline(deadline);

        }

        // Set the status of the module
        if (addSemester < currentSemester) {
            personal.setStatus("completed");
        } else if (addSemester == currentSemester) {
            personal.setStatus("in progress");
        } else {
            personal.setStatus("not taken");
        }

        moduleToAdd.setPersonal(personal);

        String messageShown;
        if (!hasModule) {
            profile.addModule(addSemester, moduleToAdd);
            // Check if prerequisites of the module have been fulfilled
            if (moduleToAdd.getPrereqTreeNode() != null && !moduleToAdd.getPrereqTreeNode()
                    .hasFulfilledPrereqs(profile.getAllModuleCodesBefore(addSemester))) {
                messageShown = String.format(MESSAGE_UNFULFILLED_PREREQS, toAdd, addSemester, moduleToAdd.getPrereqs());
            } else {
                messageShown = MESSAGE_ADD_SUCCESS;
            }
            profileManager.setDisplayedView(profile);
            return new CommandResult(String.format(messageShown, toAdd), true);
        } else {
            messageShown = MESSAGE_EDIT_SUCCESS;
        }

        profile.updateCap();
        return new CommandResult(String.format(messageShown, toAdd), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
