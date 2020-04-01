package seedu.recipe.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.recipe.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path recipeBookFilePath = Paths.get("data" , "recipebook.json");
    private Path cookedRecordFilePath = Paths.get("data" , "cookedrecords.json");
    private Path plannedBookFilePath = Paths.get("data", "plannedbook.json");


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
        setRecipeBookFilePath(newUserPrefs.getRecipeBookFilePath());
        setPlannedBookFilePath(newUserPrefs.getPlannedBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getRecipeBookFilePath() {
        return recipeBookFilePath;
    }

    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        requireNonNull(recipeBookFilePath);
        this.recipeBookFilePath = recipeBookFilePath;
    }

    public Path getPlannedBookFilePath() {
        return plannedBookFilePath;
    }

    public void setPlannedBookFilePath(Path plannedBookFilePath) {
        requireNonNull(plannedBookFilePath);
        this.plannedBookFilePath = plannedBookFilePath;
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
                && recipeBookFilePath.equals(o.recipeBookFilePath)
                && plannedBookFilePath.equals(o.plannedBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, recipeBookFilePath, plannedBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal recipe data file location : " + recipeBookFilePath);
        sb.append("\nLocal planned recipe data file location: " + plannedBookFilePath);
        return sb.toString();
    }

    public Path getCookedRecordFilePath() {
        return cookedRecordFilePath;
    }
}
