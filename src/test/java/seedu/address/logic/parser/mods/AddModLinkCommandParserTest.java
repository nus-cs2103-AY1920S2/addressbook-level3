package seedu.address.logic.parser.mods;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODLINK_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMods.CS2103;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.mods.AddModLinkCommand;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.testutil.ModBuilder;

class AddModLinkCommandParserTest {
    private AddModLinkCommandParser parser = new AddModLinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Mod expectedMod = new ModBuilder(CS2103).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MODCODE_INPUT
                + VALID_MODLINK_INPUT + VALID_MODLINK_NAME_INPUT,
            new AddModLinkCommand(expectedMod.getModCode(), VALID_MODLINK_NAME, new ModLink(VALID_MODLINK)));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModLinkCommand.MESSAGE_USAGE);

        // missing modcode prefix
        assertParseFailure(parser, VALID_MODLINK_INPUT + VALID_MODLINK_NAME_INPUT, expectedMessage);

        // missing modlink
        assertParseFailure(parser, VALID_MODCODE_INPUT + VALID_MODLINK_NAME_INPUT, expectedMessage);

        // missing modlink name
        assertParseFailure(parser, VALID_MODCODE_INPUT + VALID_MODLINK_INPUT, expectedMessage);

        // multiple input
        assertParseFailure(parser, VALID_MODCODE_INPUT + VALID_MODLINK_INPUT
                + VALID_MODLINK_NAME_INPUT + VALID_MODCODE_INPUT,
            String.format(MESSAGE_REPEATED_PREFIXES, AddModLinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_MODCODE_INPUT + VALID_MODLINK_INPUT + VALID_MODLINK_NAME_INPUT,
            ModCode.MESSAGE_CONSTRAINTS);

        // invalid modlink
        assertParseFailure(parser, VALID_MODCODE_INPUT + INVALID_MODLINK_INPUT + VALID_MODLINK_NAME_INPUT,
            ModLink.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_MODCODE_INPUT
                + VALID_MODLINK_INPUT + VALID_MODLINK_NAME_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModLinkCommand.MESSAGE_USAGE));
    }

}
