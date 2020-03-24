package csdev.couponstash.storage;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.UserPrefs;

/**
 * Jackson-friendly version of {@link ReadOnlyUserPrefs}.
 * Although StashSettings and MoneySymbol in UserPrefs
 * are mutable, this class is immutable.
 */
public class JsonAdaptedUserPrefs {
    private GuiSettings guiSettings;
    private JsonAdaptedStashSettings stashSettings;
    private Path couponStashFilePath;

    /**
     * Constructs a JsonAdaptedStashSettings using the
     * properties from the JSON file.
     */
    public JsonAdaptedUserPrefs(@JsonProperty("guiSettings") GuiSettings guiSettings,
                                @JsonProperty("stashSettings") JsonAdaptedStashSettings stashSettings,
                                @JsonProperty("couponStashFilePath") Path couponStashFilePath) {
        this.guiSettings = guiSettings;
        this.stashSettings = stashSettings;
        this.couponStashFilePath = couponStashFilePath;
    }

    /**
     * Instantiates a new JsonAdaptedUserPrefs using the
     * UserPrefs provided. When this constructor is called,
     * JsonAdaptedUserPrefs (immutable) will be created with
     * the current values stored in UserPrefs (mutable).
     *
     * @param up The UserPrefs to be adapted.
     */
    public JsonAdaptedUserPrefs(ReadOnlyUserPrefs up) {
        this.guiSettings = up.getGuiSettings();
        this.stashSettings = new JsonAdaptedStashSettings(up.getStashSettings());
        this.couponStashFilePath = up.getCouponStashFilePath();
    }

    /**
     * Converts this JsonAdaptedStashSettings to the
     * normal UserPrefs Object used in Coupon Stash.
     * @return UserPrefs object used in the
     *         Coupon Stash application.
     */
    public UserPrefs convertToNormalType() {
        UserPrefs newUserPrefs = new UserPrefs();
        if (this.guiSettings != null) {
            newUserPrefs.setGuiSettings(this.guiSettings);
        }
        if (this.stashSettings != null) {
            newUserPrefs.setStashSettings(this.stashSettings.convertToNormalType());
        }
        if (this.couponStashFilePath != null) {
            newUserPrefs.setCouponStashFilePath(this.couponStashFilePath);
        }
        return newUserPrefs;
    }
}
