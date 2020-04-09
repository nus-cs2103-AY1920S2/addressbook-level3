package fithelper.model.diary;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.diary.DeleteDiaryCommand;
import fithelper.model.diary.exceptions.DiaryNotFoundException;
import fithelper.model.diary.exceptions.DuplicateDiaryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of diaries that enforces uniqueness between its elements and does not allow nulls.
 * An diary is considered unique by comparing using {@code Entry#isSameEntry(diary)}. As such, adding and updating of
 * diaries uses Entry#isSameEntry(diary) for equality so as to ensure that the diary being added or updated is
 * unique in terms of identity in the UniqueEntryList. However, the removal of an diary uses Entry#equals(Object) so
 * as to ensure that the diary with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Diary#isSameDiary(Diary)
 */
public class UniqueDiaryList implements Iterable<Diary> {

    private static final Logger logger = LogsCenter.getLogger(DeleteDiaryCommand.class);

    private final ObservableList<Diary> internalList = FXCollections.observableArrayList();
    private final ObservableList<Diary> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent diary as the given argument.
     */
    public boolean contains(Diary toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDiary);
    }

    /**
     * Returns true if the list contains an equivalent diary as the given argument.
     */
    public boolean containsDate(String toCheck) {
        requireNonNull(toCheck);
        for (Diary diary: internalList) {
            if (diary.getDiaryDate().toString().equalsIgnoreCase(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an diary to the list.
     * The diary must not already exist in the list.
     */
    public void add(Diary toAdd) {
        requireNonNull(toAdd);
        if (containsDate(toAdd.getDiaryDate().toString())) {
            int oldIndex = getIndex(internalList, toAdd.getDiaryDate().toString());
            internalList.set(oldIndex, toAdd);
        } else {
            internalList.add(toAdd);
        }
    }

    public int getIndex(ObservableList<Diary> internalList, String dateStr) {
        requireNonNull(dateStr);
        List<Diary> tempList = internalList.stream().collect(Collectors.toList());
        for (int i = 0; i < tempList.size(); i++) {
            Diary thisDiary = tempList.get(i);
            if (thisDiary.getDiaryDate().toString().equalsIgnoreCase(dateStr)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Replaces the diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in the list.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the list.
     */
    public void setDiary(Diary target, Diary editedDiary) {
        requireAllNonNull(target, editedDiary);

        if (!target.isSameDiary(editedDiary) && contains(editedDiary)) {
            throw new DuplicateDiaryException();
        }

        internalList.remove(target);
        internalList.add(editedDiary);
    }

    /**
     * Replaces the contents of this list with {@code diaries}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setDiaries(List<Diary> diaries) {
        requireAllNonNull(diaries);
        if (!diariesAreUnique(diaries)) {
            throw new DuplicateDiaryException();
        }
        internalList.setAll(diaries);
    }

    public void setDiaries(UniqueDiaryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent diary from the list.
     * The diary must exist in the list.
     */
    public void remove(Diary toRemove) {
        requireNonNull(toRemove);
        logger.info ("UniqueDiary List: " + toRemove.toString());
        logger.info (internalList.toString());
        if (!internalList.remove(toRemove)) {
            throw new DiaryNotFoundException();
        }
    }

    /**
     * Removes a diary whose date is in string representation of dateStr
     * @param dateStr string representation of diary date
     *//*
    public void remove(String dateStr) {
        requireNonNull(dateStr);
        int oldIndex = 0;
        List<Diary> tempList = internalList.stream().collect(Collectors.toList());
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getDiaryDate().toString().equalsIgnoreCase(dateStr)) {
                oldIndex = i;
                break;
            }
        }
        Diary toRemove = internalList.get(oldIndex);
        internalList.remove(toRemove);
    }*/

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Diary> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Diary> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDiaryList // instanceof handles nulls
                && internalList.equals(((UniqueDiaryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code diaries} contains only unique diaries.
     */
    private boolean diariesAreUnique(List<Diary> diaries) {
        for (int i = 0; i < diaries.size() - 1; i++) {
            for (int j = i + 1; j < diaries.size(); j++) {
                if (diaries.get(i).isSameDiary(diaries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
