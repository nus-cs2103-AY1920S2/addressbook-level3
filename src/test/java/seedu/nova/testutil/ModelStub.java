package seedu.nova.testutil;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.nova.commons.core.GuiSettings;
import seedu.nova.logic.parser.ModeEnum;
import seedu.nova.model.Mode;
import seedu.nova.model.Model;
import seedu.nova.model.Nova;
import seedu.nova.model.ReadOnlyAddressBook;
import seedu.nova.model.ReadOnlyUserPrefs;
import seedu.nova.model.person.Person;
import seedu.nova.model.plan.StrongTask;
import seedu.nova.model.plan.Task;
import seedu.nova.model.plan.WeakTask;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.Tp;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.util.time.slotlist.DateTimeSlotList;

/**
 * A default model stub that have all of the methods failing.
 */

public class ModelStub implements Model {

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
    public Path getNovaFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNovaFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Nova getNova() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ModeEnum getModeEnum(Mode mode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getModeName(ModeEnum modeEnum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModeEnum(Mode mode, ModeEnum modeEnum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
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
    public void addPerson(Person person) {
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
    public void commitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String viewSchedule(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String viewSchedule(int weekNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isWithinSem(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isWithinSem(int weekNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Mode getMode() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEvent(Event e) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAllLessons(Lesson l) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public DateTimeSlotList getFreeSlotOn(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String viewFreeSlot(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String deleteEvent(LocalDate date, int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deleteEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String addNote(String desc, LocalDate date, int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetPlan() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean addRoutineTask(StrongTask st) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean addFlexibleTask(WeakTask wt) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Task> getTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Task searchTask(String name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deleteTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Event generateTaskEvent(Task task, LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Progress Tracker =============================================================
    @Override
    public ProgressTracker getProgressTracker() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Ip getProgressTrackerIp() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Tp getProgressTrackerTp() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String listPtTask(String projectName, int weekNum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPtTask(String projectName, int weekNum, PtTask task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deletePtTask(String projectName, int weekNum, int taskNum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean editPtTask(String projectName, int weekNum, int taskNum, String taskDesc) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean setDonePtTask(String projectName, int weekNum, int taskNum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean addPtNote(String projectName, int weekNum, int taskNum, String note) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deletePtNote(String projectName, int weekNum, int taskNum) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean editPtNote(String projectName, int weekNum, int taskNum, String note) {
        throw new AssertionError("This method should not be called.");
    }
}
