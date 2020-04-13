package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskForOneModuleCommand;
import seedu.address.model.task.TaskContainsInModulePredicate;

class TaskForOneModuleCommandParserTest {
    private TaskForOneModuleCommandParser parser = new TaskForOneModuleCommandParser();

    @Test
    public void parse_moduleCodePresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS2101,
                new TaskForOneModuleCommand(new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2101)));

        // arbitrary preamble
        assertParseSuccess(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS2101,
                new TaskForOneModuleCommand(new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2101)));

        // no premable
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2101,
                new TaskForOneModuleCommand(new TaskContainsInModulePredicate(VALID_MODULE_CODE_CS2101)));
    }

    @Test
    public void parse_moduleCodeMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskForOneModuleCommand.MESSAGE_USAGE);
        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPTY_ARGUMENTS, expectedMessage);

        // arbitrary preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EMPTY_ARGUMENTS, expectedMessage);

        // no premable
        assertParseFailure(parser, EMPTY_ARGUMENTS, expectedMessage);
    }
}
