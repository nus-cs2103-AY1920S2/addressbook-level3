package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.UserProfile;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.profile.Profile;

/**
 * An Immutable UserProfile that is serializable to JSON format.
 */
@JsonRootName(value = "userprofile")
class JsonSerializableUserProfile {

    private Profile profile;

    /**
     * Constructs a {@code JsonSerializableUserProfile} with the given Profile.
     */
    @JsonCreator
    public JsonSerializableUserProfile(@JsonProperty("profile") Profile profile) {
        this.profile = profile;
    }

    /**
     * Converts a given {@code ReadOnlyUserProfile} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
     */
    public JsonSerializableUserProfile(ReadOnlyUserProfile source) {
        this.profile = source.getUserProfile();
    }

    /**
     * Converts this profile into the model's {@code UserProfile} object.
     */
    public UserProfile toModelType() {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserProfile(this.profile);
        return userProfile;
    }

}
