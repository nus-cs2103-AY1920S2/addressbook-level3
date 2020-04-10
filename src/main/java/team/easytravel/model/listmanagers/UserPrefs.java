package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Objects;

import team.easytravel.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {
    private static final Path DEFAULT_FOLDER_PATH = Path.of("data");

    private GuiSettings guiSettings = new GuiSettings();
    private Path transportBookingStorageFilePath = DEFAULT_FOLDER_PATH.resolve("transportation.json");
    private Path fixedExpenseStorageFilePath = DEFAULT_FOLDER_PATH.resolve("expense.json");
    private Path activityStorageFilePath = DEFAULT_FOLDER_PATH.resolve("activity.json");
    private Path accommodationBookingStorageFilePath = DEFAULT_FOLDER_PATH.resolve("accommodation.json");
    private Path packingListStorageFilePath = DEFAULT_FOLDER_PATH.resolve("packinglist.json");
    private Path tripStorageFilePath = DEFAULT_FOLDER_PATH.resolve("trip.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setActivityStorageFilePath(newUserPrefs.getActivityStorageFilePath());
        setAccommodationBookingStorageFilePath(newUserPrefs.getAccommodationBookingStorageFilePath());
        setTransportBookingStorageFilePath(newUserPrefs.getTransportBookingStorageFilePath());
        setFixedExpenseStorageFilePath(newUserPrefs.getFixedExpenseStorageFilePath());
        setPackingListStorageFilePath(newUserPrefs.getPackingListStorageFilePath());

    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getTransportBookingStorageFilePath() {
        return transportBookingStorageFilePath;
    }

    public void setTransportBookingStorageFilePath(Path transportBookingStorageFilePath) {
        requireNonNull(transportBookingStorageFilePath);
        this.transportBookingStorageFilePath = transportBookingStorageFilePath;
    }

    @Override
    public Path getFixedExpenseStorageFilePath() {
        return fixedExpenseStorageFilePath;
    }

    public void setFixedExpenseStorageFilePath (Path fixedExpenseStorageFilePath) {
        requireNonNull(fixedExpenseStorageFilePath);
        this.fixedExpenseStorageFilePath = fixedExpenseStorageFilePath;
    }

    @Override
    public Path getActivityStorageFilePath() {
        return activityStorageFilePath;
    }

    public void setActivityStorageFilePath(Path activityStorageFilePath) {
        requireNonNull(activityStorageFilePath);
        this.activityStorageFilePath = activityStorageFilePath;
    }

    @Override
    public Path getAccommodationBookingStorageFilePath() {
        return accommodationBookingStorageFilePath;
    }

    public void setAccommodationBookingStorageFilePath(Path accommodationBookingStorageFilePath) {
        requireNonNull(accommodationBookingStorageFilePath);
        this.accommodationBookingStorageFilePath = accommodationBookingStorageFilePath;
    }

    @Override
    public Path getPackingListStorageFilePath() {
        return packingListStorageFilePath;
    }

    public void setPackingListStorageFilePath(Path packingListStorageFilePath) {
        requireNonNull(packingListStorageFilePath);
        this.packingListStorageFilePath = packingListStorageFilePath;
    }

    @Override
    public Path getTripStorageFilePath() {
        return tripStorageFilePath;
    }

    public void setTripStorageFilePath(Path tripStorageFilePath) {
        requireNonNull(tripStorageFilePath);
        this.tripStorageFilePath = tripStorageFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;

        return guiSettings.equals(otherUserPrefs.guiSettings)
                && transportBookingStorageFilePath.equals(otherUserPrefs.transportBookingStorageFilePath)
                && fixedExpenseStorageFilePath.equals(otherUserPrefs.fixedExpenseStorageFilePath)
                && activityStorageFilePath.equals(otherUserPrefs.activityStorageFilePath)
                && accommodationBookingStorageFilePath.equals(otherUserPrefs.accommodationBookingStorageFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, transportBookingStorageFilePath,
                fixedExpenseStorageFilePath, accommodationBookingStorageFilePath, activityStorageFilePath);
    }

    @Override
    public String toString() {
        return "Gui Settings : " + guiSettings
                + "\nTransport Bookings data file location : " + transportBookingStorageFilePath
                + "\nFixed Expenses data file location : " + fixedExpenseStorageFilePath
                + "\nAccommodation Bookings data file location : " + accommodationBookingStorageFilePath
                + "\nActivities data file location: " + activityStorageFilePath
                + "\nTrip data file location: " + tripStorageFilePath;
    }

}
