package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_COURSE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_FOCUS_AREA;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_MODULE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_MODULE_LIST;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
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
import seedu.address.model.profile.course.CourseFocusArea;
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
import seedu.address.model.profile.exceptions.MaxModsException;

//@@author chanckben
public class ShowCommandTest {

    // No profile has been added, user inputs "show n/John" and "show n/123"
    @Test
    public void execute_nameNoProfile_throwsCommandException() {
        ShowCommand showCommandJohn = new ShowCommand(new Name("John"));
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommandJohn.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
        ShowCommand showCommand123 = new ShowCommand(new Name("123"));
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommand123.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // No profile has been added, user inputs "show n/"
    // Note that the CommandException would have been thrown in ShowCommandParser
    @Test
    public void constructor_nullNameNoProfile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowCommand(new Name(null)));
    }

    // Valid name, same capitalisation, e.g. show n/John
    // Valid name, all capitalised, e.g. show n/JOHN
    // Valid name, all non-caps, e.g. show n/john
    @Test
    public void execute_validNameOneProfile_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand(new Name("John"));
        ShowCommand showCommandAllCap = new ShowCommand(new Name("JOHN"));
        ShowCommand showCommandNoCap = new ShowCommand(new Name("john"));

        try {
            assertTrue(showCommandFirstLetterCap.execute(
                    new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandAllCap.execute(
                    new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandNoCap.execute(
                    new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid name, e.g. show n/Mark
    @Test
    public void execute_invalidNameOneProfile_throwsCommandException() {
        ShowCommand invalidName = new ShowCommand(new Name("Mark"));

        assertThrows(CommandException.class, "Profile with name does not exist.", () ->
                invalidName.execute(new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));
    }

    // No profile has been added, user inputs "show y/1.1"
    // One profile has been added but with no modules, user inputs "show y/1.1"
    @Test
    public void execute_semesterNoModules_throwsCommandException() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.1"));
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                showCommandSem.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
        assertThrows(CommandException.class, MESSAGE_INVALID_SEMESTER, () ->
                showCommandSem.execute(
                        new ProfileManagerWithEmptyProfile(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // One profile has been added with a module. Valid semester, user inputs "show y/1.1"
    @Test
    public void execute_validSemesterWithModules_success() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.1"));
        try {
            assertTrue(showCommandSem.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_MODULE_LIST));
        } catch (CommandException e) {
            fail();
        }
    }

    // One profile has been added with a module. Invalid semester, user inputs "show y/1.2"
    @Test
    public void execute_invalidSemesterWithModules_throwsCommandException() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.2"));
        assertThrows(CommandException.class, MESSAGE_INVALID_SEMESTER, () ->
                showCommandSem.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));
    }

    // Valid course, first letter of each word capitalised, e.g. show c/Computer Science
    // Valid course, all words capitalised, e.g. show c/COMPUTER SCIENCE
    // Valid course, all words in non-caps, e.g. show c/computer science
    @Test
    public void execute_validCourseName_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand(new CourseName("Computer Science"));
        ShowCommand showCommandAllCap = new ShowCommand(new CourseName("COMPUTER SCIENCE"));
        ShowCommand showCommandNoCap = new ShowCommand(new CourseName("computer science"));

        try {
            assertTrue(showCommandFirstLetterCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandAllCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandNoCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_COURSE));
        } catch (CommandException e) {
            fail();
        }
    }

    // No profile has been added, user inputs "show c/"
    // Note that the CommandException would have been thrown in ShowCommandParser
    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowCommand(new CourseName(null)));
    }

    // Valid focus area, first letter of each word capitalised, e.g. show f/Computer Security
    // Valid focus area, all words capitalised, e.g. show c/COMPUTER SECURITY
    // Valid focus area, all words in non-caps, e.g. show c/computer security
    @Test
    public void execute_validFocusArea_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand("Computer Security");
        ShowCommand showCommandAllCap = new ShowCommand("COMPUTER SECURITY");
        ShowCommand showCommandNoCap = new ShowCommand("computer security");

        try {
            assertTrue(showCommandFirstLetterCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandAllCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandNoCap.execute(
                    new ProfileManagerStub(), new CourseManagerStubCs(),
                    new ModuleManagerStub()).getFeedbackToUser().equals(MESSAGE_SUCCESS_FOCUS_AREA));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid focus area, e.g. show f/focus area x
    // Integer focus area, e.g. show f/123
    @Test
    public void execute_invalidFocusArea_throwsCommandException() {
        ShowCommand invalidFocusArea = new ShowCommand("focus area x");
        ShowCommand integerFocusArea = new ShowCommand("123");

        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                invalidFocusArea.execute(new ProfileManagerStub(), new CourseManagerStubCs(), new ModuleManagerStub()));
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                integerFocusArea.execute(new ProfileManagerStub(), new CourseManagerStubCs(), new ModuleManagerStub()));
    }

    // Valid module code, all capitalised, e.g. CS1101S
    // Valid module code, some in caps, some non-caps, e.g. Cs1101s
    // Valid module code, all non-caps, e.g. cs1101s
    @Test
    public void execute_validModuleCode_success() {
        ShowCommand showCommandAllCap = new ShowCommand(new ModuleCode("CS1101S"));
        ShowCommand showCommandVariety = new ShowCommand(new ModuleCode("Cs1101s"));
        ShowCommand showCommandNoCap = new ShowCommand(new ModuleCode("cs1101s"));

        try {
            assertTrue(showCommandAllCap.execute(
                    new ProfileManagerStub(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandVariety.execute(
                    new ProfileManagerStub(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandNoCap.execute(
                    new ProfileManagerStub(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser().equals(MESSAGE_SUCCESS_MODULE));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid module code, e.g. show m/CS1111
    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ShowCommand invalidCommand = new ShowCommand(new ModuleCode("CS1111"));
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, "CS1111"), () ->
                invalidCommand.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    private class ModuleManagerStubCs extends ModuleManagerStub {
        private ModuleManagerStubCs() {
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(module);
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

    private class CourseManagerStubCs extends CourseManagerStub {
        private CourseManagerStubCs() {
            List<Course> courseList = new ArrayList<>();
            List<CourseFocusArea> courseFocusAreaList = new ArrayList<>();
            courseFocusAreaList.add(
                    new CourseFocusArea("Computer Security", new ArrayList<>(), new ArrayList<>()));
            courseList.add(
                    new Course(AcceptedCourses.COMPUTER_SCIENCE.getName(), new ArrayList<>(), courseFocusAreaList));
            this.courseList = courseList;
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
            for (Course course: courseList) {
                if (course.getCourseName().equals(courseName)) {
                    return course;
                }
            }
            throw new ParseException("Course does not exist");
        }

        @Override
        public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
            requireNonNull(focusAreaName);
            for (Course course: courseList) {
                try {
                    CourseFocusArea focusArea = course.getCourseFocusArea(focusAreaName);
                    return focusArea;
                } catch (ParseException e) {
                    continue;
                }
            }
            throw new ParseException(MESSAGE_INVALID_COURSE_FOCUS_AREA);
        }
    }

    private class ProfileManagerWithNonEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithNonEmptyProfile() {
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            try {
                profile.addModule(1, module);
            } catch (MaxModsException e) {
                fail();
            }
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

    private class ProfileManagerStub extends ProfileManager {
        protected ObservableList<Profile> profileList = FXCollections.observableArrayList();
        protected FilteredList<Profile> filteredProfiles;

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
    }

}
