package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.EventDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.RecentEvent;
import seedu.address.model.person.Time;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    boolean hasPersons(List<Person> people);

    boolean hasGroup(Group group);

    boolean hasGroups(List<Group> groups);

    boolean hasEvent(Event event);

    boolean hasEvents(List<Event> events);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    void deleteGroup(Group group);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    void addGroup(Group group);

    void addEvent(Event event);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist
     * in the address book. The person identity of {@code editedPerson} must not be the same as
     * another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    void setGroup(Group target, Group editedGroup);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Group> getFilteredGroupList();

    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void updateFilteredGroupList(Predicate<Group> predicate);

    void updateFilteredEventList(Predicate<Event> predicate);

    void importCsvToAddressBook(List<Person> importedPeople);

    void importCsvGroupsToAddressBook(List<Group> importedGroup);

    void importCsvEventsToAddressBook(List<Event> importedEvent);

    void showTime();

    void showPlaceList(Person target);

    void showActivityList(Person target);

    void showRecentList(Person target);

    void suggestPerson();

    void suggestPlace();

    void suggestActivity();

    void copyRecent(ObservableList<RecentEvent> list);

    void copyTime(ObservableList<Time> list);

    ObservableList<EventDescriptor> getFrequencyList();

    ObservableList<RecentEvent> getRecentList();

    ObservableList<Time> getTimeList();

    void showGroupPlaceList(Group groupToView);

    void showGroupActivityList(Group groupToView);

    void showGroupRecentList(Group groupToView);
}
