package seedu.address.model.diary;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * dummy javadocs
 */
public class DiaryBook {
    private List<DiaryEntry> diaryEntries;
    private final ObservableList<DiaryEntry> internalList = FXCollections.observableArrayList();

    public DiaryBook() {
        this.diaryEntries = new ArrayList<>();
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

    /*
    public void addEntry(DiaryEntry diaryEntry) {
        this.internalList.add(diaryEntry);
    }
    */
    public void addEntry(DiaryEntry diaryEntry) {
        diaryEntries.add(diaryEntry);
    }
}
