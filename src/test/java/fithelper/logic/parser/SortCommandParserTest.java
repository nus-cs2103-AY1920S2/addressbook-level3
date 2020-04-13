package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.commands.CommandTestUtil.ASCENDING;
import static fithelper.logic.commands.CommandTestUtil.CALORIE;
import static fithelper.logic.commands.CommandTestUtil.CAL_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.DESCENDING;
import static fithelper.logic.commands.CommandTestUtil.FOOD;
import static fithelper.logic.commands.CommandTestUtil.FOOD_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.INVALID;
import static fithelper.logic.commands.CommandTestUtil.NAME;
import static fithelper.logic.commands.CommandTestUtil.NAME_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.SPORTS;
import static fithelper.logic.commands.CommandTestUtil.SPORTS_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.TIME;
import static fithelper.logic.commands.CommandTestUtil.TIME_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.WHITE_SPACE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_BY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_ORDER;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.SortCommand;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parseOnlySortByFieldPresentSuccess() {
        // type and order not specified, sort by specified in full name
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + TIME,
                new SortCommand(null, new SortBy(TIME), false));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + CALORIE,
                new SortCommand(null, new SortBy(CALORIE), false));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + NAME,
                new SortCommand(null, new SortBy(NAME), false));

        // type and order not specified, sort by specified in acronym
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + TIME_ACRONYM,
                new SortCommand(null, new SortBy(TIME), false));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + CAL_ACRONYM,
                new SortCommand(null, new SortBy(CALORIE), false));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + NAME_ACRONYM,
                new SortCommand(null, new SortBy(NAME), false));
    }

    @Test
    public void parseAllFieldsPresentSuccess() {
        // order of input varies, types are specified in full name or acronym
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + TIME
                        + WHITE_SPACE + PREFIX_TYPE + SPORTS_ACRONYM
                        + WHITE_SPACE + PREFIX_SORT_ORDER + DESCENDING,
                new SortCommand(new Type(SPORTS), new SortBy(TIME), false));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING
                        + WHITE_SPACE + PREFIX_TYPE + FOOD
                        + WHITE_SPACE + PREFIX_SORT_BY + CALORIE,
                new SortCommand(new Type(FOOD), new SortBy(CALORIE), true));
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_TYPE + SPORTS
                        + WHITE_SPACE + PREFIX_SORT_BY + NAME
                        + WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING,
                new SortCommand(new Type(SPORTS), new SortBy(NAME), true));
    }

    @Test
    public void parseNoSortByFieldFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING
                + WHITE_SPACE + PREFIX_TYPE + FOOD, expectedMessage);
        assertParseFailure(parser, WHITE_SPACE, expectedMessage);
    }

    @Test
    public void parseInvalidFieldValuesFailure() {
        // invalid sort by category
        assertParseFailure(parser, WHITE_SPACE + PREFIX_TYPE + SPORTS
                + WHITE_SPACE + PREFIX_SORT_BY + INVALID
                + WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING, SortBy.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, WHITE_SPACE + PREFIX_TYPE + INVALID
                + WHITE_SPACE + PREFIX_SORT_BY + CAL_ACRONYM
                + WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING, Type.MESSAGE_CONSTRAINTS);

        // invalid sorting order
        assertParseFailure(parser, WHITE_SPACE + PREFIX_TYPE + FOOD_ACRONYM
                        + WHITE_SPACE + PREFIX_SORT_BY + CAL_ACRONYM
                        + WHITE_SPACE + PREFIX_SORT_ORDER + INVALID, SortCommand.SORT_ORDER_CONSTRAINT);
    }

    @Test
    public void parseDuplicateFieldsPresentSuccess() {
        // for all cases where there are duplicate inputs for the same field, only the last input is read and parsed

        // duplicate sort by category
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_SORT_BY + CAL_ACRONYM
                        + WHITE_SPACE + PREFIX_SORT_BY + TIME
                        + WHITE_SPACE + PREFIX_SORT_BY + NAME_ACRONYM
                        + WHITE_SPACE + PREFIX_TYPE + SPORTS_ACRONYM
                        + WHITE_SPACE + PREFIX_SORT_ORDER + DESCENDING,
                new SortCommand(new Type(SPORTS), new SortBy(NAME), false));

        // duplicate sort order
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_TYPE + FOOD
                        + WHITE_SPACE + PREFIX_SORT_ORDER + DESCENDING
                        + WHITE_SPACE + PREFIX_SORT_BY + CALORIE
                        + WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING
                        + WHITE_SPACE + PREFIX_SORT_ORDER + DESCENDING,
                new SortCommand(new Type(FOOD), new SortBy(CALORIE), false));

        // duplicate type of list
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_TYPE + SPORTS
                        + WHITE_SPACE + PREFIX_SORT_BY + NAME
                        + WHITE_SPACE + PREFIX_SORT_ORDER + ASCENDING
                        + WHITE_SPACE + PREFIX_TYPE + FOOD_ACRONYM
                        + WHITE_SPACE + PREFIX_TYPE + SPORTS_ACRONYM,
                new SortCommand(new Type(SPORTS), new SortBy(NAME), true));
    }
}
