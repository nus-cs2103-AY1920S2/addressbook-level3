package seedu.eylah.diettracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.diettracker.storage.JsonAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.eylah.diettracker.testutil.TypicalFood.PASTA;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Name;

public class JsonAdaptedFoodTest {
    private static final String INVALID_NAME = "P@sta";
    private static final long INVALID_CALORIES = -10;
    private static final String INVALID_TAG = "#favorite";

    private static final String VALID_NAME = PASTA.getName().toString();
    private static final long VALID_CALORIES = PASTA.getCalories().getValue();
    private static final String VALID_DATE = PASTA.getDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = PASTA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFoodDetails_returnsFood() throws Exception {
        JsonAdaptedFood food = new JsonAdaptedFood(PASTA);

        // check if all fields the same
        assertEquals(PASTA.getName(), food.toModelType().getName());
        assertEquals(PASTA.getCalories(), food.toModelType().getCalories());
        assertEquals(PASTA.getDate().toString(), food.toModelType().getDate().toString());
        assertEquals(PASTA.getTags(), food.toModelType().getTags());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(INVALID_NAME, VALID_CALORIES, VALID_DATE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(null, VALID_CALORIES, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidCalories_throwsIllegalArgumentException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, INVALID_CALORIES, VALID_DATE, VALID_TAGS);
        String expectedMessage = Calories.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_CALORIES, VALID_DATE, invalidTags);
        assertThrows(IllegalValueException.class, food::toModelType);
    }

}
