//package seedu.address.model.profile;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
//
//import java.util.Iterator;
//import java.util.List;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.address.model.profile.exceptions.DuplicatePersonException;
//import seedu.address.model.profile.exceptions.PersonNotFoundException;
//
///**
// * A list of persons that enforces uniqueness between its elements and does not allow nulls.
// * A profile is considered unique by comparing using {@code Profile#isSamePerson(Profile)}. As such, adding and
// * updating of persons uses Profile#isSamePerson(Profile) for equality so as to ensure that the profile being added
// * or updated is unique in terms of identity in the UniquePersonList. However, the removal of a profile uses
// * Profile#equals(Object) so as to ensure that the profile with exactly the same fields will be removed.
// *
// * Supports a minimal set of list operations.
// *
// * @see Profile#isSameProfile(Profile)
// */
//public class UniquePersonList implements Iterable<Profile> {
//
//    private final ObservableList<Profile> internalList = FXCollections.observableArrayList();
//    private final ObservableList<Profile> internalUnmodifiableList =
//            FXCollections.unmodifiableObservableList(internalList);
//
//    /**
//     * Returns true if the list contains an equivalent profile as the given argument.
//     */
//    public boolean contains(Profile toCheck) {
//        requireNonNull(toCheck);
//        return internalList.stream().anyMatch(toCheck::isSamePerson);
//    }
//
//    /**
//     * Adds a profile to the list.
//     * The profile must not already exist in the list.
//     */
//    public void add(Profile toAdd) {
//        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicatePersonException();
//        }
//        internalList.add(toAdd);
//    }
//
//    /**
//     * Replaces the profile {@code target} in the list with {@code editedProfile}.
//     * {@code target} must exist in the list.
//     * The profile identity of {@code editedProfile} must not be the same as another existing profile in the list.
//     */
//    public void setPerson(Profile target, Profile editedProfile) {
//        requireAllNonNull(target, editedProfile);
//
//        int index = internalList.indexOf(target);
//        if (index == -1) {
//            throw new PersonNotFoundException();
//        }
//
//        if (!target.isSamePerson(editedProfile) && contains(editedProfile)) {
//            throw new DuplicatePersonException();
//        }
//
//        internalList.set(index, editedProfile);
//    }
//
//    /**
//     * Removes the equivalent profile from the list.
//     * The profile must exist in the list.
//     */
//    public void remove(Profile toRemove) {
//        requireNonNull(toRemove);
//        if (!internalList.remove(toRemove)) {
//            throw new PersonNotFoundException();
//        }
//    }
//
//    public void setPersons(UniquePersonList replacement) {
//        requireNonNull(replacement);
//        internalList.setAll(replacement.internalList);
//    }
//
//    /**
//     * Replaces the contents of this list with {@code profiles}.
//     * {@code profiles} must not contain duplicate profiles.
//     */
//    public void setPersons(List<Profile> profiles) {
//        requireAllNonNull(profiles);
//        if (!personsAreUnique(profiles)) {
//            throw new DuplicatePersonException();
//        }
//
//        internalList.setAll(profiles);
//    }
//
//    /**
//     * Returns the backing list as an unmodifiable {@code ObservableList}.
//     */
//    public ObservableList<Profile> asUnmodifiableObservableList() {
//        return internalUnmodifiableList;
//    }
//
//    @Override
//    public Iterator<Profile> iterator() {
//        return internalList.iterator();
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof UniquePersonList // instanceof handles nulls
//                        && internalList.equals(((UniquePersonList) other).internalList));
//    }
//
//    @Override
//    public int hashCode() {
//        return internalList.hashCode();
//    }
//
//    /**
//     * Returns true if {@code profiles} contains only unique profiles.
//     */
//    private boolean personsAreUnique(List<Profile> profiles) {
//        for (int i = 0; i < profiles.size() - 1; i++) {
//            for (int j = i + 1; j < profiles.size(); j++) {
//                if (profiles.get(i).isSameProfile(profiles.get(j))) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//}
