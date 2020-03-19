package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.listmanagers.ReadOnlyActivityManager;
import seedu.address.model.listmanagers.ReadOnlyFixedExpenseManager;
import seedu.address.model.listmanagers.ReadOnlyPackingListManager;
import seedu.address.model.listmanagers.ReadOnlyTransportBookingManager;
import seedu.address.model.listmanagers.ReadOnlyUserPrefs;
import seedu.address.model.listmanagers.accommodationbooking.AccommodationBooking;
import seedu.address.model.listmanagers.activity.Activity;
import seedu.address.model.listmanagers.fixedexpense.FixedExpense;
import seedu.address.model.listmanagers.packinglistitem.PackingListItem;
import seedu.address.model.listmanagers.transportbooking.TransportBooking;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<TransportBooking> PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<FixedExpense> PREDICATE_SHOW_ALL_FIXED_EXPENSES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<PackingListItem> PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITIES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<AccommodationBooking> PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS = unused -> true;

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

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    // ========== TransportBookingManager ==========

    /**
     * Returns the TransportBookingManager
     */
    ReadOnlyTransportBookingManager getTransportBookingManager();

    /**
     * Replaces TransportBookingManager data with the data in {@code transportBookingManager}.
     */
    void setTransportBookingManager(ReadOnlyTransportBookingManager transportBookingManager);

    /**
     * Returns true if a transport booking that is the same as {@code target} exists in the
     * TransportBookingManager.
     *
     * @param target the given transport booking.
     * @return true if the given transport booking already exist in the TransportBookingManager.
     */
    boolean hasTransportBooking(TransportBooking target);

    /**
     * Deletes the given transport booking.
     * The transport booking must exist in the TransportBookingManager.
     *
     * @param toDelete the given transport booking.
     */
    void deleteTransportBooking(TransportBooking toDelete);

    /**
     * Adds the given transport booking.
     * {@code toAdd} must not already exist in the TransportBookingManager.
     *
     * @param toAdd the given transport booking.
     */
    void addTransportBooking(TransportBooking toAdd);

    /**
     * Replaces the given transport booking {@code target} with {@code edited}.
     * {@code target} must exist in the TransportBookingManager.
     * {@code edited} must not be the same as another existing transport booking in the address book.
     *
     * @param target the given target transport booking.
     * @param edited the given edited transport booking.
     */
    void setTransportBooking(TransportBooking target, TransportBooking edited);

    /**
     * Returns an unmodifiable view of the filtered transport booking list
     */
    ObservableList<TransportBooking> getFilteredTransportBookingList();

    /**
     * Updates the filter of the filtered transport booking list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransportBookingList(Predicate<TransportBooking> predicate);

    // ========== FixedExpenseManager ==========

    /**
     * Returns the FixedExpenseManager
     */
    ReadOnlyFixedExpenseManager getFixedExpenseManager();

    /**
     * Replaces FixedExpenseManager data with the data in {@code fixedExpenseManager}.
     */
    void setFixedExpenseManager(ReadOnlyFixedExpenseManager fixedExpenseManager);

    /**
     * Returns true if a fixed expense that is the same as {@code target} exists in the
     * FixedExpenseManager.
     *
     * @param target the given fixed expense.
     * @return true if the given fixed expense already exist in the FixedExpenseManager.
     */
    boolean hasFixedExpense(FixedExpense target);

    /**
     * Deletes the given fixed expense.
     * The fixed expense must exist in the FixedExpenseManager.
     *
     * @param toDelete the given fixed expense.
     */
    void deleteFixedExpense(FixedExpense toDelete);

    /**
     * Adds the given fixed expense.
     * {@code toAdd} must not already exist in the FixedExpenseManager.
     *
     * @param toAdd the given fixed expense.
     */
    void addFixedExpense(FixedExpense toAdd);

    /**
     * Replaces the given fixed expense {@code target} with {@code edited}.
     * {@code target} must exist in the FixedExpenseManager.
     * {@code edited} must not be the same as another existing fixed expense in the address book.
     *
     * @param target the given target fixed expense.
     * @param edited the given edited fixed expense.
     */
    void setFixedExpense(FixedExpense target, FixedExpense edited);

    /**
     * Returns an unmodifiable view of the filtered fixed expense list
     */
    ObservableList<FixedExpense> getFilteredFixedExpenseList();

    /**
     * Updates the filter of the filtered fixed expense list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFixedExpenseList(Predicate<FixedExpense> predicate);

    // ========== PackingListManager ==========

    /**
     * Returns the PackingListManager
     */
    ReadOnlyPackingListManager getPackingListManager();

    /**
     * Replaces PackingListManager data with the data in {@code packingListManager}.
     */
    void setPackingListManager(ReadOnlyPackingListManager packingListManager);

    /**
     * Returns true if a packing list item that is the same as {@code target} exists in the
     * PackingListManager.
     *
     * @param target the given packing list item.
     * @return true if the given packing list item already exist in the PackingListManager.
     */
    boolean hasPackingListItem(PackingListItem target);

    /**
     * Deletes the given packing list item.
     * The packing list item must exist in the PackingListManager.
     *
     * @param toDelete the given packing list item.
     */
    void deletePackingListItem(PackingListItem toDelete);

    /**
     * Adds the given packing list item.
     * {@code toAdd} must not already exist in the PackingListManager.
     *
     * @param toAdd the given packing list item.
     */
    void addPackingListItem(PackingListItem toAdd);

    /**
     * Replaces the given packing list item {@code target} with {@code edited}.
     * {@code target} must exist in the PackingListManager.
     * {@code edited} must not be the same as another existing packing list item in the address book.
     *
     * @param target the given target packing list item.
     * @param edited the given edited packing list item.
     */
    void setPackingListItem(PackingListItem target, PackingListItem edited);

    /**
     * Returns an unmodifiable view of the filtered packing list item list
     */
    ObservableList<PackingListItem> getFilteredPackingList();

    /**
     * Updates the filter of the filtered packing list item list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPackingList(Predicate<PackingListItem> predicate);

    // ========== ActivityManager ==========
    /**
     * Returns the activityManager
     */
    ReadOnlyActivityManager getActivityManager();

    /**
     * Replaces activityManagerv data with the data in {@code activityManager}.
     */
    void setActivityManager(ReadOnlyActivityManager activityManager);
    /**
     * Returns true if a activity that is the same as {@code target} exists in the
     * ActivityManager.
     *
     * @param target the given activity.
     * @return true if the given activity already exist in the ActivityManager.
     */
    boolean hasActivity(Activity target);

    /**
     * Deletes the given activity.
     * The activity must exist in the ActivityManager.
     *
     * @param toDelete the given activity.
     */
    void deleteActivity(Activity toDelete);

    /**
     * Adds the given activity.
     * {@code toAdd} must not already exist in the ActivityManager.
     *
     * @param toAdd the given activity.
     */
    void addActivity(Activity toAdd);

    /**
     * Replaces the given activity {@code target} with {@code edited}.
     * {@code target} must exist in the ActivityManager.
     * {@code edited} must not be the same as another existing activity in the ActivityManager.
     *
     * @param target the given target activity.
     * @param edited the given edited activity.
     */
    void setActivity(Activity target, Activity edited);

    /**
     * Returns an unmodifiable view of the filtered activity list
     */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */

    void updateFilteredActivityList(Predicate<Activity> predicate);

    // ========== AccommodationBookingManager ==========

    /**
     * Returns true if a accommodation booking that is the same as {@code target} exists in the
     * AccommodationBookingManager.
     *
     * @param target the given accommodation booking.
     * @return true if the given accommodation booking already exist in the AccommodationBookingManager.
     */
    boolean hasAccommodationBooking(AccommodationBooking target);

    /**
     * Deletes the given accommodation booking.
     * The accommodation booking must exist in the AccommodationBookingManager.
     *
     * @param toDelete the given accommodation booking.
     */
    void deleteAccommodationBooking(AccommodationBooking toDelete);

    /**
     * Adds the given accommodation booking.
     * {@code toAdd} must not already exist in the AccommodationBookingManager.
     *
     * @param toAdd the given accommodation booking.
     */
    void addAccommodationBooking(AccommodationBooking toAdd);

    /**
     * Replaces the given accommodation booking {@code target} with {@code edited}.
     * {@code target} must exist in the AccommodationBookingManager.
     * {@code edited} must not be the same as another existing accommodation booking in the address book.
     *
     * @param target the given target accommodation booking.
     * @param edited the given edited accommodation booking.
     */
    void setAccommodationBooking(AccommodationBooking target, AccommodationBooking edited);

    /**
     * Returns an unmodifiable view of the filtered transport booking list
     */
    ObservableList<AccommodationBooking> getFilteredAccommodationBookingList();

    /**
     * Updates the filter of the filtered accommodation booking list to filter by the given {@code predicate}.
     *
     * @param predicate the given predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAccommodationBookingList(Predicate<AccommodationBooking> predicate);

}
