package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path staffAddressBookFilePath = Paths.get("data", "staffAddressbook.json");
    private Path studentAddressBookFilePath = Paths.get("data", "studentAddressbook.json");
    private Path courseAddressBookFilePath = Paths.get("data", "courseAddressbook.json");
    private Path financeAddressBookFilePath = Paths.get("data", "financeAddressbook.json");
    private Path assignmentAddressBookFilePath = Paths.get("data", "assignmentAddressbook.json");
    private Path progressAddressBookFilePath = Paths.get("data", "progressAddressbook.json");


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
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
    }

    /**
     * Get the existing GUI setting of this {@code UserPrefs} with {@code guiSettings}.
     */
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    /**
     * Set the existing GUI setting of this {@code UserPrefs} with new {@code guiSettings}.
     */
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    /**
     * Get the address book file path of this {@code UserPrefs}.
     */
    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    /**
     * Set the address book file path of this {@code UserPrefs} with {@code addressBookFilePath}.
     */
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    /**
     * Get the Staff address book file path of this {@code UserPrefs}.
     *
     * @return {@code staffAddressBookFilePath}.
     */
    public Path getStaffAddressBookFilePath() {
        return staffAddressBookFilePath;
    }

    /**
     * Set the Staff address book file path of this {@code UserPrefs} with new {@code staffAddressBookFilePath}.
     */
    public void setStaffAddressBookFilePath(Path staffAddressBookFilePath) {
        requireNonNull(staffAddressBookFilePath);
        this.staffAddressBookFilePath = staffAddressBookFilePath;
    }

    /**
     * Get the Student address book file path of this {@code UserPrefs}.
     *
     * @return {@code studentAddressBookFilePath}.
     */
    public Path getStudentAddressBookFilePath() {
        return studentAddressBookFilePath;
    }

    /**
     * Set the Student address book file path of this {@code UserPrefs} with new {@code studentAddressBookFilePath}.
     */
    public void setStudentAddressBookFilePath(Path studentAddressBookFilePath) {
        requireNonNull(studentAddressBookFilePath);
        this.studentAddressBookFilePath = studentAddressBookFilePath;
    }

    /**
     * Get the Course address book file path of this {@code UserPrefs}.
     *
     * @return {@code courseAddressBookFilePath}.
     */
    public Path getCourseAddressBookFilePath() {
        return courseAddressBookFilePath;
    }

    /**
     * Set the course address book file path of this {@code UserPrefs} with new {@code courseAddressBookFilePath}.
     */
    public void setCourseAddressBookFilePath(Path courseAddressBookFilePath) {
        requireNonNull(courseAddressBookFilePath);
        this.courseAddressBookFilePath = courseAddressBookFilePath;
    }

    /**
     * Get the Finance address book file path of this {@code UserPrefs} .
     *
     * @return {@code financeAddressBookFilePath}.
     */
    public Path getFinanceAddressBookFilePath() {
        return financeAddressBookFilePath;
    }

    /**
     * Set the Finance address book file path of this {@code UserPrefs} with {@code financeAddressBookFilePath}.
     */
    public void setFinanceAddressBookFilePath(Path financeAddressBookFilePath) {
        requireNonNull(financeAddressBookFilePath);
        this.financeAddressBookFilePath = financeAddressBookFilePath;
    }

    /**
     * Get the Assignment address book file path of this {@code UserPrefs}.
     *
     * @return {@code assignmentAddressBookFilePath}.
     */
    public Path getAssignmentAddressBookFilePath() {
        return assignmentAddressBookFilePath;
    }

    /**
     * Set the Assignment address book file path of this {@code UserPrefs} with {@code assignmentAddressBookFilePath}.
     */
    public void setAssignmentAddressBookFilePath(Path assignmentAddressBookFilePath) {
        requireNonNull(assignmentAddressBookFilePath);
        this.assignmentAddressBookFilePath = assignmentAddressBookFilePath;
    }

    /**
     * Get the Progress address book file path of this {@code UserPrefs}.
     *
     * @return {@code progressAddressBookFilePath}.
     */
    public Path getProgressAddressBookFilePath() {
        return progressAddressBookFilePath;
    }

    /**
     * Set the Progress address book file path of this {@code UserPrefs} with {@code progressAddressBookFilePath}.
     */
    public void setProgressAddressBookFilePath(Path progressAddressBookFilePath) {
        requireNonNull(progressAddressBookFilePath);
        this.progressAddressBookFilePath = progressAddressBookFilePath;
    }

    /**
     * Override equal method to compare this {@code UserPrefs} with another.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
