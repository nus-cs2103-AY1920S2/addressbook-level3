package seedu.address.model;

import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

import static java.util.Objects.requireNonNull;

public class ProfileManager {

    private ProfileList profileList;

    public ProfileManager(ProfileList profileList) {
        requireNonNull(profileList);
        this.profileList = profileList;
    }

    public ProfileManager() {
        this(new ProfileList());
    }

    public boolean hasProfile(Name name) {
        return profileList.hasProfileWithName(name);
    }

    public Profile getProfile(Name name) {
        return profileList.getProfileWithName(name);
    }
}