package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProfileList;
import seedu.address.model.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class JsonSerializableProfileList {
    public static final String MESSAGE_DUPLICATE_PROFILE = "Profile list contains a profile with the same name.";

    private List<JsonProfile> profiles = new ArrayList<>();

    @JsonCreator
    public JsonSerializableProfileList(@JsonProperty("profiles") List<JsonProfile> profiles) {
        this.profiles.addAll(profiles);
    }

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
