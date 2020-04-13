package seedu.address.model.mod;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.mod.exceptions.DuplicateModException;
import seedu.address.model.mod.exceptions.ModNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Mod#equals(Object)}. As such, adding, updating and
 * removal of module uses Mod#equals(Object) so as to ensure that the module being added, updated or removed
 * is unique in terms of identity in the UniqueModList.
 *
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.mod.Mod#equals(Object)
 */

public class UniqueModList implements Iterable<Mod> {

    private final ObservableList<Mod> internalList = FXCollections.observableArrayList();
    private final ObservableList<Mod> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Mod toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMod);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Mod toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(Mod toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModNotFoundException();
        }
    }

    /**
     * Replaces the student {@code target} in the list with {@code editedMod}.
     * {@code target} must exist in the list.
     * The student identity of {@code editedMod} must not be the same as another existing student in the list.
     */
    public void setMod(Mod target, Mod editedMod) {
        requireAllNonNull(target, editedMod);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModNotFoundException();
        }

        if (!target.isSameMod(editedMod) && contains(editedMod)) {
            throw new DuplicateModException();
        }

        internalList.set(index, editedMod);
    }

    public void setMods(UniqueModList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code mods}.
     * {@code mods} must not contain duplicate modules.
     */
    public void setMods(List<Mod> mods) {
        requireAllNonNull(mods);
        if (!modsAreUnique(mods)) {
            throw new DuplicateModException();
        }

        internalList.setAll(mods);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Mod> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Mod> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.mod.UniqueModList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.mod.UniqueModList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code mods} contains only unique modules.
     */
    private boolean modsAreUnique(List<Mod> mods) {
        for (int i = 0; i < mods.size() - 1; i++) {
            for (int j = i + 1; j < mods.size(); j++) {
                if (mods.get(i).equals(mods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a list of all mods.
     */
    public ObservableList<Mod> getAllMods() {
        return this.internalList;
    }
}
