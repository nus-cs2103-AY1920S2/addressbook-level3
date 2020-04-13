package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Represents a unique list of all groups currently in CoderLifeInsights
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks whether the list contains a given group.
     *
     * @param toCheck a group to check
     * @return boolean status
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent groupList as the given argument.
     */
    public boolean containsGroups(List<Group> toCheck) {
        requireNonNull(toCheck);
        boolean groupExists;
        for (Group oneGroup : toCheck) {
            groupExists = internalList.stream().anyMatch(oneGroup::equals);
            if (groupExists) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds a given group to the list.
     *
     * @param toAdd group to be added
     */
    public void addGroup(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds given groups to the list
     *
     * @param toAdd groups to be added
     */
    public void addAll(List<Group> toAdd) {
        requireNonNull(toAdd);
        if (containsGroups(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.addAll(toAdd);
    }

    public void setGroup(Group target, Group edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.equals(edited) && contains(edited)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, edited);
    }

    public void setGroups(List<Group> groups) {
        requireNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }
        internalList.setAll(groups);
    }

    public void setGroups(UniqueGroupList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes a given group from the list.
     *
     * @param toRemove group to be removed.
     */
    public void removeGroup(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contains only unique persons.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).equals(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
