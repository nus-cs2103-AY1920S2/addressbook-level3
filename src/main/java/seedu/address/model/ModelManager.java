package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.listmanagers.AccommodationBookingManager;
import seedu.address.model.listmanagers.ActivityManager;
import seedu.address.model.listmanagers.FixedExpenseManager;
import seedu.address.model.listmanagers.PackingListManager;
import seedu.address.model.listmanagers.ReadOnlyAccommodationBookingManager;
import seedu.address.model.listmanagers.ReadOnlyActivityManager;
import seedu.address.model.listmanagers.ReadOnlyFixedExpenseManager;
import seedu.address.model.listmanagers.ReadOnlyPackingListManager;
import seedu.address.model.listmanagers.ReadOnlyTransportBookingManager;
import seedu.address.model.listmanagers.ReadOnlyUserPrefs;
import seedu.address.model.listmanagers.TransportBookingManager;
import seedu.address.model.listmanagers.UserPrefs;
import seedu.address.model.listmanagers.accommodationbooking.AccommodationBooking;
import seedu.address.model.listmanagers.activity.Activity;
import seedu.address.model.listmanagers.fixedexpense.FixedExpense;
import seedu.address.model.listmanagers.packinglistitem.PackingListItem;
import seedu.address.model.listmanagers.transportbooking.TransportBooking;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final TransportBookingManager transportBookingManager;
    private final FixedExpenseManager fixedExpenseManager;
    private final PackingListManager packingListManager;
    private final ActivityManager activityManager;
    private final AccommodationBookingManager accommodationBookingManager;
    private final UserPrefs userPrefs;

    private final FilteredList<Person> filteredPersons;
    private final FilteredList<TransportBooking> filteredTransportBookingList;
    private final FilteredList<FixedExpense> filteredFixedExpenseList;
    private final FilteredList<PackingListItem> filteredPackingList;
    private final FilteredList<Activity> filteredActivityList;
    private final FilteredList<AccommodationBooking> filteredAccommodationBookingList;

    /**
     * Initializes a ModelManager with the given managers and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTransportBookingManager transportBookingManager,
                        ReadOnlyFixedExpenseManager fixedExpenseManager, ReadOnlyPackingListManager packingListManager,
                        ReadOnlyActivityManager activityManager,
                        ReadOnlyAccommodationBookingManager accommodationBookingManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.transportBookingManager = new TransportBookingManager(transportBookingManager);
        this.fixedExpenseManager = new FixedExpenseManager(fixedExpenseManager);
        this.packingListManager = new PackingListManager(packingListManager);
        this.activityManager = new ActivityManager(activityManager);
        this.accommodationBookingManager = new AccommodationBookingManager(accommodationBookingManager);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTransportBookingList = new FilteredList<>(this.transportBookingManager.getTransportBookings());
        filteredFixedExpenseList = new FilteredList<>(this.fixedExpenseManager.getFixedExpenseList());
        filteredPackingList = new FilteredList<>(this.packingListManager.getPackingList());
        filteredActivityList = new FilteredList<>(this.activityManager.getActivityList());
        filteredAccommodationBookingList = new FilteredList<>((this.accommodationBookingManager
                .getAccommodationBookingList()));
    }

    public ModelManager() {
        this(new AddressBook(), new TransportBookingManager(), new FixedExpenseManager(), new PackingListManager(),
                new ActivityManager(), new AccommodationBookingManager(), new UserPrefs());
    }

    // Temporary constructor
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTransportBookingManager transportBookingManager,
                        ReadOnlyFixedExpenseManager fixedExpenseManager,
                        ReadOnlyAccommodationBookingManager accommodationBookingManager,
                        ReadOnlyPackingListManager packingListManager,
                        ReadOnlyUserPrefs userPrefs) {
        this(addressBook, transportBookingManager, fixedExpenseManager, packingListManager,
                new ActivityManager(), accommodationBookingManager, userPrefs);
    }

    //=========== UserPrefs ==================================================================================

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

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
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
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // TODO: Implement the methods below
    // ========== TransportBookingManager ==========

    @Override
    public ReadOnlyTransportBookingManager getTransportBookingManager() {
        return transportBookingManager;
    }

    @Override
    public void setTransportBookingManager(ReadOnlyTransportBookingManager transportBookingManager) {
        this.transportBookingManager.resetData(transportBookingManager);
    }

    @Override
    public boolean hasTransportBooking(TransportBooking target) {
        requireNonNull(target);
        return transportBookingManager.hasTransportBooking(target);
    }

    @Override
    public void deleteTransportBooking(TransportBooking toDelete) {
        transportBookingManager.removeTransportBooking(toDelete);
    }

    @Override
    public void addTransportBooking(TransportBooking toAdd) {
        transportBookingManager.addTransportBooking(toAdd);
        updateFilteredTransportBookingList(PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS);
    }

    @Override
    public void setTransportBooking(TransportBooking target, TransportBooking edited) {
        requireAllNonNull(target, edited);
        transportBookingManager.setTransportBooking(target, edited);
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        return filteredTransportBookingList;
    }

    @Override
    public void updateFilteredTransportBookingList(Predicate<TransportBooking> predicate) {
        requireNonNull(predicate);
        filteredTransportBookingList.setPredicate(predicate);
    }

    // ========== FixedExpenseManager ==========

    @Override
    public ReadOnlyFixedExpenseManager getFixedExpenseManager() {
        return fixedExpenseManager;
    }

    public void setFixedExpenseManager(ReadOnlyFixedExpenseManager fixedExpenseManager) {
        this.fixedExpenseManager.resetData(fixedExpenseManager);
    }

    @Override
    public boolean hasFixedExpense(FixedExpense target) {
        requireNonNull(target);
        return fixedExpenseManager.hasFixedExpense(target);
    }

    @Override
    public void deleteFixedExpense(FixedExpense toDelete) {
        fixedExpenseManager.removeFixedExpense(toDelete);
    }

    @Override
    public void addFixedExpense(FixedExpense toAdd) {
        fixedExpenseManager.addFixedExpense(toAdd);
        updateFilteredFixedExpenseList(PREDICATE_SHOW_ALL_FIXED_EXPENSES);
    }

    @Override
    public void setFixedExpense(FixedExpense target, FixedExpense edited) {
        requireAllNonNull(target, edited);
        fixedExpenseManager.setFixedExpense(target, edited);
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        return filteredFixedExpenseList;
    }

    @Override
    public void updateFilteredFixedExpenseList(Predicate<FixedExpense> predicate) {
        requireNonNull(predicate);
        filteredFixedExpenseList.setPredicate(predicate);
    }

    // ========== PackingListManager ==========

    @Override
    public ReadOnlyPackingListManager getPackingListManager() {
        return packingListManager;
    }

    @Override
    public void setPackingListManager(ReadOnlyPackingListManager packingListManager) {
        requireAllNonNull(packingListManager);
        this.packingListManager.resetData(packingListManager);
    }

    @Override
    public boolean hasPackingListItem(PackingListItem target) {
        requireNonNull(target);
        return packingListManager.hasPackingListItem(target);
    }

    @Override
    public void deletePackingListItem(PackingListItem toDelete) {
        packingListManager.removePackingListItem(toDelete);
    }

    @Override
    public void addPackingListItem(PackingListItem toAdd) {
        packingListManager.addPackingListItem(toAdd);
        updateFilteredPackingList(PREDICATE_SHOW_ALL_PACKING_LIST_ITEMS);
    }

    @Override
    public void setPackingListItem(PackingListItem target, PackingListItem edited) {
        requireAllNonNull(target, edited);
        packingListManager.setPackingListItem(target, edited);
    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return filteredPackingList;
    }

    @Override
    public void updateFilteredPackingList(Predicate<PackingListItem> predicate) {
        requireNonNull(predicate);
        filteredPackingList.setPredicate(predicate);
    }

    // ========== ActivityManager ==========

    @Override
    public ReadOnlyActivityManager getActivityManager() {
        return activityManager;
    }

    public void setActivityManager(ReadOnlyActivityManager activityManager) {
        this.activityManager.resetData(activityManager);
    }

    @Override
    public boolean hasActivity(Activity target) {
        requireNonNull(target);
        return activityManager.hasActivity(target);
    }

    @Override
    public void deleteActivity(Activity toDelete) {
        activityManager.removeActivity(toDelete);

    }

    @Override
    public void addActivity(Activity toAdd) {
        activityManager.addActivity(toAdd);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity edited) {
        requireAllNonNull(target, edited);
        activityManager.setActivity(target, edited);
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivityList;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivityList.setPredicate(predicate);
    }

    // ========== AccommodationBookingManager ==========

    @Override
    public boolean hasAccommodationBooking(AccommodationBooking target) {
        requireNonNull(target);
        return accommodationBookingManager.hasAccommodationBooking(target);
    }

    @Override
    public void deleteAccommodationBooking(AccommodationBooking toDelete) {
        accommodationBookingManager.removeAccommodationBooking(toDelete);
    }

    @Override
    public void addAccommodationBooking(AccommodationBooking toAdd) {
        accommodationBookingManager.addAccommodationBooking(toAdd);
        updateFilteredAccommodationBookingList(PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS);
    }

    @Override
    public void setAccommodationBooking(AccommodationBooking target, AccommodationBooking edited) {
        requireAllNonNull(target, edited);
        accommodationBookingManager.setAccommodationBooking(target, edited);
    }

    @Override
    public ObservableList<AccommodationBooking> getFilteredAccommodationBookingList() {
        return filteredAccommodationBookingList;
    }

    @Override
    public void updateFilteredAccommodationBookingList(Predicate<AccommodationBooking> predicate) {
        requireNonNull(predicate);
        filteredAccommodationBookingList.setPredicate(predicate);
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
                && filteredPersons.equals(other.filteredPersons)
                && transportBookingManager.equals(other.transportBookingManager)
                && fixedExpenseManager.equals(other.fixedExpenseManager)
                && packingListManager.equals(other.packingListManager)
                && activityManager.equals(other.activityManager)
                && accommodationBookingManager.equals(other.accommodationBookingManager);
    }

}
