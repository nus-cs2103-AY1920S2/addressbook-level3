package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.UniqueExerciseList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDay;
import seedu.address.model.schedule.ScheduleList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final ClientInView clientInView;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ClientInView clientInView) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.clientInView = clientInView;
        filteredClients = new FilteredList<>(this.addressBook.getClientList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ClientInView());
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
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return addressBook.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        if (target == getClientInView()) {
            clearClientInView();
        }
        addressBook.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        addressBook.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        addressBook.setClient(target, editedClient);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    //=========== ClientInView ================================================================================

    @Override
    public Client getClientInView() {
        return clientInView.getClient();
    }

    @Override
    public void setClientInView(Client client) {
        clientInView.setClient(client);
    }

    @Override
    public void clearClientInView() {
        setClientInView(null);
    }

    @Override
    public boolean hasClientInView() {
        return clientInView.hasClientInView();
    }

    @Override
    public void updateClientViewIfApplicable(Client clientToEdit, Client editedClient) {
        if (!clientInView.hasClientInView()) {
            return;
        }
        Client currentClientInView = clientInView.getClient();
        if (currentClientInView.equals(clientToEdit)) {
            clientInView.setClient(editedClient);
        }
    }

    //=========== ScheduleList ==================================================================================
    @Override
    public ObservableList<ScheduleDay> getScheduleDayList() {
        ArrayList<ScheduleList> fullScheduleList = new ArrayList<>();
        for (Client c: filteredClients) {
            for (Schedule s: c.getScheduleList().getArrayList()) {
                s.assignClientName(c.getName().fullName);
            }
            fullScheduleList.add(c.getScheduleList());
        }
        return ScheduleDay.weeklySchedule(fullScheduleList);
    };

    //=========== Exercise ================================================================================

    @Override
    public void deleteExercise (Exercise exercise) {
        Client clientToEdit = getClientInView();
        UniqueExerciseList clientToEditExerciseList = clientToEdit.getExerciseList();

        // mutates the list belonging to the client by removing the exercise
        clientToEditExerciseList.remove(exercise);

        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getGender(), clientToEdit.getPhone(),
            clientToEdit.getEmail(), clientToEdit.getAddress(), clientToEdit.getTags(), clientToEdit.getBirthday(),
            clientToEdit.getCurrentWeight(), clientToEdit.getTargetWeight(), clientToEdit.getHeight(),
            clientToEdit.getRemark(), clientToEdit.getSports(), clientToEditExerciseList,
            clientToEdit.getPersonalBest(), clientToEdit.getScheduleList());

        setClient(clientToEdit, editedClient);
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
                && filteredClients.equals(other.filteredClients)
                && clientInView.equals(other.clientInView);
    }

}
