package seedu.address.logic.parser.mods;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMods.CS2103;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.mods.ClearModLinksCommand;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.testutil.ModBuilder;

class ClearModLinksCommandParserTest {
    private ClearModLinksCommandParser parser = new ClearModLinksCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Mod expectedMod = new ModBuilder(CS2103).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MODCODE_INPUT,
            new ClearModLinksCommand(expectedMod.getModCode()));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearModLinksCommand.MESSAGE_USAGE);

        // missing modcode prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);

        // multiple input
        assertParseFailure(parser, VALID_MODCODE_INPUT + VALID_MODCODE_INPUT,
            String.format(MESSAGE_REPEATED_PREFIXES, ClearModLinksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_MODCODE_INPUT, ModCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_MODCODE_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearModLinksCommand.MESSAGE_USAGE));
    }

}
