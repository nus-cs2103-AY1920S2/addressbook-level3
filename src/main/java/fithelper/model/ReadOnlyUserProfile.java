package fithelper.model;

import fithelper.model.profile.Profile;

/**
 * Unmodifiable view of a user profile.
 */
public interface ReadOnlyUserProfile {

    /**
     * Returns an unmodifiable view of the user profile.
     */
    Profile getUserProfile();
}

