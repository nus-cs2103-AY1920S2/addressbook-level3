package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProfileList;
import seedu.address.model.profile.Profile;

//@@author chanckben
/**
 * An Immutable ProfileList that is serializable to JSON format.
 */
public class JsonSerializableProfileList {
    public static final String MESSAGE_DUPLICATE_PROFILE = "Profile list contains a profile with the same name.";

    private List<JsonProfile> profiles = new ArrayList<>();

    @JsonCreator
    public JsonSerializableProfileList(@JsonProperty("profiles") List<JsonProfile> profiles) {
        this.profiles.addAll(profiles);
    }

    public JsonSerializableProfileList(ProfileList profileList) {
        profiles.addAll(profileList.getProfileList().stream().map(JsonProfile::new).collect(Collectors.toList()));
    }

    /**
     * Converts this module list into a {@code ProfileList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProfileList toModelType() throws IllegalValueException {
        ProfileList profileList = new ProfileList();
        for (JsonProfile jsonProfile: profiles) {
            Profile profile = jsonProfile.toModelType();
            if (profileList.hasProfileWithName(profile.getName())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROFILE);
            }
            profileList.addProfile(profile);
        }
        return profileList;
    }
}
