package seedu.address.logic.parser.mods;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMods.CS2103;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.mods.AddModCommand;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.testutil.ModBuilder;

class AddModCommandParserTest {
    private AddModCommandParser parser = new AddModCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Mod expectedMod = new ModBuilder(CS2103).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MODCODE_INPUT + VALID_MODNAME_INPUT,
            new AddModCommand(expectedMod.getModCode(), expectedMod.getModName()));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModCommand.MESSAGE_USAGE);

        // missing modcode prefix
        assertParseFailure(parser, VALID_MODNAME_INPUT, expectedMessage);

        // missing matric number prefix
        assertParseFailure(parser, VALID_MODCODE_INPUT, expectedMessage);

        // multiple input
        assertParseFailure(parser, VALID_MODCODE_INPUT + VALID_MODNAME_INPUT + VALID_MODCODE_INPUT,
            String.format(MESSAGE_REPEATED_PREFIXES, AddModCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid modcode
        assertParseFailure(parser, INVALID_MODCODE_INPUT + VALID_MODNAME_INPUT, ModCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_MODNAME_INPUT + VALID_MODCODE_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModCommand.MESSAGE_USAGE));
    }
}
