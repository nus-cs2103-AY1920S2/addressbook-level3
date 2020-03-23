package seedu.address.model.diary;

import java.util.ArrayList;
import java.util.List;


public class DiaryBook {
    private List<DiaryEntry> diaryEntries;

    public DiaryBook() {
        this.diaryEntries = new ArrayList<>();
    }

    public DiaryBook(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    public void addEntry(DiaryEntry diaryEntry) {
        this.diaryEntries.add(diaryEntry);
    }

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
}
