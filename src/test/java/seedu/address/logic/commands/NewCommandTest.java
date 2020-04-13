package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.NewCommand.MESSAGE_DUPLICATE_PROFILE;
import static seedu.address.logic.commands.NewCommand.MESSAGE_SUCCESS;
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
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

//@@author joycelynteo
public class NewCommandTest {

    // Constructor is null.
    @Test
    public void constructor_nullProfile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NewCommand(null));
    }

    // Profile with same name already exists.
    @Test
    public void execute_duplicateProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(name, courseName, semester, focusArea);

        NewCommand newCommandAmy = new NewCommand(amyProfile);

        ProfileManagerStub profileManagerStub = new ProfileManagerStub();
        profileManagerStub.addProfile(amyProfile);

        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_PROFILE, amyProfile.getName()), () ->
                newCommandAmy.execute(profileManagerStub, new CourseManagerStub(), new ModuleManagerStub()));
    }

    // Another profile already exists.
    @Test
    public void execute_profileExists_throwsCommandException() {
        Name nameAmy = new Name(VALID_NAME_AMY);
        CourseName courseNameAmy = new CourseName(VALID_COURSE_AMY);
        int semesterAmy = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusAreaAmy = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(nameAmy, courseNameAmy, semesterAmy, focusAreaAmy);

        Name nameBob = new Name(VALID_NAME_BOB);
        CourseName courseNameBob = new CourseName(VALID_COURSE_BOB);
        int semesterBob = new Year(VALID_SEMESTER_BOB).getSemester();
        FocusArea focusAreaBob = new FocusArea(VALID_FOCUS_AREA_BOB);
        Profile bobProfile = new Profile(nameBob, courseNameBob, semesterBob, focusAreaBob);

        NewCommand newCommandBob = new NewCommand(bobProfile);

        ProfileManagerStub profileManagerStub = new ProfileManagerStub();
        profileManagerStub.addProfile(amyProfile);

        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_PROFILE, amyProfile.getName()), () ->
                newCommandBob.execute(profileManagerStub, new CourseManagerStub(), new ModuleManagerStub()));
    }

    @Test
    public void execute_validFields_success() {
        Name nameAmy = new Name(VALID_NAME_AMY);
        CourseName courseNameAmy = new CourseName(VALID_COURSE_AMY);
        int semesterAmy = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusAreaAmy = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(nameAmy, courseNameAmy, semesterAmy, focusAreaAmy);

        NewCommand newCommand = new NewCommand(amyProfile);

        try {
            assertEquals(newCommand.execute(new ProfileManagerStub(), new CourseManagerStub(),
                    new ModuleManagerStub()).getFeedbackToUser(),
                    String.format(MESSAGE_SUCCESS, amyProfile));
        } catch (CommandException e) {
            fail();
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
        public ObservableList<Profile> getFilteredPersonList() {
            return filteredProfiles;
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
}

