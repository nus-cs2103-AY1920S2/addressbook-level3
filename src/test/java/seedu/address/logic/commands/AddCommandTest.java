package seedu.address.logic.commands;

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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
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
import seedu.address.testutil.PersonBuilder;


public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
