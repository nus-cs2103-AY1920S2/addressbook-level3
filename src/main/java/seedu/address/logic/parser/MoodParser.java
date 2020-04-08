package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.mood.CalmMood;
import seedu.address.model.diary.mood.HappyMood;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.mood.SadMood;
import seedu.address.model.diary.mood.StressedMood;

/**
 * Dummy java docs.
 */
public class MoodParser {
    private static HashMap<String, Mood> moodParser = new HashMap<>();

    static {
        moodParser.put("calm", new CalmMood());
        moodParser.put("happy", new HappyMood());
        moodParser.put("sad", new SadMood());
        moodParser.put("stressed", new StressedMood());
    }

    /**
     * Dummy java docs.
     * @param moodString
     * @return
     * @throws IllegalValueException
     */
    public Mood parseMood(String moodString) throws IllegalValueException {
        if (moodParser.get(moodString) == null) {
            throw new IllegalValueException("Please enter a valid mood!");
        }
        return moodParser.get(moodString);
    }
}
