//@@author aakanksha-rai
package tatracker.logic.parser.sort;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.sort.SortCommand;
import tatracker.logic.commands.sort.SortGroupCommand;
import tatracker.logic.commands.sort.SortModuleCommand;
import tatracker.logic.commands.sort.SortType;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        SortType type = SortType.ALPHABETIC;
        String expectedModule = "CS3243";
        String expectedGroup = "G04";


        assertParseSuccess(parser, " group m/CS3243 g/G04 t/alpha",
                new SortGroupCommand(type, expectedGroup, expectedModule));

        // different order
        assertParseSuccess(parser, " group t/alphabetically m/CS3243 g/G04",
                new SortGroupCommand(type, expectedGroup, expectedModule));

        // multiple of same prefix
        assertParseSuccess(parser, " group t/alphabetically g/G05 m/CS3243 g/G04",
                new SortGroupCommand(type, expectedGroup, expectedModule));

        assertParseSuccess(parser, " module m/CS3243 t/alphabetically",
                new SortModuleCommand(type, expectedModule));

        // different order
        assertParseSuccess(parser, " module t/alphabetically m/CS3243",
                new SortModuleCommand(type, expectedModule));

        //Repeat prefixes
        assertParseSuccess(parser, " module t/rating asc t/alphabetically m/CS3243",
                new SortModuleCommand(type, expectedModule));

        assertParseSuccess(parser, " all t/alphabetically",
                new SortCommand(type));

        //repeat prefixes
        assertParseSuccess(parser, " all t/rating asc t/alphabetically",
                new SortCommand(type));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedGroupMessage = Messages.getInvalidCommandMessage(SortGroupCommand.DETAILS.getUsage());
        String expectedModuleMessage = Messages.getInvalidCommandMessage(SortModuleCommand.DETAILS.getUsage());
        String expectedAllMessage = Messages.getInvalidCommandMessage(SortCommand.DETAILS.getUsage());

        // missing group prefix
        assertParseFailure(parser, " group m/CS3243 t/alphabetically",
                expectedGroupMessage);

        // missing module prefix
        assertParseFailure(parser, " group g/G04 t/alphabetically",
                expectedGroupMessage);

        // missing type prefix
        assertParseFailure(parser, " group g/G04 m/CS3243",
                expectedGroupMessage);

        // missing all prefixes
        assertParseFailure(parser, " group",
                expectedGroupMessage);

        // missing module prefix
        assertParseFailure(parser, " module t/alphabetically",
                expectedModuleMessage);

        // missing type prefix
        assertParseFailure(parser, " module m/CS3243",
                expectedModuleMessage);

        // missing all prefixes
        assertParseFailure(parser, " module",
                expectedModuleMessage);


        // missing type prefix
        assertParseFailure(parser, " all",
                expectedAllMessage);

    }
}
