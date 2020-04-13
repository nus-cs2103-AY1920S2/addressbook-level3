package seedu.nova.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

import seedu.nova.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path novaFilePath = Paths.get("data" , "Nova.json");
    private LocalDate scheduleStartDate = LocalDate.of(2020, 1, 13);
    private LocalDate scheduleEndDate = LocalDate.of(2020, 5, 3);

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

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
        setNovaFilePath(newUserPrefs.getNovaFilePath());
        setScheduleStartDate(newUserPrefs.getScheduleStartDate());
        setScheduleEndDate(newUserPrefs.getScheduleEndDate());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getNovaFilePath() {
        return novaFilePath;
    }

    public void setNovaFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.novaFilePath = addressBookFilePath;
    }

    public LocalDate getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(LocalDate scheduleStartDate) {
        requireNonNull(scheduleStartDate);
        this.scheduleStartDate = scheduleStartDate;
    }

    public LocalDate getScheduleEndDate() {
        return scheduleEndDate;
    }

    public void setScheduleEndDate(LocalDate scheduleEndDate) {
        requireNonNull(scheduleEndDate);
        this.scheduleEndDate = scheduleEndDate;
    }

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
                && novaFilePath.equals(o.novaFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, novaFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + novaFilePath);
        return sb.toString();
    }

}
