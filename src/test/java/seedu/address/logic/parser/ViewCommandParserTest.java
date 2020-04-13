package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.InsightType;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewCommandParserTest {

    private ViewCommandParser vcp = new ViewCommandParser();

    @Test
    public void parse_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> vcp.parse(null));
    }

    @Test
    public void parse_extraPrefix_failure() {
        String userInput1 = "places m/1 g/1";
        String userInput2 = "a m/1 g/1";
        String userInput3 = "places m/1 g/0";
        assertParseFailure(vcp, userInput1, ViewCommand.MESSAGE_EXTRA_PREFIX);
        assertParseFailure(vcp, userInput2, ViewCommand.MESSAGE_EXTRA_PREFIX);
        assertParseFailure(vcp, userInput3, ViewCommand.MESSAGE_EXTRA_PREFIX);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> vcp.parse("places m/a"));
    }

    @Test
    public void parse_invalidParameter_throwsParseException() {
        assertThrows(ParseException.class, () -> vcp.parse("1"));
        assertThrows(ParseException.class, () -> vcp.parse("times"));
    }

    @Test
    public void parse_invalidPersonIndex_failure() {
        String userInput1 = "places m/a";
        String userInput2 = "activities m/0";
        String userInput3 = "places m/";
        assertParseFailure(vcp, userInput1, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertParseFailure(vcp, userInput2, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertParseFailure(vcp, userInput3, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_invalidGroupIndex_failure() {
        String userInput1 = "places g/a";
        String userInput2 = "activities g/0";
        String userInput3 = "places g/";
        assertParseFailure(vcp, userInput1, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        assertParseFailure(vcp, userInput2, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        assertParseFailure(vcp, userInput3, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void parse_missingIndex_failure() {
        String userInput1 = "places";
        String userInput2 = "activities";
        assertParseFailure(vcp, userInput1, String.format(ViewCommand.MESSAGE_INDEX_REQUIRED,
                ViewCommand.MESSAGE_USAGE));
        assertParseFailure(vcp, userInput2, String.format(ViewCommand.MESSAGE_INDEX_REQUIRED,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_forbiddenIndex_failure() {
        String userInput1 = "all m/1";
        String userInput2 = "time g/1";
        assertParseFailure(vcp, userInput1, String.format(ViewCommand.MESSAGE_INDEX_FORBIDDEN,
                ViewCommand.MESSAGE_USAGE));
        assertParseFailure(vcp, userInput2, String.format(ViewCommand.MESSAGE_INDEX_FORBIDDEN,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyParameter_failure() {
        String userInput = "m/1 g/1";
        assertParseFailure(vcp, userInput, ViewCommand.MESSAGE_INVALID_PARAMETER
                + ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidParameter_failure() {
        String userInput = "a m/1";
        assertParseFailure(vcp, userInput, ViewCommand.MESSAGE_INVALID_PARAMETER
                + ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_highIndex_success() throws ParseException {
        String userInput = "places m/999";
        assertParseSuccess(vcp, userInput, new ViewCommand(ParserUtil.parseIndex("999"),
                "places", InsightType.PERSON));
    }

    @Test
    public void parse_viewPlaces_success() throws ParseException {
        String userInput1 = "places m/2";
        String userInput2 = "places g/3";
        assertParseSuccess(vcp, userInput1, new ViewCommand(ParserUtil.parseIndex("2"),
                "places", InsightType.PERSON));
        assertParseSuccess(vcp, userInput2, new ViewCommand(ParserUtil.parseIndex("3"),
                "places", InsightType.GROUP));
    }

    @Test
    public void parse_viewActivities_success() throws ParseException {
        String userInput1 = "activities m/2";
        String userInput2 = "activities g/3";
        assertParseSuccess(vcp, userInput1, new ViewCommand(ParserUtil.parseIndex("2"),
                "activities", InsightType.PERSON));
        assertParseSuccess(vcp, userInput2, new ViewCommand(ParserUtil.parseIndex("3"),
                "activities", InsightType.GROUP));
    }

    @Test
    public void parse_viewRecent_success() throws ParseException {
        String userInput1 = "recent m/2";
        String userInput2 = "recent";
        assertParseSuccess(vcp, userInput1, new ViewCommand(ParserUtil.parseIndex("2"),
                "recent", InsightType.PERSON));
        assertParseSuccess(vcp, userInput2, new ViewCommand(ParserUtil.parseIndex("1"),
                "recent", InsightType.ALL));
    }

    @Test
    public void parse_viewAll_success() throws ParseException {
        String userInput = "all";
        assertParseSuccess(vcp, userInput, new ViewCommand(ParserUtil.parseIndex("1"),
                "all", InsightType.ALL));
    }

    @Test
    public void parse_viewTime_success() throws ParseException {
        String userInput = "time";
        assertParseSuccess(vcp, userInput, new ViewCommand(ParserUtil.parseIndex("1"),
                "time", InsightType.ALL));
    }
}
