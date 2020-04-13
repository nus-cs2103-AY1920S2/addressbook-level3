package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_DEADLINE_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_MODULE_DATA;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MODULE_NOT_ADDED;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOCUS_AREA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_TASK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_MODULE_SUCCESS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PROFILE_SUCCESS;
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

//@@author joycelynteo

public class EditCommandTest {


    // No profile exists, hence no profile or module to be edited
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        EditCommand editCommand = new EditCommand(name, null, 0, null);

        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                editCommand.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // No module data, hence there are no modules to edit
    @Test
    public void execute_noModuleData_throwsCommandException() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        EditCommand editCommand = new EditCommand(moduleCodeAmy, 0, VALID_GRADE_AMY, null,
                null, null);

        assertThrows(CommandException.class, MESSAGE_EMPTY_MODULE_DATA, () ->
                editCommand.execute(new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));
    }

    // Module to be edited has not been added before
    @Test
    public void execute_moduleNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS2105");
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        EditCommand editCommand = new EditCommand(moduleCode, semester, null, null, null,
                null);

        assertThrows(CommandException.class, MESSAGE_MODULE_NOT_ADDED, () ->
                editCommand.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStubCs()));
    }

    // Deadline to be edited does not exist
    @Test
    public void execute_deadlineDoesNotExist_throwsCommandException() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);

        // When editing task description
        EditCommand editTaskCommand = new EditCommand(moduleCodeAmy, 0, null, VALID_TASK_AMY,
                VALID_NEW_TASK_AMY, null);
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editTaskCommand.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStubCs()));

        // When editing deadline
        EditCommand editDeadlineCommand = new EditCommand(moduleCodeAmy, 0, null, VALID_TASK_AMY,
                null, VALID_DEADLINE_AMY);
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editDeadlineCommand.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStubCs()));
    }

    // Invalid Focus Area (based off course)
    @Test
    public void execute_invalidFocusArea_throwsCommandException() {
        // Invalid focus area
        EditCommand editCommandInvalid = new EditCommand(null, null, 0,
                INVALID_FOCUS_AREA_DESC);
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandInvalid.execute(new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));

        // Valid focus area, but not for the current course
        EditCommand editCommandValidForWrongCourse = new EditCommand(null, null, 0,
                "f/Financial Analytics");
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandValidForWrongCourse.execute(new ProfileManagerWithEmptyProfile(), new CourseManagerStub(),
                        new ModuleManagerStub()));
    }

    @Test
    public void execute_validProfileFields_success() {
        ProfileManagerWithEmptyProfile profileManagerWithEmptyProfile = new ProfileManagerWithEmptyProfile();

        try {
            // Valid name
            Name name = new Name(VALID_NAME_AMY);
            EditCommand editName = new EditCommand(name, null, 0, null);
            assertEquals(editName.execute(
                    profileManagerWithEmptyProfile, new CourseManagerStub(), new ModuleManagerStub())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS, name));

            // Valid course
            CourseName courseName = new CourseName(VALID_COURSE_AMY);
            EditCommand editCourse = new EditCommand(null, courseName, 0, null);
            assertEquals(editCourse.execute(
                    profileManagerWithEmptyProfile, new CourseManagerStub(), new ModuleManagerStub())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    profileManagerWithEmptyProfile.getFirstProfile().getName()));

            // Valid current semester
            int semester = new Year(VALID_SEMESTER_AMY).getSemester();
            EditCommand editSemester = new EditCommand(null, null, semester, null);
            assertEquals(editSemester.execute(
                    profileManagerWithEmptyProfile, new CourseManagerStub(), new ModuleManagerStub())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    profileManagerWithEmptyProfile.getFirstProfile().getName()));

            // Valid focus area
            EditCommand editFocusArea = new EditCommand(null, null, 0,
                    VALID_FOCUS_AREA_AMY);
            assertEquals(editFocusArea.execute(
                    profileManagerWithEmptyProfile, new CourseManagerStub(), new ModuleManagerStub())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    profileManagerWithEmptyProfile.getFirstProfile().getName()));
        } catch (CommandException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void execute_validModuleFields_success() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeBob = new ModuleCode(VALID_MODCODE_BOB);
        ProfileManagerWithNonEmptyProfile profileManagerWithNonEmptyProfile = new ProfileManagerWithNonEmptyProfile();

        try {
            // Valid semester
            int semester = new Year(VALID_SEMESTER_AMY).getSemester();
            EditCommand editSemester = new EditCommand(moduleCodeAmy, semester, null, null,
                    null, null);
            assertEquals(editSemester.execute(
                    profileManagerWithNonEmptyProfile, new CourseManagerStub(), new ModuleManagerStubCs())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));

            // Valid grade
            EditCommand editGrade = new EditCommand(moduleCodeBob, 0, VALID_GRADE_AMY, null,
                    null, null);
            assertEquals(editGrade.execute(
                    profileManagerWithNonEmptyProfile, new CourseManagerStub(), new ModuleManagerStubCs())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeBob));

            // Valid new task
            EditCommand editTask = new EditCommand(moduleCodeBob, 0, null, VALID_TASK_BOB,
                    VALID_NEW_TASK_BOB, null);
            assertEquals(editTask.execute(
                    profileManagerWithNonEmptyProfile, new CourseManagerStub(), new ModuleManagerStubCs())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeBob));

            // Valid new deadline
            EditCommand editDeadline = new EditCommand(moduleCodeBob, 0, null, VALID_NEW_TASK_BOB,
                    null, VALID_DEADLINE_BOB);
            assertEquals(editDeadline.execute(
                    profileManagerWithNonEmptyProfile, new CourseManagerStub(), new ModuleManagerStubCs())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeBob));
        } catch (CommandException e) {
            fail();
        }
    }

    // Editing grade of future semesters
    @Test
    public void execute_addGradeToFutureSemester_success() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        EditCommand editCommand = new EditCommand(moduleCodeAmy, 0, VALID_GRADE_AMY, null,
                null, null);

        try {
            assertEquals(editCommand.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
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

        @Override
        public void setProfile(Profile target, Profile editedProfile) {
            requireAllNonNull(target, editedProfile);
            int index = this.profileList.indexOf(target);

            this.profileList.set(index, editedProfile);
        }
    }

    private class ProfileManagerWithNonEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithNonEmptyProfile() {
            Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            Deadline deadline = new Deadline(VALID_MODCODE_BOB, VALID_TASK_BOB);
            Personal personalBob = new Personal();
            personalBob.addDeadline(deadline);
            moduleBob.setPersonal(personalBob);
            Module moduleAmy = new Module(new ModuleCode(VALID_MODCODE_AMY), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), new PrereqTreeNode());
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profile.addModule(1, moduleBob);
            profile.addModule(2, moduleAmy);
            profileList.add(profile);
            addDeadline(deadline);
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
            Module module = new Module(new ModuleCode("CS2105"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(moduleAmy);
            moduleList.add(moduleBob);
            moduleList.add(module);
        }
    }
}
