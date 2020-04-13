package seedu.address.testutil;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.EventDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.RecentEvent;
import seedu.address.model.person.Time;
/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
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
    public void addEvent(Event event) {
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
    public boolean hasGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroups(List<Group> groups) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPersons(List<Person> people) {
        return false;
    }

    @Override
    public boolean hasEvents(List<Event> events) {
        return false;
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
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
    public void importCsvToAddressBook(List<Person> importedPeople) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importCsvGroupsToAddressBook(List<Group> importedGroup) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importCsvEventsToAddressBook(List<Event> importedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showPlaceList(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showActivityList(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showRecentList(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showTime() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void suggestPerson() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void suggestPlace() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void suggestActivity() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void copyRecent(ObservableList<RecentEvent> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void copyTime(ObservableList<Time> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<EventDescriptor> getFrequencyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<RecentEvent> getRecentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Time> getTimeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showGroupRecentList(Group groupToView) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showGroupActivityList(Group groupToView) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showGroupPlaceList(Group groupToView) {
        throw new AssertionError("This method should not be called.");
    }
}
