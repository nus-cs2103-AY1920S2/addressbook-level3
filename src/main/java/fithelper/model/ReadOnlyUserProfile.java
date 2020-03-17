package fithelper.model;

import fithelper.model.profile.Profile;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a log book
 */
public interface ReadOnlyUserProfile {

    /**
     * Returns an unmodifiable view of the user profile.
     */
    ObservableList<Profile> getProfile();
}

