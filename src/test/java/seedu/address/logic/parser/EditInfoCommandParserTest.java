package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditInfoCommand;
import seedu.address.model.person.Remark;


public class EditInfoCommandParserTest {
    private EditInfoCommandParser parser = new EditInfoCommandParser();
    private final int lineNum = 1;
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = FIRST_INDEX;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LINE_NUMBER
                + lineNum + " " + PREFIX_REMARK + nonEmptyRemark;
        EditInfoCommand expectedCommand = new EditInfoCommand(FIRST_INDEX, 1, new Remark(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_LINE_NUMBER + lineNum + " " + PREFIX_REMARK;
        expectedCommand = new EditInfoCommand(FIRST_INDEX, 1, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditInfoCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, EditInfoCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, EditInfoCommand.COMMAND_WORD + " "
                + PREFIX_LINE_NUMBER + lineNum + " " + nonEmptyRemark, expectedMessage);
    }
}
