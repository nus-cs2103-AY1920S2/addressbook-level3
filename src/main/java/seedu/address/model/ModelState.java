package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

/**
 * Class representing a single state of the application
 */
public class ModelState {
    private final AddressBook addressBook;
    private final RestaurantBook restaurantBook;
    private final AssignmentSchedule assignmentSchedule;
    private final EventSchedule eventSchedule;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredPersonsResult;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final FilteredList<Assignment> filteredAssignments;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Person> bdayList;

    private String commandType;

    /**
     * Initializes a ModelState with the given addressBook and userPrefs at the start.
     */
    public ModelState(ReadOnlyAddressBook addressBook, ReadOnlyRestaurantBook restaurantBook,
                      ReadOnlyAssignmentSchedule scheduler, ReadOnlyEventSchedule eventSchedule,
                      ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, scheduler, eventSchedule, userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonsList());
        filteredPersonsResult = new FilteredList<>(this.addressBook.getPersonsList());
        this.restaurantBook = new RestaurantBook(restaurantBook);
        this.assignmentSchedule = new AssignmentSchedule(scheduler);
        this.eventSchedule = new EventSchedule(eventSchedule);
        filteredRestaurants = new FilteredList<>(this.restaurantBook.getRestaurantsList());
        filteredAssignments = new FilteredList<>(this.assignmentSchedule.getAssignmentsList());
        filteredEvents = new FilteredList<>(this.eventSchedule.getEventsList());
        bdayList = new FilteredList<>(this.addressBook.getBdayList());
        this.commandType = "ADDRESS";
    }

    /**
     * Initializes a ModelState with the given addressBook and userPrefs.
     */
    public ModelState(ReadOnlyAddressBook addressBook, ReadOnlyRestaurantBook restaurantBook,
                      ReadOnlyAssignmentSchedule scheduler, ReadOnlyEventSchedule eventSchedule,
                      ReadOnlyUserPrefs userPrefs, String commandType) {
        super();
        requireAllNonNull(addressBook, scheduler, eventSchedule, userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonsList());
        filteredPersonsResult = new FilteredList<>(this.addressBook.getPersonsList());
        this.restaurantBook = new RestaurantBook(restaurantBook);
        this.assignmentSchedule = new AssignmentSchedule(scheduler);
        this.eventSchedule = new EventSchedule(eventSchedule);
        filteredRestaurants = new FilteredList<>(this.restaurantBook.getRestaurantsList());
        filteredAssignments = new FilteredList<>(this.assignmentSchedule.getAssignmentsList());
        filteredEvents = new FilteredList<>(this.eventSchedule.getEventsList());
        bdayList = new FilteredList<>(this.addressBook.getBdayList());
        this.commandType = commandType;
    }

    /**
     * Initializes an empty ModelState.
     */
    public ModelState() {
        super();

        this.addressBook = new AddressBook();
        this.userPrefs = new UserPrefs();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonsList());
        filteredPersonsResult = new FilteredList<>(this.addressBook.getPersonsList());
        this.restaurantBook = new RestaurantBook();
        this.assignmentSchedule = new AssignmentSchedule();
        this.eventSchedule = new EventSchedule();
        filteredRestaurants = new FilteredList<>(this.restaurantBook.getRestaurantsList());
        filteredAssignments = new FilteredList<>(this.assignmentSchedule.getAssignmentsList());
        filteredEvents = new FilteredList<>(this.eventSchedule.getEventsList());
        bdayList = new FilteredList<>(this.addressBook.getBdayList());
        this.commandType = commandType;
    }

    /**
     * Makes a copy of the state m
     * @param m the state to be copied
     * @return a new copy of the state m
     */
    public static ModelState copy(ModelState m) {
        return new ModelState(m.getAddressBook(), m.getRestaurantBook(), m.getAssignmentSchedule(),
            m.getEventSchedule(), m.getUserPrefs(), m.getCommandType());
    }

    public AddressBook getAddressBook() {
        return this.addressBook;
    }

    public RestaurantBook getRestaurantBook() {
        return this.restaurantBook;
    }

    public AssignmentSchedule getAssignmentSchedule() {
        return this.assignmentSchedule;
    }

    public EventSchedule getEventSchedule() {
        return this.eventSchedule;
    }

    public UserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    public FilteredList<Person> getFilteredPersons() {
        return this.filteredPersons;
    }

    public FilteredList<Person> getFilteredPersonsResult() {
        return this.filteredPersonsResult;
    }

    public FilteredList<Restaurant> getFilteredRestaurants() {
        return this.filteredRestaurants;
    }

    public FilteredList<Assignment> getFilteredAssignments() {
        return this.filteredAssignments;
    }

    public FilteredList<Event> getFilteredEvents() {
        return this.filteredEvents;
    }

    public FilteredList<Person> getBdayList() {
        return this.bdayList;
    }

    public String getCommandType() {
        return this.commandType;
    }

    public void setCommandType(String command) {
        this.commandType = command;
    }
}
