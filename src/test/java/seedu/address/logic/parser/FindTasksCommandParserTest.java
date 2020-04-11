package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByCatCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByDateCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByModuleCodeCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksCommand;
import seedu.address.model.calender.CatContainsKeywordsPredicate;
import seedu.address.model.calender.DateContainKeywordsPredicate;
import seedu.address.model.calender.ModuleCodeContainKeywordsPredicate;
import seedu.address.model.nusmodule.ModuleCode;

class FindTasksCommandParserTest {

    private FindTasksCommandParser parser = new FindTasksCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {

        ModuleCode expectedModuleCode = new ModuleCode("CS2103");

        assertParseSuccess(parser, " m/CS2103",
                new FindTasksByModuleCodeCommand(
                        new ModuleCodeContainKeywordsPredicate(Arrays.asList(expectedModuleCode.toString()))));

        String expectedDate = "02-02-2020";

        assertParseSuccess(parser, " date/02-02-2020",
                new FindTasksByDateCommand(
                        new DateContainKeywordsPredicate(Arrays.asList(expectedDate))));

        String expectedCat = "school";

        assertParseSuccess(parser, " cat/school",
                new FindTasksByCatCommand(
                        new CatContainsKeywordsPredicate(Arrays.asList(expectedCat))));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTasksCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " lhjjqs", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        //invalid module code
        assertParseFailure(parser, " m/casdas", ModuleCode.MESSAGE_CONSTRAINTS);

        //invalid date
        assertParseFailure(parser, " date/casdass", "Invalid date! format:{dd-mm-yyyy}");

    }
}
