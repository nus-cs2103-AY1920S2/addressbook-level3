package seedu.expensela.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_FILTER;

import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_10;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_11;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_12;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_13;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_14;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_15;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_16;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_17;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_18;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_19;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_20;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_21;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_22;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_23;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_7;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_8;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_CATEGORY_PREDICATE_9;

import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_1;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_10;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_11;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_12;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_13;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_14;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_15;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_16;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_17;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_18;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_19;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_2;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_20;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_21;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_22;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_23;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_24;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_3;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_4;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_5;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_6;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_7;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_8;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_FILTER_MONTH_PREDICATE_9;

import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.FilterCommand;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;





class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList("FOOD")),
                        new DateEqualsKeywordPredicate(Arrays.asList("2020-01")));
        assertParseSuccess(parser, " c/FOOD m/2020-01", expectedFilterCommand);
    }

    @Test
    public void parse_validCatArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList("FOOD")),
                        new DateEqualsKeywordPredicate(Arrays.asList("ALL")));
        assertParseSuccess(parser, " c/FOOD", expectedFilterCommand);
    }

    @Test
    public void parse_validCaseInsensitiveCatArgs_returnsFilterCommand() {
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList("FOOD")),
                        new DateEqualsKeywordPredicate(Arrays.asList("ALL")));

        //lower case category name
        assertParseSuccess(parser, " c/food", expectedFilterCommand);

        // mixed case category name
        assertParseSuccess(parser, " c/fOoD", expectedFilterCommand);
    }

    @Test
    public void parse_validDateArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList("ALL")),
                        new DateEqualsKeywordPredicate(Arrays.asList("2020-01")));
        assertParseSuccess(parser, " m/2020-01", expectedFilterCommand);
    }

    @Test
    public void parse_invalidCatArgs_throwsParseException() {
        // category listed not in category enum
        assertParseFailure(parser, " c/PETS", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // category mispelt
        assertParseFailure(parser, " c/HEATH", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // category not a String
        assertParseFailure(parser, " c/1234", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateArgs_throwsParseException() {
        // date year before 1900
        assertParseFailure(parser, " m/1899-12", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // date year is not an integer
        assertParseFailure(parser, " m/two thousand and two-12", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // invalid date month (too big)
        assertParseFailure(parser, " m/2999-13", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // invalid date month (too small)
        assertParseFailure(parser, " m/2999-00", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // invalid date month (not an integer)
        assertParseFailure(parser, " m/January", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));

        // month and category swapped
        assertParseFailure(parser, " c/2020-01 m/FOOD", String.format(MESSAGE_INVALID_FILTER,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void isValidCategory() {
        // invalid categories
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_1));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_2));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_3));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_4));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_5));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_6));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_7));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_8));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_9));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_10));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_11));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_12));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_13));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_14));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_15));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_16));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_17));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_18));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_19));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_20));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_21));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_22));
        assertFalse(parser.isValidCategory(INVALID_FILTER_CATEGORY_PREDICATE_23));

        // valid categories
        assertTrue(parser.isValidCategory("FOOD"));
        assertTrue(parser.isValidCategory("MISC"));
        assertTrue(parser.isValidCategory("SHOPPING"));
        assertTrue(parser.isValidCategory("GROCERIES"));
        assertTrue(parser.isValidCategory("TRANSPORT"));
        assertTrue(parser.isValidCategory("HEALTH"));
        assertTrue(parser.isValidCategory("RECREATION"));
        assertTrue(parser.isValidCategory("ALL"));
        assertTrue(parser.isValidCategory("UTILITIES"));
        assertTrue(parser.isValidCategory("INCOME"));
    }

    @Test
    public void isValidMonth() {
        // invalid month
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_1));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_2));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_3));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_4));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_5));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_6));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_7));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_8));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_9));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_10));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_11));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_12));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_13));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_14));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_15));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_16));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_17));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_18));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_19));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_20));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_21));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_22));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_23));
        assertFalse(parser.isValidMonth(INVALID_FILTER_MONTH_PREDICATE_24));

        // valid month
        assertTrue(parser.isValidMonth("1900-01"));
        assertTrue(parser.isValidMonth("9999-12"));
        assertTrue(parser.isValidMonth("2020-06"));
    }
}
