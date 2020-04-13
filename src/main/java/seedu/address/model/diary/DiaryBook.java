package seedu.address.model.diary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.diary.mood.HappyMood;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.weather.CloudyWeather;
import seedu.address.model.diary.weather.Weather;

/**
 * Represents a diary book that manages a list of diary entries.
 */
public class DiaryBook {
    private static List<DiaryEntry> diaryEntries;
    private static ObservableList<DiaryEntry> internalList;

    public DiaryBook() {
        this.diaryEntries = new ArrayList<>();
        DiaryEntry dummyDiary = new DiaryEntry(LocalDate.now(),
                new CloudyWeather(),
                new HappyMood(),
                "First dummy entry");
        diaryEntries.add(dummyDiary);
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
     * Shows the logs of the recorded diary entries.
     *
     * @return String message representing the log messages.
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
        return diaryEntries.get(calibrateIndex(entryId)).toString();
    }

    /*
    public void addEntry(DiaryEntry diaryEntry) {
        internalList.add(diaryEntry);
    }
    */
    public static ObservableList<DiaryEntry> getInternalList() {
        return internalList;
    }

    public void addEntry(DiaryEntry diaryEntry) {
        diaryEntries.add(diaryEntry);
    }

    public void deleteEntry(int entryId) {
        diaryEntries.remove(calibrateIndex(entryId));
    }

    public void tagWeather(int entryId, Weather weather) {
        diaryEntries.get(calibrateIndex(entryId)).setWeather(weather);
    }

    public void tagMood(int entryId, Mood mood) {
        diaryEntries.get(calibrateIndex(entryId)).setMood(mood);
    }

    public DiaryEntry getDiaryEntryById(int entryId) {
        return diaryEntries.get(calibrateIndex(entryId));
    }

    public List<Integer> getListOfIdsByDate(LocalDate date) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= diaryEntries.size(); i++) {
            if (getDiaryEntryById(i).getDate().equals(date)) {
                ids.add(i);
            }
        }
        return ids;
    }

    public boolean isExistingDate(LocalDate date) {
        return getListOfIdsByDate(date).size() != 0;
    }


    public List<DiaryEntry> getDiaryEntries() {
        return this.diaryEntries;
    }


    private int calibrateIndex(int index) {
        return index - 1;
    }
}
