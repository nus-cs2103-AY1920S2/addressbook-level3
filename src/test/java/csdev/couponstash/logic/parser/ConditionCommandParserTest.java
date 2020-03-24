package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static csdev.couponstash.testutil.TypicalIndexes.INDEX_FIRST_COUPON;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.ConditionCommand;
import csdev.couponstash.model.coupon.Condition;

public class ConditionCommandParserTest {
    private ConditionCommandParser parser = new ConditionCommandParser();
    private final String nonEmptyRemark = "Some remark.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_COUPON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_CONDITION + nonEmptyRemark;
        ConditionCommand expectedCommand = new ConditionCommand(INDEX_FIRST_COUPON,
                new Condition(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_CONDITION;
        expectedCommand = new ConditionCommand(INDEX_FIRST_COUPON, new Condition(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConditionCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, ConditionCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, ConditionCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}
