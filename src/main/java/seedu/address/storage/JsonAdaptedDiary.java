package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryEntry;


/**
 * Jackson-friendly version of {@link DiaryEntry}.
 */
class JsonAdaptedDiary {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

    private final String entryContent;
    private final String date;
    private final String weather;
    private final String mood;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDiary(@JsonProperty("entry_content") String entryContent, @JsonProperty("date") String date,
                            @JsonProperty("weather") String weather,
                            @JsonProperty("mood") String mood) {
        this.entryContent = entryContent;
        this.date = date;
        this.weather = weather;
        this.mood = mood;
    }


    /**
     * Dummy java docs.
     * @param source param
     */
    public JsonAdaptedDiary(DiaryEntry source) {
        entryContent = source.getEntryContent();
        date = source.getDate().toString();
        weather = source.getWeather().toString();
        mood = source.getMood().toString();
    }

    /**
     * Converts this Jackson-friendly adapted DiaryEntry object into the model's {@code DiaryEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public DiaryEntry toModelType() throws IllegalValueException {

        return new DiaryEntry(entryContent);
    }

}
