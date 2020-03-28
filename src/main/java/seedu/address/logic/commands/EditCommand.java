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
import seedu.address.model.Model;
import seedu.address.model.ModuleList;
import seedu.address.model.ModuleManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;

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
            + "(" + PREFIX_SPEC + "Theory and ALgorithm) "
            + "Parameters to edit Module: "
            + PREFIX_MODULE + "MODULE "
            + "(" + PREFIX_SEMESTER + "SEMESTER) "
            + "(" + PREFIX_GRADE + "GRADE) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + "(" + PREFIX_SEMESTER + "4) "
            + "(" + PREFIX_GRADE + "A+) ";

    public static final String MESSAGE_EDIT_PROFILE_SUCCESS = "Edited Profile: " + Profile.getStaticName();
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";

    private boolean toEditProfile = false;
    private Name name = null;
    private CourseName courseName = null;
    private int currentSemester = 0;
    private String specialisation = null;

    private Module toEditModule = null;
    private ModuleCode moduleCode;
    private int intSemester = 0;
    private String grade = null;
    private String oldTask = null;
    private String newTask = null;
    private String newDeadline = null;
    private int inSemester = 0;

    public EditCommand(Name name, CourseName courseName, int currentSemester, String specialisation) {
        toEditProfile = true;
        this.name = name;
        this.courseName = courseName;
        this.currentSemester = currentSemester;
        this.specialisation = specialisation;
    }

    public EditCommand(ModuleCode moduleCode, int intSemester, String grade, String oldTask, String newTask
            , String newDeadline) throws ParseException {
        this.moduleCode = moduleCode;
        this.intSemester = intSemester;
        this.grade = grade;
        this.oldTask = oldTask;
        this.newTask = newTask;
        this.newDeadline = newDeadline;
        Module existingModule = null;
        ModuleList inList = null;
        Module module = ModuleManager.getModule(moduleCode);
        HashMap<Integer, ModuleList> hashMap = Profile.getHashMap();
        if (hashMap.isEmpty()) {
            System.out.println("hashmap is empty");
            throw new ParseException(String.format("Error: Module does not exist", EditCommand.MESSAGE_USAGE));
        }
        for (ModuleList list: hashMap.values()) {
            for (Module moduleItr: list) {
                if (module.isSameModule(moduleItr)) {
                    existingModule = moduleItr;
                    inList = list;
                }
            }
        }
        if (existingModule == null) {
            System.out.println("existing module is null");
            throw new ParseException(String.format("Error: Module does not exist", EditCommand.MESSAGE_USAGE));
        }

        this.inSemester = getKey(hashMap, inList);
        this.toEditModule = existingModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Profile> lastShownList = model.getFilteredPersonList();
        Profile profileToEdit;
        try {
            profileToEdit = lastShownList.get(0); //accessing only first profile in list
        } catch (Exception e) {
            throw new CommandException("Error: There is no existing profile.");
        }

        if (toEditModule != null) {
            if (intSemester != 0) {
                HashMap<Integer, ModuleList> hashMap = profileToEdit.getHashMap();
                try {
                    hashMap.get(inSemester).removeModuleWithModuleCode(moduleCode);
                } catch (ParseException e) {
                    throw new CommandException("Error deleting exiting module.");
                }
                profileToEdit.addModule(intSemester, toEditModule);
                updateStatus(profileToEdit, profileToEdit.getCurrentSemester());
            }
            if (grade != null) {
                toEditModule.getPersonal().setGrade(grade);
            }
            //TODO: edit task and deadlines
            if (newTask != null) {
                Deadline deadline = toEditModule.getDeadlineList().getTask(oldTask);
                deadline.setDescription(newTask);
            }
            if (newDeadline != null) {
                Deadline deadline = toEditModule.getDeadlineList().getTask(oldTask);
                String date = newDeadline.split(" ")[0];
                String time = newDeadline.split(" ")[1];
                try {
                    deadline.setDateTime(date, time);
                } catch (DateTimeException e) {
                    throw new CommandException("Invalid date or time!");
                }
            }
            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, toEditModule));
        } else if (toEditProfile) {
            if (name != null) {
                profileToEdit.setName(name);
            }
            if (courseName != null) {
                profileToEdit.setCourse(courseName);
            }
            if (currentSemester != 0) {
                profileToEdit.setCurrentSemester(currentSemester);
                updateStatus(profileToEdit, currentSemester);
            }
            if (specialisation != null) {
                profileToEdit.setSpecialisation(specialisation);
            }

            Profile editedPerson = createEditedPerson(profileToEdit);

            model.setPerson(profileToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_EDIT_PROFILE_SUCCESS, toEditProfile));
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
    private void updateStatus(Profile profileToEdit, int currentSemester) {
        HashMap<Integer, ModuleList> hashMap = profileToEdit.getHashMap();
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
