package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_SESSION;

//import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.commons.util.DateTimeUtil;
//import tatracker.logic.commands.Command;
import tatracker.logic.commands.session.EditSessionCommand;
//import tatracker.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
//import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.SessionType;
//import tatracker.testutil.sessions.EditSessionDescriptorBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteStudentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteStudentCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EditSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = Messages
            .getInvalidCommandMessage(EditSessionCommand.DETAILS.getUsage());

    private EditSessionCommandParser parser = new EditSessionCommandParser();

    @Test
    public void parse_missingPars_failure() {

        // index provided, but no field specified
        assertParseFailure(parser,
                "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser,
                "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser,
                "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 random", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid sessionType
        assertParseFailure(parser, "1 " + "t/grade", SessionType.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, "1 " + "d/2020-02-31", DateTimeUtil.CONSTRAINTS_DATE);

        // invalid time
        assertParseFailure(parser, "1 " + "s/6:30", DateTimeUtil.CONSTRAINTS_TIME);
    }

    /*@Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";

        LocalDateTime startTime = LocalDateTime.of(2020, 05, 20, 17, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 05, 20, 17, 00);
        EditSessionDescriptor descriptor =
                new EditSessionDescriptorBuilder()
                        .withStartTime(startTime)
                        .withEndTime(endTime)
                        .withModule("CS3243")
                        .withSessionType("grading")
                        .withDescription("grade group 1")
                        .withRecurring(0).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    } *

    /*@Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";

        EditSessionDescriptor descriptor =
                new EditSessionDescriptorBuilder()
                        .withModule("CS3243")
                        .withRecurring(0).build();
        EditSessionCommand expectedCommand = new EditSessionCommand(INDEX_FIRST_SESSION, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    } */

    /*@Test
    public void parse_oneFieldSpecified_success() throws ParseException {

        // with date and time
        String userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";
        EditSessionDescriptor descriptor =
                new EditSessionDescriptorBuilder().withStartTime("2020-06-20T17:00")
                        .withEndTime("2020-06-20T18:00").build();
        EditSessionCommand expectedCommand = new EditSessionCommand(
                INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module
        userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";
        descriptor = new EditSessionDescriptorBuilder().withModule("CS3243").build();
        expectedCommand = new EditSessionCommand(
                INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // session type
        userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";
        descriptor = new EditSessionDescriptorBuilder().withSessionType("consultation").build();
        expectedCommand = new EditSessionCommand(
                INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // recurring weeks
        userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";
        descriptor = new EditSessionDescriptorBuilder().withRecurring(0).build();
        expectedCommand = new EditSessionCommand(
                INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = "1 " + "s/17:00 " + "e/18:00 " + "d/2020-05-20 "
                + "m/CS2103T " + "lab " + "n/prepare notes " + "w/2";
        descriptor = new EditSessionDescriptorBuilder().withDescription("markings").build();
        expectedCommand = new EditSessionCommand(
                INDEX_FIRST_SESSION, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    } */
}
