package seedu.address.model.diary;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * dummy javadocs
 */
public class DiaryBook {
    private static List<DiaryEntry> diaryEntries;
    private static ObservableList<DiaryEntry> internalList;

    public DiaryBook() {
        this.diaryEntries = new ArrayList<>();
        internalList = FXCollections.observableList(diaryEntries);
    }

    public DiaryBook(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    public void setDiary(ObservableList<DiaryEntry> diaryEntry) {
        this.internalList.addAll(diaryEntry);
    }

    public ObservableList<DiaryEntry> getObservableList() {
        return this.internalList;
    }

    /**
     * Dummy java docs
     *
     * @return
     */
    public String showLog() {
        if (diaryEntries.size() == 0) {
            return "There are currently no entries in your diary book!";
        }
        int entryId = 1;
        StringBuilder sb = new StringBuilder();
        for (DiaryEntry entry : diaryEntries) {
            sb.append(entryId++).append(". ").append(entry.getHeading()).append("\n");
        }
        return sb.toString();
    }

    public String showEntry(int entryId) {
        return diaryEntries.get(entryId - 1).toString();
    }

    public void addEntry(DiaryEntry diaryEntry) {
        internalList.add(diaryEntry);
    }

    public static ObservableList<DiaryEntry> getInternalList() {
        return internalList;
    }
}
