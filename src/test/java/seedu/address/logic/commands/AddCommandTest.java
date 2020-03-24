package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParserTest;
import seedu.address.model.*;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.*;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.DeadlineList;
import seedu.address.storage.JsonModuleListStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;

public class AddCommandTest {

    private static final Logger logger = LogsCenter.getLogger(AddCommandParserTest.class);
    private final ModuleCode moduleCode;
    private final int semester;
    private final String grade;
    private final String task;
    private final String deadline;

    public AddCommandTest() {
        Path moduleListFilePath = Paths.get("data/modules.json");
        JsonModuleListStorage modules = new JsonModuleListStorage(moduleListFilePath);
        try {
            Optional<ModuleList> moduleListOptional = modules.readModuleList();
            if (!moduleListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty ModuleList");
                new ModuleManager();
            } else {
                ModuleList moduleList = moduleListOptional.get();
                new ModuleManager(moduleList);
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ModuleList");
            new ModuleManager();
        }

        moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        semester = Integer.parseInt(VALID_SEMESTER_BOB);
        grade = VALID_GRADE_BOB;
        task = VALID_TASK_BOB;
        deadline = VALID_DEADLINE_BOB;
    }

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        // Null module code
        assertThrows(NullPointerException.class, () ->
                new AddCommand(null, semester, grade, task, deadline));
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Profile validProfile = new Profile(
                new Name("name"), new CourseName("course"), "3", "spec");
        Module module = new Module(moduleCode, new Title("title"), new PrereqList("prereq"),
                new ModularCredits("4"), new Description("desc"), new SemesterData(Arrays.asList("1","2")));
        String initialStatus = module.getPersonal().getStatus();
        // Create an AddCommand which attempts to add a module with the same module code
        AddCommand addCommand = new AddCommand(moduleCode, semester, null, null, null);
        // Create a "ProfileManager" which contains validProfile, which contains module
        ModelStub modelStub = new ModelStubWithProfile(validProfile, module);
        // Executing addCommand.execute should raise CommandException with the duplicate module message
        assertThrows(CommandException.class,
                String.format(AddCommand.MESSAGE_DUPLICATE_MODULE, module.getPersonal().getStatus()),
                () -> addCommand.execute(modelStub));
        // Also make sure that the module's status does not change after failing to add duplicate module
        assertTrue(module.getPersonal().getStatus().equals(initialStatus));
    }

    @Test
    public void execute_newModule_noChangeStatus() {
        Profile validProfile = new Profile(
                new Name("name"), new CourseName("course"), "3", "spec");
        Module module = new Module(new ModuleCode(VALID_MODCODE_AMY), new Title("title"),
                new PrereqList("prereq"), new ModularCredits("4"), new Description("desc"),
                new SemesterData(Arrays.asList("1","2")));
        int initialSemester = module.getPersonal().getSemester();
        String initialStatus = module.getPersonal().getStatus();
        String initialGrade = module.getPersonal().getGrade();
        DeadlineList initialDeadlines = module.getPersonal().getDeadlineList();
        // Create an AddCommand which attempts to add a module with the same module code
        AddCommand addCommand = new AddCommand(moduleCode, semester, null, null, null);
        // Create a "ProfileManager" which contains validProfile, which contains module
        ModelStub modelStub = new ModelStubWithProfile(validProfile, module);
        try {
            addCommand.execute(modelStub);
        } catch (CommandException e) {
            fail();
        }
        assertTrue(module.getPersonal().getSemester() == initialSemester); // Check if semester is the same
        assertTrue(module.getPersonal().getStatus().equals(initialStatus)); // Check if status is the same
        boolean sameGrade = (module.getPersonal().getGrade() == null && initialGrade == null)
                || (module.getPersonal().getGrade() != null && initialGrade != null
                && module.getPersonal().getGrade().equals(initialGrade));
        assertTrue(sameGrade); // Check if grade is the same
        assertTrue(module.getPersonal().getDeadlineList().equals(initialDeadlines)); // Check if deadlines are the same
    }

    @Test
    public void equals() {
        AddCommand addAmyCommand = new AddCommand(new ModuleCode(VALID_MODCODE_AMY),
                Integer.parseInt(VALID_SEMESTER_AMY), VALID_GRADE_AMY, VALID_TASK_AMY, VALID_DEADLINE_AMY);
        AddCommand addBobCommand = new AddCommand(moduleCode, semester, grade, task, deadline);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addAmyCommandCopy = new AddCommand(new ModuleCode(VALID_MODCODE_AMY),
                Integer.parseInt(VALID_SEMESTER_AMY), VALID_GRADE_AMY, VALID_TASK_AMY, VALID_DEADLINE_AMY);
        assertTrue(addAmyCommand.equals(addAmyCommandCopy));

        // same module code but remainder fields differ -> returns true
        AddCommand addBobCommandDiff = new AddCommand(new ModuleCode(VALID_MODCODE_BOB),
                Integer.parseInt(VALID_SEMESTER_AMY), VALID_GRADE_AMY, VALID_TASK_AMY, VALID_DEADLINE_AMY);
        assertTrue(addBobCommand.equals(addBobCommandDiff));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBobCommand));
    }

    /**
     * ModelStub acts as ProfileManager.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getProfileListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProfileListFilePath(Path profileListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProfileList(ProfileList profileList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ProfileList getProfileList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Profile profile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Profile target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Profile profile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Profile target, Profile editedProfile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Profile> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Profile> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProfile(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Profile getProfile(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Profile getFirstProfile() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithProfile extends ModelStub {
        private final Profile profile;

        ModelStubWithProfile(Profile profile, Module module) {
            requireNonNull(profile);
            this.profile = profile;
            this.profile.addModule(1, module);
        }

        @Override
        public Profile getFirstProfile() {
            return this.profile;
        }
    }
}
