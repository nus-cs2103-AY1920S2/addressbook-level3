package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.commands.CommandTestUtil.CALORIE_DESC_BURGER;
import static fithelper.logic.commands.CommandTestUtil.CALORIE_DESC_CAKE;
import static fithelper.logic.commands.CommandTestUtil.INVALID_CALORIE_DESC;
import static fithelper.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static fithelper.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static fithelper.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static fithelper.logic.commands.CommandTestUtil.LOCATION_DESC_BURGER;
import static fithelper.logic.commands.CommandTestUtil.LOCATION_DESC_CAKE;
import static fithelper.logic.commands.CommandTestUtil.NAME_DESC_BURGER;
import static fithelper.logic.commands.CommandTestUtil.NAME_DESC_CAKE;
import static fithelper.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static fithelper.logic.commands.CommandTestUtil.TIME_DESC_BURGER;
import static fithelper.logic.commands.CommandTestUtil.TIME_DESC_CAKE;
import static fithelper.logic.commands.CommandTestUtil.TYPE_DESC_FOOD;
import static fithelper.logic.commands.CommandTestUtil.VALID_CALORIE_BURGER;
import static fithelper.logic.commands.CommandTestUtil.VALID_LOCATION_BURGER;
import static fithelper.logic.commands.CommandTestUtil.VALID_NAME_BURGER;
import static fithelper.logic.commands.CommandTestUtil.VALID_TIME_BURGER;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static fithelper.testutil.TypicalEntriesUtil.BURGER;
import static fithelper.testutil.TypicalEntriesUtil.CAKE;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.AddCommand;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Time;
import fithelper.testutil.EntryBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Entry expectedEntry = new EntryBuilder(BURGER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, new AddCommand(expectedEntry));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_FOOD + NAME_DESC_CAKE + NAME_DESC_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, new AddCommand(expectedEntry));

        // multiple times - last time accepted
        assertParseSuccess(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_CAKE + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, new AddCommand(expectedEntry));

        // multiple locations - last location accepted
        assertParseSuccess(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER + LOCATION_DESC_CAKE
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, new AddCommand(expectedEntry));

        // multiple calories - last calories accepted
        assertParseSuccess(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_CAKE + CALORIE_DESC_BURGER, new AddCommand(expectedEntry));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero duration
        Entry expectedEntry = new EntryBuilder(CAKE).withDuration("1").build();
        assertParseSuccess(parser, TYPE_DESC_FOOD + NAME_DESC_CAKE + TIME_DESC_CAKE
                + LOCATION_DESC_CAKE + CALORIE_DESC_CAKE,
                new AddCommand(expectedEntry));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, TYPE_DESC_FOOD + VALID_NAME_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + VALID_TIME_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + VALID_LOCATION_BURGER + CALORIE_DESC_BURGER, expectedMessage);

        // missing calorie prefix
        assertParseFailure(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + VALID_CALORIE_BURGER, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, TYPE_DESC_FOOD + VALID_NAME_BURGER + VALID_TIME_BURGER
                + VALID_LOCATION_BURGER + VALID_CALORIE_BURGER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid name
        assertParseFailure(parser, " " + TYPE_DESC_FOOD + INVALID_NAME_DESC + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, Name.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, " " + TYPE_DESC_FOOD + NAME_DESC_BURGER + INVALID_TIME_DESC
                + LOCATION_DESC_BURGER + CALORIE_DESC_BURGER, Time.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, " " + TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + INVALID_LOCATION_DESC + CALORIE_DESC_BURGER, Location.MESSAGE_CONSTRAINTS);

        // invalid calorie
        assertParseFailure(parser, TYPE_DESC_FOOD + NAME_DESC_BURGER + TIME_DESC_BURGER
                + LOCATION_DESC_BURGER + INVALID_CALORIE_DESC, Calorie.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TYPE_DESC_FOOD + INVALID_NAME_DESC + TIME_DESC_BURGER
                        + LOCATION_DESC_BURGER + INVALID_CALORIE_DESC, Name.MESSAGE_CONSTRAINTS);

    }
}

