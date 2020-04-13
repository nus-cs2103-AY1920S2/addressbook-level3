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
    private Path cookbookFilePath = Paths.get("data" , "cookbook.json");
    private Path inventoryFilePath = Paths.get("data" , "inventory.json");
    private Path cartFilePath = Paths.get("data" , "cart.json");

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
        setCookbookFilePath(newUserPrefs.getCookbookFilePath());
        setInventoryFilePath(newUserPrefs.getInventoryFilePath());
        setCartFilePath(newUserPrefs.getCartFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCookbookFilePath() {
        return cookbookFilePath;
    }

    public void setCookbookFilePath(Path cookbookFilePath) {
        requireNonNull(cookbookFilePath);
        this.cookbookFilePath = cookbookFilePath;
    }

    public Path getInventoryFilePath() {
        return inventoryFilePath;
    }

    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        this.inventoryFilePath = inventoryFilePath;
    }

    public Path getCartFilePath() {
        return cartFilePath;
    }

    public void setCartFilePath(Path cartFilePath) {
        requireNonNull(cartFilePath);
        this.cartFilePath = cartFilePath;
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
                && cookbookFilePath.equals(o.cookbookFilePath)
                && inventoryFilePath.equals(o.inventoryFilePath)
                && cartFilePath.equals(o.cartFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, cookbookFilePath, inventoryFilePath, cartFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + cookbookFilePath);
        sb.append("\nLocal data file location : " + inventoryFilePath);
        sb.append("\nLocal data file location : " + cartFilePath);
        return sb.toString();
    }

}
