package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class JsonSerializableProfileList {

    private List<JsonProfile> profiles = new ArrayList<>();

    @JsonCreator
    public JsonSerializableProfileList(@JsonProperty("profiles") List<JsonProfile> profiles) {
        this.profiles.addAll(profiles);
    }

    // To edit, probably make a ProfileList if we're dealing with multiple profiles
    // For now, I'll assume there's just one Profile and return just one Profile object
    public Profile toModelType() throws IllegalValueException {
        return this.profiles.get(0).toModelType();
    }
}
