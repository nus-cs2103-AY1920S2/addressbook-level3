package seedu.address.model.diary;

import java.util.Optional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.weather.Weather;

public class DiaryEntry {
    private static int NUM_OF_ENTRIES = 0;
    private int entryId;
    private LocalDate date;
    private Optional<Weather> weather;
    private Optional<Mood> mood;
    private String entryContent;

    private DiaryEntry(int entryId, LocalDate date, Weather weather, Mood mood, String entryContent) {
        this.entryId = entryId;
        this.date = date;
        this.weather = Optional.ofNullable(weather);
        this.mood = Optional.ofNullable(mood);
        this.entryContent = entryContent;
    }

    public DiaryEntry(String entryContent) {
        this(NUM_OF_ENTRIES + 1, LocalDate.now(), null, null, entryContent);
        NUM_OF_ENTRIES++;
    }

    @Override
    public String toString() {
        String dairyDisplay = "";
        dairyDisplay += " DATE: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + "\n";
        dairyDisplay += "WEATHER: " + (weather.isPresent() ? weather.get().toString() : "N.A.") + "\n";
        dairyDisplay += "MOOD: " + (mood.isPresent() ? mood.get().toString() : "N.A.") + "\n";
        dairyDisplay += "_".repeat(50) + "\n";
        dairyDisplay += entryContent + "\n";
        return dairyDisplay;
    }
}
