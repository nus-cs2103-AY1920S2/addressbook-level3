package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.mood.CalmMood;
import seedu.address.model.diary.mood.HappyMood;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.mood.SadMood;
import seedu.address.model.diary.mood.StressedMood;

public class MoodParser {
    private static HashMap<String, Mood> MOOD_IDENTIFIERS = new HashMap<>();

    static {
        MOOD_IDENTIFIERS.put("calm", new CalmMood());
        MOOD_IDENTIFIERS.put("happy", new HappyMood());
        MOOD_IDENTIFIERS.put("sad", new SadMood());
        MOOD_IDENTIFIERS.put("stressed", new StressedMood());
    }

    public Mood parseMood(String moodString) throws IllegalValueException {
        if (MOOD_IDENTIFIERS.get(moodString) == null) {
            throw new IllegalValueException("Please enter a valid mood!");
        }
        return MOOD_IDENTIFIERS.get(moodString);
    }
}
