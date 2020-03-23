package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.UniqueProfileList;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * Creates a new ProfileList object which contains Profile objects.
 */
public class ProfileList {

    private final UniqueProfileList profiles;

    {
        profiles = new UniqueProfileList();
    }

    public ProfileList() {};

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

//    public List<Profile> getProfileList() {
//        return profileList; // TODO: Implement read-only version of profileList, similar to address book
//    }

    public ObservableList<Profile> getProfileList() {
        return profiles.asUnmodifiableObservableList();
    }


    /**
     * Returns true if a person with the same identity as {@code profile} exists in the profile list.
     */
    public boolean hasPerson(Profile profile) {
        requireNonNull(profile);
        return profiles.contains(profile);
    }

    /**
     * Resets the existing data of this {@code ProfileList} with {@code newData}.
     */
    public void resetData(ProfileList profileList) {
        requireNonNull(profileList);

        setProfiles(profileList.getProfileList());
    }

    /**
     * Removes {@code key} from this {@code ProfileList}.
     * {@code key} must exist in the profile list.
     */
    public void deleteProfile(Profile profile) {
        profiles.remove(profile);
    }

    /**
     * Replaces the profile {@code target} in the list with {@code editedProfile}.
     * {@code target} must exist in the list.
     * The profile identity of {@code editedProfile} must not be the same as another existing profile in the list.
     */
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);
        profiles.setProfile(target, editedProfile);
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles.setProfiles(profiles);
    }

    public boolean hasProfileWithName(Name name) {
        return this.profiles.hasProfileWithName(name);
    }

    public Profile getProfileWithName(Name name) {
        return profiles.getProfileWithName(name);
    }

    /**
     * Returns true if {@code profiles} contains only unique profiles.
     */
    private boolean personsAreUnique(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 1; i++) {
            for (int j = i + 1; j < profiles.size(); j++) {
                if (profiles.get(i).isSameProfile(profiles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
