package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * A list of Profiles that enforces uniqueness between its elements and does not allow nulls.
 * A Profile is considered unique by comparing using {@code Profile#isSameProfile(Profile)}.
 * As such, adding and updating of Profiles uses Profile#isSameProfile(Profile) for equality so as
 * to ensure that the Profile being added or updated is unique in terms of identity in the UniqueProfileList.
 * However, the removal of a Profile uses Profile#equals(Object) so
 * as to ensure that the Profile with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Profile#isSameProfile(Profile)
 */
public class UniqueProfileList implements Iterable<Profile> {

    private final ObservableList<Profile> internalList = FXCollections.observableArrayList();
    private final ObservableList<Profile> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Profile as the given argument.
     */
    public boolean contains(Profile toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProfile);
    }

    /**
     * Adds a Profile to the list.
     * The Profile must not already exist in the list.
     */
    public void add(Profile toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Profile {@code target} in the list with {@code editedProfile}.
     * {@code target} must exist in the list.
     * The Profile identity of {@code editedProfile} must not be the same as another existing Profile in the list.
     */
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameProfile(editedProfile) && contains(editedProfile)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedProfile);
    }

    /**
     * Removes the equivalent Profile from the list.
     * The Profile must exist in the list.
     */
    public void remove(Profile toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setProfiles(UniqueProfileList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Profiles}.
     * {@code Profiles} must not contain duplicate Profiles.
     */
    public void setProfiles(List<Profile> profiles) {
        requireAllNonNull(profiles);
        if (!profilesAreUnique(profiles)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(profiles);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Profile> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Profile> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProfileList // instanceof handles nulls
                && internalList.equals(((UniqueProfileList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Profiles} contains only unique Profiles.
     */
    private boolean profilesAreUnique(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 1; i++) {
            for (int j = i + 1; j < profiles.size(); j++) {
                if (profiles.get(i).isSameProfile(profiles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasProfileWithName(Name name) {
        return internalList.stream().map(Profile::getName).anyMatch(x->x.equals(name));
    }

    public Profile getProfileWithName(Name name) {
        Optional<Profile> p = internalList.stream().filter(x->x.getName().equals(name)).findFirst();
        if (!p.isPresent()) {
            throw new NoSuchElementException("None of the profiles contains the name " + name.toString());
        }
        return p.get();
    }
}
