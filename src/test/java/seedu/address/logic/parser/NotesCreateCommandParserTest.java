package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_NAME_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_TYPE_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATH_WITH_PREFIX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NotesCreateCommand;
import seedu.address.model.notes.Notes;

public class NotesCreateCommandParserTest {

    private NotesCreateCommandParser parser = new NotesCreateCommandParser();
    private String validPath = "Desktop" + File.separator + "test.doc";
    private String validPathType = "abs";
    private String validFileType = "file";
    private Notes testNote = new Notes(validPath, validFileType, validPathType);

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCreateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsNotesOpenCommand() {
        NotesCreateCommand expectedNotesOpenCommand = new NotesCreateCommand(testNote);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_PATH_WITH_PREFIX
                + VALID_FILE_PATH_WITH_PREFIX
                + VALID_FILE_TYPE_WITH_PREFIX
                + VALID_FILE_NAME_WITH_PREFIX, expectedNotesOpenCommand);

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCreateCommand.MESSAGE_USAGE);
        // No path type indicated
        assertParseFailure(parser, VALID_PATH_WITH_PREFIX + VALID_FILE_NAME_WITH_PREFIX + VALID_FILE_TYPE_WITH_PREFIX,
                expectedMessage);

        // no file name indicated
        assertParseFailure(parser, VALID_PATH_WITH_PREFIX + VALID_FILE_PATH_WITH_PREFIX + VALID_FILE_TYPE_WITH_PREFIX,
                expectedMessage);

        // no file path indicated
        assertParseFailure(parser, VALID_FILE_NAME_WITH_PREFIX
                + VALID_FILE_PATH_WITH_PREFIX
                + VALID_FILE_TYPE_WITH_PREFIX, expectedMessage);

        // no file type indicated
        assertParseFailure(parser, VALID_FILE_NAME_WITH_PREFIX + VALID_FILE_PATH_WITH_PREFIX + VALID_PATH_WITH_PREFIX,
                expectedMessage);
    }

}
