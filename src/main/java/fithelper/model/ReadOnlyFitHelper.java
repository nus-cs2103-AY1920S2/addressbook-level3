package fithelper.model;

import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;
import fithelper.model.entry.VeventList;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a log book
 */
public interface ReadOnlyFitHelper {

    /**
     * Returns an unmodifiable view of the diary list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Diary> getDiaryList();

    /**
     * Returns an unmodifiable view of the veventlist.
     * This list will not contain any duplicate entries.
     */
    VeventList getVeventList();

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getFoodList();

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getSportsList();

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getReminderList();
}

