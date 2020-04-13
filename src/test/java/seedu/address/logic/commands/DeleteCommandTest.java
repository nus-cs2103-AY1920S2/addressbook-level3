package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import static seedu.address.logic.commands.AddCommand.MESSAGE_EDIT_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.DeleteCommand.*;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
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

//@@author wanxuanong

public class DeleteCommandTest {

    // No profile exists, hence profile cannot deleted
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        DeleteCommand deleteCommandName = new DeleteCommand(name);

        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, name), () ->
                deleteCommandName.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // No name, user inputs "delete n/"
    // ParserException thrown in DeleteCommandParser
    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(new Name(null)));
    }

    // Invalid name, e.g. show n/Mark
    @Test
    public void execute_invalidName_throwsCommandException() {
        DeleteCommand invalidName = new DeleteCommand(new Name("Mark"));

        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, "mark"), () ->
                invalidName.execute(new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));
    }


    // No module code added, user inputs "delete m/"
    // ParserException thrown in DeleteCommandParser
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DeleteCommand(Collections.singletonList(new ModuleCode(null))));
    }

    // Module has not been added to profile before

    @Test
    public void execute_moduleNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        DeleteCommand deleteCommand = new DeleteCommand(Collections.singletonList(moduleCode));

        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, Arrays.asList(moduleCode)), () ->
                deleteCommand.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStubCs()));
    }


    // Valid module code, different capitalizations
    @Test
    public void execute_validModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        DeleteCommand deleteCommandAllCap = new DeleteCommand(Collections.singletonList(moduleCode));
        DeleteCommand deleteCommandVariety = new DeleteCommand(Collections.singletonList(new ModuleCode("Cs1231")));
        DeleteCommand deleteCommandNoCap = new DeleteCommand(Collections.singletonList(new ModuleCode("cs1231")));

        try {
            assertTrue(deleteCommandAllCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(
                    String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandVariety.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(
                    String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandNoCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(
                            String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
        } catch (CommandException e) {
            fail();
        }
    }

    // All invalid module codes
    @Test
    public void execute_allInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeCs = new ModuleCode("CS1111");
        ModuleCode moduleCodeMa = new ModuleCode("MA2000");
        moduleCodes.add(moduleCodeCs);
        moduleCodes.add(moduleCodeMa);
        DeleteCommand deleteCommandModule = new DeleteCommand(moduleCodes);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                deleteCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Some invalid module codes
    @Test
    public void execute_someInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        List<ModuleCode> invalidModuleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeCs = new ModuleCode("CS1111");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeCs);
        invalidModuleCodes.add(moduleCodeCs);
        DeleteCommand deleteCommandModule = new DeleteCommand(moduleCodes);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidModuleCodes), () ->
                deleteCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Multiple modules, some modules not taking
    @Test
    public void execute_someModulesNotAdded_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeB = new ModuleCode(VALID_MODCODE_BOB);
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeB);
        List<ModuleCode> modulesNotTaking = new ArrayList<>();
        modulesNotTaking.add(moduleCodeB);
        DeleteCommand deleteCommandModules = new DeleteCommand(moduleCodes);

        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, modulesNotTaking), () ->
                deleteCommandModules.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));

    }

    // Multiple modules deleted
    @Test
    public void execute_multipleModules_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeB = new ModuleCode("CS1101S");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeB);
        DeleteCommand deleteCommandModules = new DeleteCommand(moduleCodes);

        try {
            assertEquals(deleteCommandModules.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }

    }


    // User inputs "delete t/homework" without module tag
    @Test
    public void constructor_noModuleCodeForDeleteTask_throwsNullPointerException() {
        String moduleCode = VALID_MODCODE_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        ArrayList<Deadline> tasks = new ArrayList<>();
        tasks.add(new Deadline(moduleCode, VALID_TASK_AMY, date, time));

        assertThrows(NullPointerException.class, () -> new DeleteCommand(null, tasks));
    }

    // Task to be deleted does not exist

    @Test
    public void execute_taskDoesNotExist_throwsCommandException() {
        String moduleCode = VALID_MODCODE_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        ArrayList<Deadline> tasks = new ArrayList<>();
        tasks.add(new Deadline(moduleCode, VALID_TASK_AMY, date, time));
        DeleteCommand deleteCommandTask =
                new DeleteCommand(Collections.singletonList(new ModuleCode(moduleCode)), tasks);

        String updateMessage = String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, moduleCode);
        String deleteError = String.format("Failed to delete these task(s) as they were not added: %1$s; ", tasks);
        updateMessage += "\n" + deleteError;

        try {
            assertEquals(deleteCommandTask.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                            new ModuleManagerStubCs()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    // Multiple tasks deleted
    @Test
    public void execute_allTasks_success() {
        String moduleCode = VALID_MODCODE_AMY;
        ArrayList<Deadline> tasks = new ArrayList<>();
        tasks.add(new Deadline(moduleCode, VALID_TASK_AMY));
        tasks.add(new Deadline(moduleCode, VALID_TASK_BOB));
        DeleteCommand deleteCommandTasks =
                new DeleteCommand(Collections.singletonList(new ModuleCode(moduleCode)), tasks);

        try {
            assertEquals(deleteCommandTasks.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, tasks.toString()));
        } catch (CommandException e) {
            fail();
        }

    }


    // No grade to be deleted
    @Test
    public void execute_gradeNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        String grade = VALID_GRADE_AMY;
        DeleteCommand deleteCommandGrade = new DeleteCommand(Collections.singletonList(moduleCode), grade);

        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, Arrays.asList(moduleCode)), () ->
                        deleteCommandGrade.execute(new ProfileManagerWithNonEmptyProfile(),
                                new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Multiple modules, some missing grades
    @Test
    public void execute_someGradesNotAdded_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        List<ModuleCode> modulesNoGrades = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        String grade = "";
        ModuleCode  moduleCodeCs = new ModuleCode("CS1101S");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeCs);
        modulesNoGrades.add(moduleCodeCs);
        DeleteCommand deleteCommandGrades = new DeleteCommand(moduleCodes, grade);

        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, modulesNoGrades), () ->
                        deleteCommandGrades.execute(new ProfileManagerWithNonEmptyProfile(),
                                new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    // Multiple modules' grades deleted
    @Test
    public void execute_allGrades_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeIs = new ModuleCode("IS1103");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeIs);
        String grade = "";
        DeleteCommand deleteCommandGrades = new DeleteCommand(moduleCodes, grade);

        try {
            assertEquals(deleteCommandGrades.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_GRADE_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }
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
            Module moduleA = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Module moduleCs = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Module moduleIs = new Module(new ModuleCode("IS1103"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());

            Deadline deadlineA = new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY);
            Deadline deadlineB = new Deadline(VALID_MODCODE_AMY, VALID_TASK_BOB);
            ArrayList<Deadline> deadlines = new ArrayList<>();
            deadlines.add(deadlineA);
            deadlines.add(deadlineB);

            String gradeA = VALID_GRADE_AMY;
            String gradeIs = VALID_GRADE_BOB;

            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("JOHN"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profile.addModule(1, moduleA);
            profile.addModule(1, moduleCs);
            profile.addModule(1, moduleIs);

            Personal personalA = new Personal();
            moduleA.setPersonal(personalA);
            personalA.addDeadline(deadlineA);
            personalA.addDeadline(deadlineB);
            personalA.setGrade(gradeA);

            Personal personalIs = new Personal();
            moduleIs.setPersonal(personalIs);
            personalIs.setGrade(gradeIs);

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
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            Module moduleCs = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Module moduleIs = new Module(new ModuleCode("IS1103"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(moduleAmy);
            moduleList.add(moduleBob);
            moduleList.add(moduleCs);
            moduleList.add(moduleIs);
        }
    }
}

