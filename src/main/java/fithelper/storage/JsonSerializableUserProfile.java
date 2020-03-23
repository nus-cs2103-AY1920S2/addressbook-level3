package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.UserProfile;

/**
 * An Immutable UserProfile that is serializable to JSON format.
 */
@JsonRootName(value = "userprofile")
class JsonSerializableUserProfile {

    private final JsonAdaptedProfile profile;

    /**
     * Constructs a {@code JsonSerializableUserProfile} with the given Profile.
     */
    @JsonCreator
    public JsonSerializableUserProfile(@JsonProperty("profile") JsonAdaptedProfile profile) {
        this.profile = profile;
    }

    /**
     * Converts a given {@code ReadOnlyUserProfile} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
     */
    public JsonSerializableUserProfile(ReadOnlyUserProfile source) {
        this.profile = new JsonAdaptedProfile(source.getUserProfile());
    }

    /**
     * Converts this profile into the model's {@code UserProfile} object.
     */
    public UserProfile toModelType() throws IllegalValueException {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserProfile(this.profile.toModelType());
        return userProfile;
    }

}
