package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DEADLINE_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_MODULE_DATA;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MODULE_NOT_ADDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleList;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.ModuleNotFoundException;
import seedu.address.model.profile.course.module.personal.Deadline;

//@@author joycelynteo

/**
 * Edits Profile or Module specified by user.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a profile or module specified by user.\n"
            + "Parameters to edit a profile: "
            + PREFIX_NAME + "NAME "
            + "(" + PREFIX_COURSE_NAME + "COURSE) "
            + "(" + CliSyntax.PREFIX_YEAR + "CURRENT_SEMESTER) "
            + "(" + PREFIX_FOCUS_AREA + "FOCUS_AREA) "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + "(" + PREFIX_COURSE_NAME + "Computer Science) "
            + "(" + CliSyntax.PREFIX_YEAR + "4) "
            + "(" + PREFIX_FOCUS_AREA + "Algorithms & Theory) "
            + "\nParameters to edit a module: "
            + PREFIX_MODULE + "MODULE "
            + "(" + PREFIX_YEAR + "SEMESTER) "
            + "(" + PREFIX_GRADE + "GRADE) "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + "(" + PREFIX_YEAR + "4) "
            + "(" + PREFIX_GRADE + "A+) ";

    public static final String MESSAGE_EDIT_PROFILE_SUCCESS = "Edited Profile: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";

    private boolean toEditProfile = false;
    private Name profileName = null;
    private CourseName courseName = null;
    private int updatedSemester = 0;
    private String focusAreaString = null;
    private FocusArea focusArea = null;

    private boolean editModule = false;
    private ModuleCode moduleCode;
    private int editSemester = 0;
    private String grade = null;
    private String oldTask = null;
    private String newTask = null;
    private String newDeadlineString = null;
    private int inSemester = 0;

    public EditCommand(Name name, CourseName courseName, int updatedSemester, String focusAreaString) {
        toEditProfile = true;
        this.profileName = name;
        this.courseName = courseName;
        this.updatedSemester = updatedSemester;
        this.focusAreaString = focusAreaString;
    }

    public EditCommand(ModuleCode moduleCode, int editSemester, String grade, String oldTask, String newTask,
                       String newDeadline) {
        editModule = true;
        this.moduleCode = moduleCode;
        this.editSemester = editSemester;
        this.grade = grade;
        this.oldTask = oldTask;
        this.newTask = newTask;
        this.newDeadlineString = newDeadline;
    }

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        List<Profile> lastShownList = profileManager.getFilteredPersonList();
        Profile profileToEdit;
        try {
            profileToEdit = lastShownList.get(0); //accessing only first profile in list
        } catch (Exception e) {
            throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
        }

        boolean showCommand = false;

        if (editModule) {

            HashMap<Integer, ModuleList> hashMap = profileToEdit.getSemModHashMap();
            if (hashMap.isEmpty()) { // No modules have been added to any semester at all
                throw new CommandException(MESSAGE_EMPTY_MODULE_DATA);
            }

            // Checks if module has been added to any semesters before
            Module module = moduleManager.getModule(moduleCode);
            Module existingModule = null;
            int oldSemester = 0;

            for (ModuleList moduleList: hashMap.values()) {
                for (Module moduleItr: moduleList) {
                    if (module.isSameModule(moduleItr)) {
                        existingModule = moduleItr;
                        oldSemester = getKey(hashMap, moduleList);
                    }
                }
            }

            if (existingModule == null) {
                throw new CommandException(MESSAGE_MODULE_NOT_ADDED);
            }

            if (grade != null) {
                int currentUserSemester = profileToEdit.getOverallSemester();
                existingModule.getPersonal().setGrade(grade);
                profileManager.setDisplayedView(profileToEdit);
                profileToEdit.updateCap();
                showCommand = true;

            }

            if (oldSemester != 0 && editSemester != 0) {
                try {
                    hashMap.get(oldSemester).removeModuleWithModuleCode(moduleCode);
                } catch (ModuleNotFoundException e) { // Will not happen
                    throw new CommandException("Error deleting existing module.");
                }

                profileToEdit.addModule(editSemester, existingModule);
                updateStatus(profileToEdit);
                profileManager.setDisplayedView(profileToEdit);

                profileManager.clearDeadlineList();
                profileManager.setNewDeadlineList(profileToEdit);
                showCommand = true;
            }

            Deadline oldDeadline = null;
            Deadline newDeadline = null;
            if (newTask != null) {
                try {
                    newDeadline = existingModule.getDeadlineList().getTask(oldTask);
                    oldDeadline = newDeadline;
                    newDeadline.setDescription(newTask);
                    profileManager.replaceDeadline(oldDeadline, newDeadline);
                    oldTask = newTask;
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_DEADLINE_DOES_NOT_EXIST);
                }
            }
            if (newDeadlineString != null) {
                try {
                    newDeadline = existingModule.getDeadlineList().getTask(oldTask);
                    oldDeadline = newDeadline;
                    String date = newDeadlineString.split(" ")[0];
                    String time = newDeadlineString.split(" ")[1];
                    newDeadline.setDateTime(date, time);
                    newDeadline.addTag();
                    profileManager.replaceDeadline(oldDeadline, newDeadline);
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_DEADLINE_DOES_NOT_EXIST);
                }
            }

            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCode), showCommand);

        } else if (toEditProfile) {

            if (profileName != null) {
                profileToEdit.setName(profileName);
            }
            if (courseName != null) {
                profileToEdit.setCourse(courseName);

                // If focusArea is not edited, make sure old focusArea is valid for the new course
                if (focusAreaString == null) {
                    FocusArea oldFocusArea = profileToEdit.getFocusArea();
                    if (!oldFocusArea.isValid(courseName, oldFocusArea.toString())) {
                        focusAreaString = "UNDECIDED";
                    }
                }

            }

            if (focusAreaString != null) {
                CourseName courseName = profileToEdit.getCourseName();
                try {
                    focusArea = ParserUtil.parseFocusArea(courseName, focusAreaString);
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_INVALID_COURSE_FOCUS_AREA);
                }
                profileToEdit.setFocusArea(focusArea);
            }

            if (updatedSemester != 0) {
                profileToEdit.setCurrentSemester(updatedSemester);
                updateStatus(profileToEdit);
                profileManager.clearDeadlineList();
                profileManager.setNewDeadlineList(profileToEdit);
            }

            Profile editedPerson = createEditedPerson(profileToEdit);

            profileManager.setProfile(profileToEdit, editedPerson);
            profileManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_EDIT_PROFILE_SUCCESS, editedPerson.getName()), false);
        } else {
            throw new CommandException("Error: Edit Command cannot be executed");
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Profile createEditedPerson(Profile person) {
        assert person != null;
        return person;
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

    /**
     * Updates statuses of all modules in the Profile
     */
    private void updateStatus(Profile profileToEdit) {
        int currentSemester = profileToEdit.getOverallSemester();
        HashMap<Integer, ModuleList> hashMap = profileToEdit.getSemModHashMap();
        for (ModuleList list: hashMap.values()) {
            int semester = getKey(hashMap, list);
            for (Module moduleItr: list) {
                if (semester < currentSemester) {
                    moduleItr.getPersonal().setStatus("completed");
                } else if (semester == currentSemester) {
                    moduleItr.getPersonal().setStatus("in progress");
                } else {
                    moduleItr.getPersonal().setStatus("not taken");
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        boolean isSameProfile = false;
        boolean isSameModule = false;
        if (toEditProfile == true) {
            if (isProfileNameEqual(other)
                    && isCourseNameEqual(other)
                    && (updatedSemester == ((EditCommand) other).updatedSemester)
                    && isFocusAreaEqual(other)) {
                isSameProfile = true;
            }
        } else if (editModule == true) {
            if (moduleCode.equals(((EditCommand) other).moduleCode)
                    && (editSemester == ((EditCommand) other).editSemester)
                    && isGradeEqual(other)
                    && isOldTaskEqual(other)
                    && isNewTaskEqual(other)
                    && isNewDeadLineEqual(other)) {
                isSameModule = true;
            }
        }
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && (isSameProfile || isSameModule));
    }

    /**
     * Returns true if profileName is the same, including null cases
     */
    public boolean isProfileNameEqual(Object other) {
        boolean isEqual = false;
        if ((profileName == null) && (((EditCommand) other).profileName == null)) {
            isEqual = true;
        } else if (profileName.equals(((EditCommand) other).profileName)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if courseName is the same, including null cases
     */
    public boolean isCourseNameEqual(Object other) {
        boolean isEqual = false;
        if ((courseName == null) && (((EditCommand) other).courseName == null)) {
            isEqual = true;
        } else if (courseName.equals(((EditCommand) other).courseName)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if focus area is the same, including null cases
     */
    public boolean isFocusAreaEqual(Object other) {
        boolean isEqual = false;
        if ((focusAreaString == null) && (((EditCommand) other).focusAreaString == null)) {
            isEqual = true;
        } else if (focusAreaString.equals(((EditCommand) other).focusAreaString)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if grade is the same, including null cases
     */
    public boolean isGradeEqual(Object other) {
        boolean isEqual = false;
        if ((grade == null) && (((EditCommand) other).grade == null)) {
            isEqual = true;
        } else if (grade.equals(((EditCommand) other).grade)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if old task is the same, including null cases
     */
    public boolean isOldTaskEqual(Object other) {
        boolean isEqual = false;
        if ((oldTask == null) && (((EditCommand) other).oldTask == null)) {
            isEqual = true;
        } else if (oldTask.equals(((EditCommand) other).oldTask)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if new task is the same, including null cases
     */
    public boolean isNewTaskEqual(Object other) {
        boolean isEqual = false;
        if ((newTask == null) && (((EditCommand) other).newTask == null)) {
            isEqual = true;
        } else if (newTask.equals(((EditCommand) other).newTask)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns true if new deadline is the same, including null cases
     */
    public boolean isNewDeadLineEqual(Object other) {
        boolean isEqual = false;
        if ((newDeadlineString == null) && (((EditCommand) other).newDeadlineString == null)) {
            isEqual = true;
        } else if (newDeadlineString.equals(((EditCommand) other).newDeadlineString)) {
            isEqual = true;
        }
        return isEqual;
    }
}
