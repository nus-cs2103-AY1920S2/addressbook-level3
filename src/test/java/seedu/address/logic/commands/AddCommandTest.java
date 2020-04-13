package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_ADD_SUCCESS;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DEADLINE_INVALID_SEMESTER;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_MODULE;
import static seedu.address.logic.commands.AddCommand.MESSAGE_EDIT_SUCCESS;
import static seedu.address.logic.commands.AddCommand.MESSAGE_MODULE_NOT_ADDED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;
import seedu.address.model.profile.course.AcceptedCourses;
import seedu.address.model.profile.course.AcceptedFocusArea;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.course.module.Description;
import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.Preclusions;
import seedu.address.model.profile.course.module.PrereqTreeNode;
import seedu.address.model.profile.course.module.Prereqs;
import seedu.address.model.profile.course.module.SemesterData;
import seedu.address.model.profile.course.module.Title;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Personal;
import seedu.address.model.profile.exceptions.MaxModsException;

//@@author wanxuanong

public class AddCommandTest {

    // No profile exists, hence modules cannot be added
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommand = new AddCommand(Collections.singletonList(moduleCode), semester, null, deadlines);
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                addCommand.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // No module code added, user inputs "add m/"
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(
                Collections.singletonList(new ModuleCode(null)), 0, null, null));
    }

    // No semester added, user inputs "add m/CS1231 y/"
    @Test
    public void constructor_nullSemester_throwsNullPointerException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(NullPointerException.class, () -> new AddCommand(
                Collections.singletonList(moduleCode), new Year(null).getSemester(), null, null));
    }

    // Invalid module, valid semester, user inputs "add m/123ABC y/2.1"
    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(INVALID_MODCODE_DESC);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModule = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, Arrays.asList(moduleCode)), () ->
                addCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }


    // Module has been added before (to a future semester)
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.2").getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModule = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, "CS1101S"), () ->
                addCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Multiple modules added, some duplicate
    @Test
    public void execute_multipleDuplicateModules_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_AMY));
        moduleCodes.add(new ModuleCode("CS1101S"));
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        List<ModuleCode> duplicate = new ArrayList<>();
        duplicate.add(new ModuleCode("CS1101S"));
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, duplicate), () ->
                addCommandModules.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));

    }

    // Valid module code, different capitalizations
    @Test
    public void execute_validModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandAllCap = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        AddCommand addCommandVariety = new AddCommand(
                Collections.singletonList(new ModuleCode("Cs1231")), semester, null, deadlines);
        AddCommand addCommandNoCap = new AddCommand(
                Collections.singletonList(new ModuleCode("cs1231")), semester, null, deadlines);

        try {
            assertEquals(addCommandAllCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandVariety.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandNoCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    // Multiple modules added, some invalid
    @Test
    public void execute_multipleInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_AMY));
        moduleCodes.add(new ModuleCode("CS1111"));
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        List<ModuleCode> invalidCodes = new ArrayList<>();
        invalidCodes.add(new ModuleCode("CS1111"));
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidCodes), () ->
                addCommandModules.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));

    }

    // Multiple modules added, all invalid
    @Test
    public void execute_multipleAllInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode("IS4000"));
        moduleCodes.add(new ModuleCode("CS1111"));
        moduleCodes.add(new ModuleCode("MA2000"));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                addCommandModules.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));

    }

    // Multiple modules added, all valid
    @Test
    public void execute_multipleValidModules_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_AMY));
        moduleCodes.add(new ModuleCode("GER1000"));
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        try {
            assertEquals(addCommandModules.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }

    }

    // Valid grade, user inputs "add m/MA1521, y/1.1, g/C+"
    @Test
    public void execute_validGrade_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        String grade = VALID_GRADE_BOB;
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandGrade = new AddCommand(
                Collections.singletonList(moduleCode), semester, grade, deadlines);
        try {
            assertEquals(addCommandGrade.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    // Adding task to a past semester
    @Test
    public void execute_addTaskToNonCurrentSemester_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("IS1103");
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        assertThrows(CommandException.class, MESSAGE_DEADLINE_INVALID_SEMESTER, () ->
                addCommandTask.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Adding task to a non existing module
    @Test
    public void execute_addTaskToNonExistingModule_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year("1.1").getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        assertThrows(CommandException.class, MESSAGE_MODULE_NOT_ADDED, () ->
                addCommandTask.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Adding multiple tasks, valid
    @Test
    public void execute_addMultipleTasks_success() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.1").getSemester();
        String taskA = VALID_TASK_AMY;
        LocalDate dateA = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime timeA = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        String taskB = VALID_TASK_BOB;
        LocalDate dateB = LocalDate.parse(VALID_DEADLINE_DATE_BOB);
        LocalTime timeB = LocalTime.parse(VALID_DEADLINE_TIME_BOB);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, taskA, dateA, timeA));
        deadlines.add(new Deadline(VALID_MODCODE_AMY, taskB, dateB, timeB));
        AddCommand addCommandTasks = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        String updateMessage = String.format(MESSAGE_EDIT_SUCCESS, moduleCode);
        String addSuccess = String.format("These task(s) have been added: %1$s; %2$s; ", taskA, taskB);
        updateMessage += "\n" + addSuccess;

        try {
            assertEquals(addCommandTasks.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    // Duplicate tasks added
    @Test
    public void execute_duplicateTask_error() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.1").getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline("CS1101S", task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        String updateMessage = String.format(MESSAGE_EDIT_SUCCESS, moduleCode);
        String addError = String.format("Failed to add these duplicate task(s): %1$s; ", task);
        updateMessage += "\n" + addError;

        try {
            assertEquals(addCommandTask.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void equals() {
        LocalDate modelDate = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime modelTime = LocalTime.parse(VALID_DEADLINE_TIME_AMY, DateTimeFormatter.ofPattern("HH:mm"));

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY, modelDate, modelTime));
        deadlines.add(new Deadline(VALID_MODCODE_BOB, VALID_TASK_BOB, modelDate, modelTime));

        AddCommand addAmyCommand = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_AMY)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        AddCommand addBobCommand = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_BOB)),
                new Year(VALID_SEMESTER_BOB).getSemester(), VALID_GRADE_BOB, deadlines);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addAmyCommandCopy = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_AMY)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        assertTrue(addAmyCommand.equals(addAmyCommandCopy));

        // same module code but remainder fields differ -> returns true
        AddCommand addBobCommandDiff = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_BOB)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        assertTrue(addBobCommand.equals(addBobCommandDiff));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBobCommand));
    }


    private class ProfileManagerStub extends ProfileManager {
        protected ObservableList<Profile> profileList = FXCollections.observableArrayList();
        protected FilteredList<Profile> filteredProfiles;
        protected ObservableList<Deadline> deadlineList;

        private ProfileManagerStub(List<Profile> profileList) {
            requireNonNull(profileList);
            this.profileList.setAll(profileList);
            filteredProfiles = new FilteredList<>(this.profileList);
        }

        public ProfileManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public Profile getFirstProfile() {
            return this.profileList.get(0);
        }

        @Override
        public ObservableList<Profile> getFilteredPersonList() {
            return filteredProfiles;
        }

        public void setPerson(Profile target, Profile editedProfile) {
            requireAllNonNull(target, editedProfile);
            int index = this.profileList.indexOf(target);
            this.profileList.set(index, editedProfile);
        }
    }

    private class ProfileManagerWithNonEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithNonEmptyProfile() {
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Module moduleIs = new Module(new ModuleCode("IS1103"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());

            Deadline deadline = new Deadline("CS1101S",
                    "homework", LocalDate.parse("2020-04-25"), LocalTime.parse("23:59"));
            ArrayList<Deadline> deadlines = new ArrayList<>();
            deadlines.add(deadline);

            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));

            try {
                profile.addModule(1, module);
                profile.addModule(3, moduleIs);
            } catch (MaxModsException e) {
                fail();
            }

            Personal personal = new Personal();
            module.setPersonal(personal);
            personal.addDeadline(deadline);

            profileList.add(profile);
            this.profileList = profileList;
            filteredProfiles = new FilteredList<>(this.profileList);
        }
        @Override
        public boolean hasOneProfile() {
            return true;
        }
    }


    private class ProfileManagerWithEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithEmptyProfile() {
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profileList.add(profile);
            this.profileList = profileList;
            filteredProfiles = new FilteredList<>(this.profileList);
        }

        @Override
        public boolean hasOneProfile() {
            return true;
        }
    }

    private class CourseManagerStub extends CourseManager {
        protected List<Course> courseList;

        private CourseManagerStub(List<Course> courseList) {
            requireNonNull(courseList);
            this.courseList = courseList;
        }

        public CourseManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public Course getCourse(CourseName courseName) throws ParseException {
            for (Course course : courseList) {
                if (course.getCourseName().equals(courseName)) {
                    return course;
                }
            }
            throw new ParseException("Course does not exist");
        }
    }

    private class ModuleManagerStub extends ModuleManager {
        protected List<Module> moduleList;

        private ModuleManagerStub(List<Module> moduleList) {
            requireNonNull(moduleList);
            this.moduleList = moduleList;
        }

        public ModuleManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            for (Module module: moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Module getModule(ModuleCode moduleCode) {
            requireNonNull(moduleCode);

            for (Module mod: moduleList) {
                if (mod.getModuleCode().equals(moduleCode)) {
                    return mod;
                }
            }
            // Code should not reach this line
            assert false;
            return null;
        }
    }


    private class ModuleManagerStubCs extends ModuleManagerStub {
        private ModuleManagerStubCs() {
            Module moduleAmy = new Module(new ModuleCode(VALID_MODCODE_AMY), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), null);
            Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), null);
            Module moduleCs = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Module moduleIs = new Module(new ModuleCode("IS1103"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    null);
            Module moduleGe = new Module(new ModuleCode("GER1000"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    null);
            moduleList.add(moduleAmy);
            moduleList.add(moduleBob);
            moduleList.add(moduleCs);
            moduleList.add(moduleIs);
            moduleList.add(moduleGe);
        }
    }
}
