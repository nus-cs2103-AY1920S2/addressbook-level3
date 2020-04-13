package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.sort.SortCommand;
import seedu.address.logic.commands.sort.SortInternshipsCommand;
import seedu.address.logic.commands.sort.SortProjectsCommand;
import seedu.address.model.item.Item;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    // NOTE: All userInput must be preceded by a space.
    @Test
    public void parse_sortInternshipByNameWithoutReverseArgument_success() {
        assertParseSuccess(
                parser, " i/ int order/ name" , new SortInternshipsCommand("name", false));
    }

    @Test
    public void parse_sortProjectByTimeReverseIsTrue_success() {
        assertParseSuccess(
                parser, " i/ proj order/ time reverse/ true" , new SortProjectsCommand("time", true));
    }

    @Test
    public void parse_itemTypeMissing_failure() {
        assertParseFailure(parser, " order/ name reverse/true", Item.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_sortOrderMissing_failure() {
        assertParseFailure(
                parser, " i/ res reverse/true",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_sortOrderNotExisted_failure() {
        assertParseFailure(parser, " i/ proj order/ age reverse/true", SortCommand.MESSAGE_SORT_NOT_EXISTED);
    }

    // The sort order exists but not for the asked item type.
    @Test
    public void parse_sortOrderInapplicable_failure() {
        assertParseFailure(parser, " i/ ski order/ time reverse/true", SortCommand.MESSAGE_INAPPLICABLE_SORT);
    }

    @Test
    public void parse_invalidItemType_failure() {
        assertParseFailure(parser, " i/ food order/ name reverse/true", Item.MESSAGE_INVALID_ITEM_TYPE);
    }

}
