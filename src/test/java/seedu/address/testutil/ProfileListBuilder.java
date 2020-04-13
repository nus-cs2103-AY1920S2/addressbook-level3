package seedu.address.testutil;

import seedu.address.model.ProfileList;
import seedu.address.model.profile.Profile;

//@@author gyant6

/**
 * A utility class to help with building ProfileList objects.
 * Example usage: <br>
 *     {@code ProfileList pl = new ProfileListBuilder().withPerson("John", "Doe").build();}
 */
public class ProfileListBuilder {

    private ProfileList profileList;

    public ProfileListBuilder() {
        profileList = new ProfileList();
    }

    public ProfileListBuilder(ProfileList profileList) {
        this.profileList = profileList;
    }

    /**
     * Adds a new {@code Profile} to the {@code ProfileList} that we are building.
     */
    public ProfileListBuilder withProfile(Profile profile) {
        profileList.addProfile(profile);
        return this;
    }

    public ProfileList build() {
        return profileList;
    }
}
