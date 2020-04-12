package seedu.address.logic.commands.modulecommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModuleBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.calender.Task;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.weather.Weather;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.person.Person;
import seedu.address.model.studentprofile.Profile;
import seedu.address.testutil.TypicalNusModules;

public class AddModuleCommandTest {

    @Test
    public void constructor_nullNusModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    @Test
    public void execute_nusModuleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNusModuleAdded modelStub = new ModelStubAcceptingNusModuleAdded();
        NusModule validModule = TypicalNusModules.CS2030;
        CommandResult commandResult = new AddModuleCommand(validModule).execute(modelStub);

        assertEquals(AddModuleCommand.MESSAGE_SUCCESS + validModule, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateNusModule_throwsCommandException() {
        NusModule validModule = TypicalNusModules.CS2030;
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModule);
        ModelStub modelStub = new ModelStubWithNusModule(validModule);

        assertThrows(CommandException.class, AddModuleCommand.MESSAGE_DUPLICATE_NUS_MODULE, (
        ) -> addModuleCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        NusModule module1 = TypicalNusModules.CS2030;
        NusModule module2 = TypicalNusModules.CS2103;
        AddModuleCommand addModuleCommand1 = new AddModuleCommand(module1);
        AddModuleCommand addModuleCommand2 = new AddModuleCommand(module2);

        // same object -> returns true
        assertTrue(addModuleCommand1.equals(addModuleCommand1));

        // same values -> returns true
        AddModuleCommand addModuleCommand1Copy = new AddModuleCommand(module1);
        assertTrue(addModuleCommand1.equals(addModuleCommand1Copy));

        // different types -> returns false
        assertFalse(addModuleCommand1.equals(1));

        // null -> returns false
        assertFalse(addModuleCommand1.equals(null));

        // different person -> returns false
        assertFalse(addModuleCommand1.equals(addModuleCommand2));
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDiaryBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DiaryEntry> getDiaryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidEntryId(int entryId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDiaryEntry(int entryId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void tagWeather(int entryId, Weather weather) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void tagMood(int entryId, Mood mood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DiaryEntry getDiaryEntryById(int entryId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Integer> getListOfIdsByDate(LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isExistingDate(LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Notes> getFilesInFolderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDiaryEntry(DiaryEntry diaryEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isEmptyDiaryEntry(DiaryEntry diaryEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String showDiaryLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateNotesList(Predicate<Notes> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(NusModule module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void doneModuleTask(ModuleCode moduleCode, Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getModuleTaskInfo(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTaskBreakdown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<ModuleTask> getModuleTaskList(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Task> findTasksByDate(String date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Task> findTasksByCat(String cat) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void gradeModule(ModuleCode moduleCode, Grade grade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleTask(ModuleTask moduleTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ModuleBook getModuleBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getSizeOfModuleTaskList(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModuleTask(ModuleCode moduleCode, Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMajor(Major major) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableValue<String> getMajor() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Profile getProfile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double getCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<NusModule> getModulesListTaken() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateModulesListTaken(Predicate<NusModule> predicate) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getModuleBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeadlineTask(Task deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Task deleteTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Task doneDeadlineTask(Task deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTask(String param) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isEmptyDeadline(Task deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getDeadlineTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeadlineTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToDo(Task todo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isEmptyToDo(Task todo) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithNusModule extends ModelStub {
        private final NusModule module;

        ModelStubWithNusModule(NusModule module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            requireNonNull(moduleCode);
            return this.module.getModuleCode().equals(moduleCode);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingNusModuleAdded extends ModelStub {
        final ArrayList<NusModule> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            requireNonNull(moduleCode);
            return modulesAdded.stream().map(NusModule::getModuleCode).anyMatch(moduleCode::equals);
        }

        @Override
        public void addModule(NusModule module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ModuleBook getModuleBook() {
            return new ModuleBook();
        }

        @Override
        public void updateModulesListTaken(Predicate<NusModule> predicate) {

        }
    }
}
