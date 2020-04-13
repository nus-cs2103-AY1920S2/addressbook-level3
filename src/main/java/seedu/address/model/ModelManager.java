package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.ActivityContainsActivityNamePredicate;
import seedu.address.model.event.Event;
import seedu.address.model.event.PlaceContainsPlaceNamePredicate;
import seedu.address.model.group.Group;
import seedu.address.model.person.EventDescriptor;
import seedu.address.model.person.NameContainsFullNamePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RecentEvent;
import seedu.address.model.person.Time;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Group> filteredGroups;
    private final FilteredList<Event> filteredEvents;
    private final ObservableList<EventDescriptor> frequencyList;
    private final ObservableList<RecentEvent> recentEventList;
    private final ObservableList<Time> timeList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        frequencyList = FXCollections.observableArrayList();
        recentEventList = FXCollections.observableArrayList();
        timeList = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // =========== AddressBook
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        showTime();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public boolean hasPersons(List<Person> people) {
        requireNonNull(people);
        return addressBook.hasPersons(people);
    }

    @Override
    public boolean hasGroups(List<Group> groups) {
        requireNonNull(groups);
        return addressBook.hasGroups(groups);
    }

    @Override
    public boolean hasEvents(List<Event> events) {
        requireNonNull(events);
        return addressBook.hasEvents(events);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addGroup(Group group) {
        addressBook.addGroup(group);
    }

    @Override
    public void deleteGroup(Group group) {
        addressBook.removeGroup(group);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        showTime();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);
        addressBook.setGroup(target, editedGroup);
    }

    // =========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return this.filteredGroups;
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return this.filteredEvents;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void importCsvToAddressBook(List<Person> importedPeople) {
        requireNonNull(importedPeople);
        addressBook.addPersons(importedPeople);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        showTime();
    }

    @Override
    public void importCsvGroupsToAddressBook(List<Group> importedGroup) {
        requireNonNull(importedGroup);
        addressBook.addGroups(importedGroup);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        showTime();
    }

    @Override
    public void importCsvEventsToAddressBook(List<Event> importedEvent) {
        requireNonNull(importedEvent);
        addressBook.addEvents(importedEvent);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        showTime();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    public void showPlaceList(Person target) {
        copyList(target.getPlaceList());
    }

    public void showActivityList(Person target) {
        copyList(target.getActivityList());
    }

    public void showRecentList(Person target) {
        copyRecent(target.getRecentEventList());
    }

    public void showGroupPlaceList(Group target) {
        copyList(target.getPlaceList2());
    }

    public void showGroupActivityList(Group target) {
        copyList(target.getActivityList2());
    }

    public void showGroupRecentList(Group target) {
        copyRecent(target.getRecentEventList());
    }

    /**
     * Copies the active PlaceList or ActivityList onto the Model's Frequency List.
     *
     * @param list List to be copied.
     */
    private void copyList(ObservableList<EventDescriptor> list) {
        frequencyList.clear();
        for (EventDescriptor eventDescriptor : list) {
            frequencyList.add(eventDescriptor);
        }
    }

    /**
     * Copies the target Person's active RecentEventList onto the Model's list.
     *
     * @param list List to be copied.
     */
    @Override
    public void copyRecent(ObservableList<RecentEvent> list) {
        recentEventList.clear();
        for (RecentEvent recentEvent : list) {
            recentEventList.add(recentEvent);
        }
    }

    @Override
    public void copyTime(ObservableList<Time> list) {
        timeList.clear();
        for (Time time : list) {
            timeList.add(time);
        }
    }

    @Override
    public void showTime() {
        ObservableList<Person> personList = addressBook.getPersonList();
        ObservableList<Group> groupList = addressBook.getGroupList();
        ObservableList<Time> timeList = FXCollections.observableArrayList();

        Time personTime = new Time(0, 0);
        Time groupTime = new Time(0, 0);
        for (Person onePerson : personList) {
            int personHour = onePerson.getTime().getHours();
            int personMin = onePerson.getTime().getMinutes();
            personTime.addTime(personMin, personHour);
        }

        for (Group oneGroup : groupList) {
            int groupHour = oneGroup.getTimeSpent().getHours();
            int groupMin = oneGroup.getTimeSpent().getMinutes();
            groupTime.addTime(groupMin, groupHour);
        }
        timeList.add(personTime);
        timeList.add(groupTime);
        copyTime(timeList);
    }

    /**
     * Updates filtered person list with suggested person based on time spent.
     */
    public void suggestPerson() {
        ObservableList<Person> personsList = addressBook.getPersonList();
        Person suggestedPerson;
        List<String> names = new ArrayList<>();
        NameContainsFullNamePredicate predicate = new NameContainsFullNamePredicate(names);
        if (!personsList.isEmpty()) {
            suggestedPerson = personsList.get(0);
            for (Person onePerson : personsList) {
                int suggestedHours = suggestedPerson.getTime().getHours();
                int suggestedMins = suggestedPerson.getTime().getMinutes();
                int hours = onePerson.getTime().getHours();
                int mins = onePerson.getTime().getMinutes();
                if (hours <= suggestedHours) {
                    if (mins <= suggestedMins) {
                        if (suggestedPerson.equals(onePerson)) {
                            continue;
                        } else {
                            suggestedPerson = onePerson;
                        }
                    }
                }
            }
            names.add(suggestedPerson.getName().toString());
            updateFilteredPersonList(predicate);
        } else {
            updateFilteredPersonList(predicate);
        }

    }

    /**
     * Suggests a place based on frequency
     */
    public void suggestPlace() {
        ObservableList<Event> eventList = addressBook.getEventList();
        Map<String, Integer> placeIntegerMap = new HashMap<>();
        List<String> place = new ArrayList<>();
        String minKey = "No places available. ";
        if (!eventList.isEmpty()) {
            for (Event oneEvent : eventList) {
                String suggestedPlace = oneEvent.getPlace();
                if (placeIntegerMap.containsKey(suggestedPlace)) {
                    placeIntegerMap.put(suggestedPlace, placeIntegerMap.get(suggestedPlace) + 1);
                } else {
                    placeIntegerMap.put(suggestedPlace, 1);
                }
            }

            //get min place visited
            int minValue = Integer.MAX_VALUE;
            for (String key : placeIntegerMap.keySet()) {
                int value = placeIntegerMap.get(key);
                if (value < minValue) {
                    minValue = value;
                    minKey = key;
                }
            }
            place.add(minKey);
            PlaceContainsPlaceNamePredicate predicate = new PlaceContainsPlaceNamePredicate(place);
            updateFilteredEventList(predicate);
        }
    }

    /**
     * Suggest activity
     */
    public void suggestActivity() {
        ObservableList<Event> eventList = addressBook.getEventList();
        Map<String, Integer> activityIntegerMap = new HashMap<>();
        List<String> activity = new ArrayList<>();
        String minKey = "No activities available. ";
        if (!eventList.isEmpty()) {
            for (Event oneEvent : eventList) {
                String suggestedActivity = oneEvent.getActivity();
                if (activityIntegerMap.containsKey(suggestedActivity)) {
                    activityIntegerMap.put(suggestedActivity, activityIntegerMap.get(suggestedActivity) + 1);
                } else {
                    activityIntegerMap.put(suggestedActivity, 1);
                }
            }

            //get min activity done
            int minValue = Integer.MAX_VALUE;
            for (String key : activityIntegerMap.keySet()) {
                int value = activityIntegerMap.get(key);
                if (value < minValue) {
                    minValue = value;
                    minKey = key;
                }
            }
            activity.add(minKey);
            ActivityContainsActivityNamePredicate predicate = new ActivityContainsActivityNamePredicate(activity);
            updateFilteredEventList(predicate);
        }
    }

    @Override
    public ObservableList<EventDescriptor> getFrequencyList() {
        return frequencyList;
    }

    @Override
    public ObservableList<RecentEvent> getRecentList() {
        return recentEventList;
    }

    @Override
    public ObservableList<Time> getTimeList() {
        return timeList;
    }

    @Override
    public String toString() {
        String finalContent = "";

        // NOTE: VersionedAddressBook does not have a toString() method, so you will need to manually
        // implement VersionedAddressBook#toString() to see its content!
        finalContent += "versioned addressbook: " + addressBook.toString();

        finalContent += ", filtered person list: [";
        for (Person p : filteredPersons) {
            finalContent += p.toString() + ",";
        }
        finalContent += "]";

        return finalContent;
    }
}

