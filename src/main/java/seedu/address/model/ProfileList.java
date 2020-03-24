package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * Creates a new ProfileList object which contains Profile objects.
 */
public class ProfileList {

    private final ObservableList<Profile> profileList = FXCollections.observableArrayList();

    public ProfileList() {};

    /**
     * Returns true if the list contains an equivalent profile as the given argument.
     */
    public boolean contains(Profile toCheck) {
        requireNonNull(toCheck);
        return profileList.stream().anyMatch(toCheck::isSameProfile);
    }

    /**
     * Resets the existing data of this {@code ProfileList} with {@code newData}.
     */
    public void resetData(ProfileList profileList) {
        requireNonNull(profileList);

        setProfiles(profileList.getProfileList());
    }

    /**
     * Add {@code profile} to {@code ProfileList}.
     * {@code profile} must not exist in the profile list.
     */
    public void addProfile(Profile profile) {
        requireNonNull(profile);
        if (contains(profile)) {
            throw new DuplicatePersonException();
        }
        this.profileList.add(profile);
    }

    public ObservableList<Profile> getProfileList() {
        return profileList; // TODO: Implement read-only version of profileList, similar to address book
    }

    /**
     * Removes {@code key} from this {@code ProfileList}.
     * {@code key} must exist in the profile list.
     */
    public void deleteProfile(Profile profile) {
        requireNonNull(profile);
        if (!profileList.remove(profile)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Replaces the profile {@code target} in the list with {@code editedProfile}.
     * {@code target} must exist in the list.
     * The profile identity of {@code editedProfile} must not be the same as another existing profile in the list.
     */
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);

        int index = profileList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameProfile(editedProfile) && contains(editedProfile)) {
            throw new DuplicatePersonException();
        }

        profileList.set(index, editedProfile);
    }

    public void setProfiles(List<Profile> profiles) {
        requireAllNonNull(profiles);
        if (!personsAreUnique(profiles)) {
            throw new DuplicatePersonException();
        }

        profileList.setAll(profiles);
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

    public DeadlineList getDeadlineList() {
        return new DeadlineList(this.getProfileList().get(0).getDeadlines());
    }
}
