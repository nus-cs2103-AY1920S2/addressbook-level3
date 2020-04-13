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
    public void parse_indexInvalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parse_indexOutOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parse_indexValidInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ENTRY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ENTRY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parse_nameNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parse_nameInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parse_nameValidValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parse_nameValidValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parse_timeNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parse_timeInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parse_timeValidValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parse_timeValidValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parse_calorieNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCalorie((String) null));
    }

    @Test
    public void parse_calorieInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCalorie(INVALID_CALORIE));
    }

    @Test
    public void parse_calorieValidValueWithoutWhitespace_returnsCalorie() throws Exception {
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(VALID_CALORIE));
    }

    @Test
    public void parse_calorieValidValueWithWhitespace_returnsTrimmedCalorie() throws Exception {
        String calorieWithWhitespace = WHITESPACE + VALID_CALORIE + WHITESPACE;
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(calorieWithWhitespace));
    }

    @Test
    public void parse_locationNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parse_locationInvalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parse_locationValidValueWithoutWhitespace_returnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parse_locationValidValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(locationWithWhitespace));
    }
}

