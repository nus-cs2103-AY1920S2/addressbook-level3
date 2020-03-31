package seedu.eylah.commons.model;

import static java.util.Objects.requireNonNull;

/**
 * The main LogicManager of the app.
 */
public class ModelManager implements Model {

    private final UserPrefs userPrefs;

    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        this.userPrefs = new UserPrefs(userPrefs);
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

}
