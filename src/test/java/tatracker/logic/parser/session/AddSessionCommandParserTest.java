//@@author Chuayijing
package tatracker.logic.parser.session;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.commands.session.AddSessionCommand;
import tatracker.model.session.SessionType;


public class AddSessionCommandParserTest {

    private static final String DEFAULT_START = LocalTime.of(17, 30).toString();
    private static final String DEFAULT_END = LocalTime.of(19, 30).toString();
    private static final String DEFAULT_DATE = LocalDate.of(2020, 05, 20).toString();
    private static final String DEFAULT_MODULE = "CS2103T";
    private static final String DEFAULT_TYPE = SessionType.TUTORIAL.toString();
    private static final String DEFAULT_DESCRIPTION = "finishes his tutorial";
    private static final String DEFAULT_RECURRING = "2";

    private static final String validModule = "m/C2103T";
    private static final String invalidModule = "C3243";

    private AddSessionCommandParser parser = new AddSessionCommandParser();

    /*@Test
    public void parse_allFieldsPresent_success() {
        Session expectedSession = new SessionBuilder().build();

        String command =
                "m/" + DEFAULT_MODULE
                + " s/" + DEFAULT_START + " e/" + DEFAULT_END + " d/" + DEFAULT_DATE
                + " w/" + DEFAULT_RECURRING + " t/" + DEFAULT_TYPE + " n/" + DEFAULT_DESCRIPTION;

        assertParseSuccess(parser, command, new AddSessionCommand(expectedSession));
    } */

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = Messages.getInvalidCommandMessage(AddSessionCommand.DETAILS.getUsage());

        //missing module
        assertParseFailure(parser, DEFAULT_START + DEFAULT_END + DEFAULT_DATE, expectedMessage);

        //all prefixes missing
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException {
        // invalid session types, module does not exists
        String expectedMessage = Messages.getInvalidCommandMessage(AddSessionCommand.DETAILS.getUsage());
        StringBuilder command = new StringBuilder()
                .append(DEFAULT_MODULE)
                .append(DEFAULT_START)
                .append(DEFAULT_END)
                .append(DEFAULT_DATE)
                .append(DEFAULT_RECURRING)
                .append("consult")
                .append(DEFAULT_DESCRIPTION);

        assertParseFailure(parser, command.toString(), expectedMessage);
    }
}
