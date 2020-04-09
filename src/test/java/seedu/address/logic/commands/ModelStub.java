package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventSchedule;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.day.Day;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void clear() {
        throw new AssertionError("This method should not be called.");
    }

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
    public ReadOnlyScheduler getScheduler() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonListResult() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonListResult(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setScheduler(ReadOnlyScheduler scheduler) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getEventsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAssignment(Assignment toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAssignment(Assignment toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortAssignment(Comparator<Assignment> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAssignment(Assignment assignmentToUpdate, Assignment updatedAssignment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAssignment(Assignment assignmentToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAssignmentDueOnSameDate(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventSchedule(ReadOnlyEventSchedule eventSchedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyEventSchedule getEventSchedule() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEvent(Event toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEvent(Event eventToUpdate, Event updatedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteEvent(Event toDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEvent(Event toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasClashingEvent(Event toAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortEvent(Comparator<Event> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    public ObservableList<Person> getBdayListResult() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Day> getScheduleVisualResult() {
        throw new AssertionError("This method should not be called.");
    }

    public void calculateScheduleIntensity(int numDays) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int undoStackSize() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int redoStackSize() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String undo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String redo() {
        throw new AssertionError("This method should not be called.");
    }
}
