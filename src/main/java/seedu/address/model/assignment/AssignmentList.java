package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of assignments that enforces uniqueness between its elements and does not allow nulls.
 * An assignment is considered unique by comparing using {@code Assignment#isSameAssignment(Assignment)}.
 * As such, adding and updating of assignments uses Assignment#isSameAssignment(Assignment) for equality so as to
 * ensure that the assignment being added or updated is unique in terms of identity in the AssignmentList.
 *
 * @see Assignment#isSameAssignment(Assignment)
 */
public class AssignmentList implements Iterable<Assignment> {

    private final ObservableList<Assignment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Assignment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /*public AssignmentList() {
        this.assignments = new ObservableList<Assignment>() {
            @Override
            public void addListener(ListChangeListener<? super Assignment> listChangeListener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Assignment> listChangeListener) {

            }

            @Override
            public boolean addAll(Assignment... assignments) {
                return false;
            }

            @Override
            public boolean setAll(Assignment... assignments) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Assignment> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Assignment... assignments) {
                return false;
            }

            @Override
            public boolean retainAll(Assignment... assignments) {
                return false;
            }

            @Override
            public void remove(int i, int i1) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Assignment> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(Assignment assignment) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Assignment> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, Collection<? extends Assignment> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Assignment get(int i) {
                return null;
            }

            @Override
            public Assignment set(int i, Assignment assignment) {
                return null;
            }

            @Override
            public void add(int i, Assignment assignment) {

            }

            @Override
            public Assignment remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Assignment> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Assignment> listIterator(int i) {
                return null;
            }

            @Override
            public List<Assignment> subList(int i, int i1) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        };
    }*/

    public void setAssignments(AssignmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setAssignments(List<Assignment> replacement) {
        requireNonNull(replacement);
        if (!assignmentsAreUnique(replacement)) {
            throw new DuplicateAssignmentException();
        }
        internalList.setAll(replacement);
    }

    /**
     * Adds an assignment to the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Removes the equivalent assignment from the list.
     * The assignment must exist in the list.
     */
    public void remove(Assignment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AssignmentNotFoundException();
        }
    }

    /**
     * Sorts the scheduler list as an {@code ObservableList}.
     */
    public void sort(Comparator<Assignment> comparator) {
        FXCollections.sort(internalList, comparator);
        //Comparator<Assignment> titleComparator = new TitleComparator();
        //SortedList<Assignment> sortedList = new SortedList<>(internalList, titleComparator);
        //internalList.setAll(sortedList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assignment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Assignment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentList // instanceof handles nulls
                && internalList.equals(((AssignmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireAllNonNull(target, markedAssignment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }
        if (!target.isSameAssignment(markedAssignment) && contains(markedAssignment)) {
            throw new DuplicatePersonException();
        }


        internalList.set(index, markedAssignment);
    }

    /**
     * Returns true if {@code assignment} contains only unique assignments.
     */
    private boolean assignmentsAreUnique(List<Assignment> assignments) {
        for (int i = 0; i < assignments.size() - 1; i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (assignments.get(i).isSameAssignment(assignments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
