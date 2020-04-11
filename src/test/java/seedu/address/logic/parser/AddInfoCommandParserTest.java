package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.model.person.Remark;

public class AddInfoCommandParserTest {
    private AddInfoCommandParser parser = new AddInfoCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = FIRST_INDEX;
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(nonEmptyRemark));
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark;
        AddInfoCommand expectedCommand = new AddInfoCommand(FIRST_INDEX, remarks);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInfoCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddInfoCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddInfoCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
