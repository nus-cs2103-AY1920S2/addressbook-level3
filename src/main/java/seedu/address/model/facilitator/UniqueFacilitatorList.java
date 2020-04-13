package seedu.address.model.facilitator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.facilitator.exceptions.DuplicateFacilitatorException;
import seedu.address.model.facilitator.exceptions.FacilitatorNotFoundException;
import seedu.address.model.module.ModuleCode;

/**
 * A list of facilitators that enforces uniqueness between its elements and does not allow nulls.
 * A facilitator is considered unique by comparing using {@code Facilitator#isSameFacilitator(Facilitator)}.
 * As such, adding and updating of facilitators uses Facilitator#isSameFacilitator(Facilitator) for equality so as
 * to ensure that the facilitator being added or updated is unique in terms of identity in the UniqueFacilitatorList.
 * However, the removal of a facilitator uses Facilitator#equals(Object) so as to ensure that the facilitator
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Facilitator#isSameFacilitator(Facilitator)
 */
public class UniqueFacilitatorList implements Iterable<Facilitator> {

    private final ObservableList<Facilitator> internalList = FXCollections.observableArrayList();
    private final ObservableList<Facilitator> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent facilitator as the given argument.
     */
    public boolean contains(Facilitator toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFacilitator);
    }

    /**
     * Adds a facilitator to the list.
     * The facilitator must not already exist in the list.
     */
    public void add(Facilitator toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFacilitatorException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Replaces the facilitator {@code target} in the list with {@code editedFacilitator}.
     * {@code target} must exist in the list.
     * The facilitator identity of {@code editedFacilitator} must not be the same as another existing facilitator
     * in the list.
     */
    public void setFacilitator(Facilitator target, Facilitator editedFacilitator) {
        requireAllNonNull(target, editedFacilitator);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FacilitatorNotFoundException();
        }

        if (!target.isSameFacilitator(editedFacilitator) && contains(editedFacilitator)) {
            throw new DuplicateFacilitatorException();
        }

        internalList.set(index, editedFacilitator);
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Removes the equivalent facilitator from the list.
     * The facilitator must exist in the list.
     */
    public void remove(Facilitator toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FacilitatorNotFoundException();
        }
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Removes the equivalent module code from the list.
     * The module code must exist in the list.
     */
    public void removeModuleCode(final ModuleCode toRemove) {
        requireNonNull(toRemove);
        List<Facilitator> replacementList = new ArrayList<>();
        internalList.stream()
                .filter(facilitator -> {
                    Set<ModuleCode> temp = facilitator.getModuleCodes();
                    return !temp.contains(toRemove) || temp.size() > 1;
                })
                .map(facilitator -> {
                    Set<ModuleCode> moduleCodes = new HashSet<>(facilitator.getModuleCodes());
                    boolean hadToRemove = moduleCodes.remove(toRemove);
                    return hadToRemove
                            ? new Facilitator(facilitator.getName(), facilitator.getPhone(),
                            facilitator.getEmail(), facilitator.getOffice(), moduleCodes)
                            : facilitator;
                })
                .forEach(replacementList::add);

        setFacilitators(replacementList);
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Replaces the module code {@code target} in the list with {@code editedModuleCode}.
     * {@code target} must exist in the list.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in the list.
     */
    public void setModuleCode(ModuleCode target, ModuleCode editedModuleCode) {
        requireAllNonNull(target, editedModuleCode);
        List<Facilitator> facilitatorsToEdit = new ArrayList<>();
        for (Facilitator facilitator : internalList) {
            if (facilitator.getModuleCodes().contains(target)) {
                facilitatorsToEdit.add(facilitator);
            }
        }
        for (Facilitator facilitator : facilitatorsToEdit) {
            Set<ModuleCode> moduleCodes = new HashSet<>(facilitator.getModuleCodes());
            moduleCodes.remove(target);
            moduleCodes.add(editedModuleCode);
            Facilitator editedFacilitator = new Facilitator(facilitator.getName(), facilitator.getPhone(),
                    facilitator.getEmail(), facilitator.getOffice(), moduleCodes);
            setFacilitator(facilitator, editedFacilitator);
        }
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    public void setFacilitators(UniqueFacilitatorList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Replaces the contents of this list with {@code facilitators}.
     * {@code facilitators} must not contain duplicate facilitator.
     */
    public void setFacilitators(List<Facilitator> facilitators) {
        requireAllNonNull(facilitators);
        if (!facilitatorsAreUnique(facilitators)) {
            throw new DuplicateFacilitatorException();
        }

        internalList.setAll(facilitators);
        FXCollections.sort(internalList, new FacilitatorNameComparator());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Facilitator> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the list of facilitators.
     * @return the list of facilitators.
     */
    public List<Facilitator> getFacilitatorList() {
        return internalList;
    }

    @Override
    public Iterator<Facilitator> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFacilitatorList // instanceof handles nulls
                        && internalList.equals(((UniqueFacilitatorList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code facilitators} contains only unique facilitators.
     */
    private boolean facilitatorsAreUnique(List<Facilitator> facilitators) {
        for (int i = 0; i < facilitators.size() - 1; i++) {
            for (int j = i + 1; j < facilitators.size(); j++) {
                if (facilitators.get(i).isSameFacilitator(facilitators.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
