package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_HELP;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tatracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.parser.exceptions.ParseException;

public class TaTrackerParserTest {

    private final TaTrackerParser parser = new TaTrackerParser();

    // @Test
    // public void parseCommand_add() throws Exception {
    //     Student student = new StudentBuilder().build();
    //     AddStudentCommand command = (AddStudentCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
    //     assertEquals(new AddStudentCommand(student, testGroup, testModule), command);
    // }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.DETAILS.getFullCommandWord()) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.DETAILS.getFullCommandWord() + " 3") instanceof ClearCommand);
    }

    // @Test
    // public void parseCommand_delete() throws Exception {
    //     DeleteCommand command = (DeleteCommand) parser.parseCommand(
    //             DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
    //     assertEquals(new DeleteCommand(INDEX_FIRST_STUDENT), command);
    // }

    // @Test
    // public void parseCommand_edit() throws Exception {
    //     Matric matric = new Matric(VALID_MODULE)
    //     Student student = new StudentBuilder().build();
    //     EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
    //     EditStudentCommand command = (EditStudentCommand) parser.parseCommand(
    //             EditStudentCommand.DETAILS.getFullCommandWord() + " "
    //             + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
    //     assertEquals(new EditStudentCommand(INDEX_FIRST_STUDENT, descriptor), command);
    // }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.DETAILS.getFullCommandWord()) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.DETAILS.getFullCommandWord() + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.DETAILS.getFullCommandWord()) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.DETAILS.getFullCommandWord() + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.DETAILS.getFullCommandWord()) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.DETAILS.getFullCommandWord() + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_HELP, ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
