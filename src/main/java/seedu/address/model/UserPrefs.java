package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path sessionsDirectory = Paths.get("data");
    private Path intervieweeDirectory = Paths.get("data", "interviewee.json");
    private Path attributeDirectory = Paths.get("data", "attribute.json");
    private Path questionDirectory = Paths.get("data", "question.json");
    private Path metricDirectory = Paths.get("data", "metric.json");
    private Path transcriptDirectory = Paths.get("data/transcript");
    private Path remarkDirectory = Paths.get("data/remark");

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
        setSessionsDirectory(newUserPrefs.getSessionsDirectory());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getSessionsDirectory() {
        return sessionsDirectory;
    }
    public Path getIntervieweeDirectory() {
        return this.intervieweeDirectory;
    }
    public Path getAttributeDirectory() {
        return this.attributeDirectory;
    }
    public Path getQuestionDirectory() {
        return this.questionDirectory;
    }
    public Path getMetricDirectory() {
        return this.metricDirectory;
    }
    public Path getTranscriptDirectory() {
        return this.transcriptDirectory;
    }
    public Path getRemarkDirectory() {
        return this.remarkDirectory;
    }

    public void setSessionsDirectory(Path sessionsDirectory) {
        requireNonNull(sessionsDirectory);
        this.sessionsDirectory = sessionsDirectory;
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
                && sessionsDirectory.equals(o.sessionsDirectory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, sessionsDirectory);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data directory location : " + sessionsDirectory);
        return sb.toString();
    }

}
