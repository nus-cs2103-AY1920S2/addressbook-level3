package team.easytravel.storage.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_PEAK;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.storage.JsonAdaptedTag;

class JsonAdaptedActivityTest {

    public static final String INVALID_TITLE = "T!T!@#E";
    public static final Integer INVALID_DURATION = -10;
    public static final String INVALID_LOCATION = "M)&SC&W!";
    public static final String INVALID_TAG = "#activity";
    public static final String INVALID_DATE_TIME = "21qwe-03-21321";

    public static final String VALID_TITLE = ACTIVITY_PEAK.getTitle().value;
    public static final Integer VALID_DURATION = ACTIVITY_PEAK.getDuration().value;
    public static final String VALID_LOCATION = ACTIVITY_PEAK.getLocation().value;
    public static final String VALID_DATE_TIME = ACTIVITY_PEAK.getScheduledDateTime().toString();
    public static final List<JsonAdaptedTag> VALID_TAGS = ACTIVITY_PEAK.getTags().stream()
            .map(JsonAdaptedTag::new).collect(Collectors.toList());

    @Test
    public void toModelType_validActivityDetails_returnsActivity() throws Exception {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(ACTIVITY_PEAK);
        assertEquals(ACTIVITY_PEAK, activity.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(INVALID_TITLE, VALID_DURATION, VALID_LOCATION, VALID_DATE_TIME,
                        VALID_TAGS);
        String exceptedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, exceptedMessage, activity::toModelType);
    }


    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_TITLE, INVALID_DURATION, VALID_LOCATION, VALID_DATE_TIME,
                        VALID_TAGS);
        String expectedMessage = Duration.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }


    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_TITLE, VALID_DURATION, INVALID_LOCATION, VALID_DATE_TIME,
                        VALID_TAGS);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }


    @Test
    public void toModelType_invalidDateTime_throwsIllegalArgumentException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_TITLE, VALID_DURATION, VALID_LOCATION, INVALID_DATE_TIME,
                        VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_TITLE, VALID_DURATION, VALID_LOCATION, VALID_DATE_TIME,
                        invalidTags);
        assertThrows(IllegalValueException.class, activity::toModelType);

    }


}
