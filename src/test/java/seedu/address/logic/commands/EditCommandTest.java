package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_MODULE_DATA;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;
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
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        EditCommand editCommand = new EditCommand(moduleCode, 0, VALID_GRADE_AMY, null, null, null);

        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile profile = new Profile(name, courseName, semester, focusArea);

        ProfileManagerStub profileManagerStub = new ProfileManagerStub();
        profileManagerStub.addPerson(profile);

        assertThrows(CommandException.class, MESSAGE_EMPTY_MODULE_DATA, () ->
                editCommand.execute(profileManagerStub, new CourseManagerStub(), new ModuleManagerStub()));
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
