package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_DEADLINE_FAILURE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_GRADE_FAILURE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PROFILE_FAILURE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_NOT_TAKING_MODULE;
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

    // Invalid module code, user inputs "delete m/123abc"
    // Check in the DeleteCommandParser

    /*@Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1111");
        DeleteCommand deleteCommandModule = new DeleteCommand(Collections.singletonList(moduleCode));

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE, () ->
                deleteCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }*/


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

        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_DEADLINE_FAILURE, tasks.toString()), () ->
                deleteCommandTask.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStubCs()));
    }


    // No grade to be deleted
    @Test
    public void execute_gradeNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        String grade = VALID_GRADE_AMY;
        DeleteCommand deleteCommandGrade = new DeleteCommand(Collections.singletonList(moduleCode), grade);

        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, Arrays.asList(moduleCode)), () ->
                        deleteCommandGrade.execute(new ProfileManagerWithNonEmptyProfile(),
                                new CourseManagerStub(), new ModuleManagerStubCs()));
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
            Module module = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            Deadline deadline = new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY);
            String grade = VALID_GRADE_AMY;
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("JOHN"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profile.addModule(1, module);
            Personal personal = new Personal();
            personal.addDeadline(deadline);
            personal.setGrade(grade);
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
            Module moduleCs = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(moduleCs);
            moduleList.add(moduleBob);
            moduleList.add(module);
        }
    }
}

