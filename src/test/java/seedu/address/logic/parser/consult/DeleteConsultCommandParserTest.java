package seedu.address.logic.parser.consult;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_INDEX_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.DeleteConsultCommand;
import seedu.address.logic.parser.consults.DeleteConsultCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteConsultCommandParserTest {
    private DeleteConsultCommandParser parser = new DeleteConsultCommandParser();

    @Test
    public void parse_indexPresent_success() throws ParseException {
        String indexInput = "1";
        Index index = Index.fromOneBased(Integer.parseInt(indexInput));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + indexInput,
            new DeleteConsultCommand(index));
    }

    @Test
    public void parse_indexMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteConsultCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, VALID_INDEX + VALID_START_TIME_INPUT + VALID_END_TIME_INPUT
            + VALID_PLACE_INPUT, expectedMessage);
    }

    @Test
    public void parse_invalidFields_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_STUDENT_INDEX_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteConsultCommand.MESSAGE_USAGE));
    }
}
