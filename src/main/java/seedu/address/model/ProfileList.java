package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

/**
 * Creates a new ProfileList object which contains Profile objects.
 */
public class ProfileList {

    private List<Profile> profileList = new ArrayList<>();

    public ProfileList() {};

    public void addProfile(Profile profile) {
        this.profileList.add(profile);
    }

    public List<Profile> getProfileList() {
        return profileList; // TODO: Implement read-only version of profileList, similar to address book
    }

    public boolean hasProfileWithName(Name name) {
        return this.profileList.stream().map(Profile::getName).anyMatch(x->x.equals(name));
    }

    public Profile getProfileWithName(Name name) {
        Optional<Profile> p = this.profileList.stream().filter(x->x.getName().equals(name)).findFirst();
        if (!p.isPresent()) {
            throw new NoSuchElementException("None of the profiles contains the name " + name.toString());
        }
        return p.get();
    }
}
