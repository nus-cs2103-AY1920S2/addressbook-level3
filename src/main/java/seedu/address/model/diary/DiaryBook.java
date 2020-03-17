package seedu.address.model.diary;

import java.util.List;
import java.util.ArrayList;

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
        StringBuilder sb = new StringBuilder();
        int entryId = 1;
        for (DiaryEntry entry : diaryEntries) {
            sb.append(entryId++).append(entry.getHeading()).append("\n");
        }
        return sb.toString();
    }

    public String showEntry(int entryId) {
        return diaryEntries.get(entryId - 1).toString();
    }
}
