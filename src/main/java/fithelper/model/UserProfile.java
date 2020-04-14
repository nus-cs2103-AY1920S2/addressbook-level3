package fithelper.model;

import static java.util.Objects.requireNonNull;

import fithelper.model.profile.Profile;

/**
 * Wraps all profile-related data at the UserProfile level
 */
public class UserProfile implements ReadOnlyUserProfile {

    private Profile profile = new Profile();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserProfile() {}

    /**
     * Creates a {@code UserProfile} using the value in the {@code toBeCopied}
     */
    public UserProfile(ReadOnlyUserProfile toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code UserProfile} with {@code newUserProfile}.
     */
    public void resetData(ReadOnlyUserProfile newUserProfile) {
        requireNonNull(newUserProfile);
        setUserProfile(newUserProfile.getUserProfile());
    }

    public Profile getUserProfile() {
        return profile;
    }

    public void setUserProfile(Profile profile) {
        requireNonNull(profile);
        this.profile = profile;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Profile: ")
                .append(this.profile.toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserProfile // instanceof handles nulls
                && profile.equals(((UserProfile) other).profile));
    }

    @Override
    public int hashCode() {
        return this.profile.hashCode();
    }
}
