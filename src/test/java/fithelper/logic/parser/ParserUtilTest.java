package fithelper.logic.parser;

import static fithelper.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static fithelper.testutil.AssertUtil.assertThrows;
import static fithelper.testutil.TypicalIndexesUtil.INDEX_FIRST_ENTRY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Time;

public class ParserUtilTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_TIME = "12:90";
    private static final String INVALID_LOCATION = "";
    private static final String INVALID_CALORIE = "calorie";

    private static final String VALID_NAME = "Fries";
    private static final String VALID_TIME = "2020-04-11-00:58";
    private static final String VALID_LOCATION = "123 Main Street #0505";
    private static final String VALID_CALORIE = "500";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndexInvalidInputThrowsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndexOutOfRangeInputThrowsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndexValidInputSuccess() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ENTRY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ENTRY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseNameNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseNameInvalidValueThrowsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseNameValidValueWithoutWhitespaceReturnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseNameValidValueWithWhitespaceReturnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTimeNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTimeInvalidValueThrowsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTimeValidValueWithoutWhitespaceReturnsTime() throws Exception {
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseTimeValidValueWithWhitespaceReturnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseCalorieNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCalorie((String) null));
    }

    @Test
    public void parseCalorieInvalidValueThrowsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCalorie(INVALID_CALORIE));
    }

    @Test
    public void parseCalorieValidValueWithoutWhitespaceReturnsCalorie() throws Exception {
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(VALID_CALORIE));
    }

    @Test
    public void parseCalorieValidValueWithWhitespaceReturnsTrimmedCalorie() throws Exception {
        String calorieWithWhitespace = WHITESPACE + VALID_CALORIE + WHITESPACE;
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(calorieWithWhitespace));
    }

    @Test
    public void parseLocationNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocationInvalidValueThrowsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parseLocationValidValueWithoutWhitespaceReturnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocationValidValueWithWhitespaceReturnsTrimmedLocation() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(locationWithWhitespace));
    }
}

