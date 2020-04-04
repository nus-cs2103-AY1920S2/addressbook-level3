package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPEC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleList;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;
import seedu.address.model.profile.course.module.personal.ModuleGrade;

/**
 * Edits Profile or Module specified by user.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits Profile or Module specified by user.\n"
            + "Parameters to edit Profile: "
            + PREFIX_NAME + "NAME "
            + "(" + PREFIX_COURSE_NAME + "COURSE) "
            + "(" + PREFIX_CURRENT_SEMESTER + "CURRENT_SEMESTER) "
            + "(" + PREFIX_SPEC + "SPECIALISATION) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + "(" + PREFIX_COURSE_NAME + "Computer Science) "
            + "(" + PREFIX_CURRENT_SEMESTER + "4) "
            + "(" + PREFIX_SPEC + "Algorithms & Theory) "
            + "Parameters to edit Module: "
            + PREFIX_MODULE + "MODULE "
            + "(" + PREFIX_SEMESTER + "SEMESTER) "
            + "(" + PREFIX_GRADE + "GRADE) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + "(" + PREFIX_SEMESTER + "4) "
            + "(" + PREFIX_GRADE + "A+) ";

    public static final String MESSAGE_EDIT_PROFILE_SUCCESS = "Edited Profile: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";

    private boolean toEditProfile = false;
    private Name profileName = null;
    private CourseName courseName = null;
    private int updatedSemester = 0;
    private String specialisation = null;

    private boolean editModule = false;
    private ModuleCode moduleCode;
    private int editSemester = 0;
    private String grade = null;
    private String oldTask = null;
    private String newTask = null;
    private String newDeadlineString = null;
    private int inSemester = 0;

    public EditCommand(Name name, CourseName courseName, int updatedSemester, String specialisation) {
        toEditProfile = true;
        this.profileName = name;
        this.courseName = courseName;
        this.updatedSemester = updatedSemester;
        this.specialisation = specialisation;
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
            throw new CommandException("Error: There is no existing profile.");
        }


        if (editModule) {

            HashMap<Integer, ModuleList> hashMap = profileToEdit.getSemModHashMap();
            if (hashMap.isEmpty()) { // No modules have been added to any semester at all
                throw new CommandException("No module data has been added to any semesters.");
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
                throw new CommandException("This module has not been added before.");
            }

            if (grade != null) {
                int currentUserSemester = profileToEdit.getCurrentSemester();
                if (oldSemester > currentUserSemester) {
                    throw new CommandException("You cannot add a grade to future semesters!");
                }
                if (!Grade.isValidGrade(grade)) {
                    throw new CommandException("Error: Invalid Grade.");
                }
                existingModule.getPersonal().setGrade(grade);
                profileManager.setDisplayedView(profileToEdit);
                profileToEdit.updateCap();
                return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCode), true);

            }

            if (oldSemester != 0 && editSemester != 0) {
                try {
                    hashMap.get(oldSemester).removeModuleWithModuleCode(moduleCode);
                } catch (ParseException e) {
                    throw new CommandException("Error deleting existing module.");
                }

                profileToEdit.addModule(editSemester, existingModule);
                updateStatus(profileToEdit);
            }

            //TODO: edit task and deadlines
            Deadline oldDeadline = null;
            Deadline newDeadline = null;
            if (newTask != null) {
                try {
                    newDeadline = existingModule.getDeadlineList().getTask(oldTask);
                    oldDeadline = newDeadline;
                    newDeadline.setDescription(newTask);
                    profileManager.replaceDeadline(oldDeadline, newDeadline);
                } catch (Exception e) {
                    throw new CommandException("Error: Deadline does not exist");
                }
            }
            if (newDeadlineString != null) {
                newDeadline = existingModule.getDeadlineList().getTask(oldTask);
                oldDeadline = newDeadline;
                String date = newDeadlineString.split(" ")[0];
                String time = newDeadlineString.split(" ")[1];
                try {
                    newDeadline.setDateTime(date, time);
                    newDeadline.addTag();
                    profileManager.replaceDeadline(oldDeadline, newDeadline);
                } catch (DateTimeException e) {
                    throw new CommandException("Invalid date or time!");
                }
            }

            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCode), false);

        } else if (toEditProfile) {

            if (profileName != null) {
                profileToEdit.setName(profileName);
            }
            if (courseName != null) {
                profileToEdit.setCourse(courseName);
            }
            if (updatedSemester != 0) {
                profileToEdit.setCurrentSemester(updatedSemester);
                updateStatus(profileToEdit);
                profileManager.deleteDeadlineList();
            }
            if (specialisation != null) {
                profileToEdit.setSpecialisation(specialisation);
            }

            Profile editedPerson = createEditedPerson(profileToEdit);

            profileManager.setPerson(profileToEdit, editedPerson);
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
        int currentSemester = profileToEdit.getCurrentSemester();
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
}
