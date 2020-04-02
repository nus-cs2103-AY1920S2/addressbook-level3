package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_HELP;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.FindCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.NameContainsKeywordsPredicate;
import tatracker.model.student.Student;
import tatracker.testutil.StudentUtil;
import tatracker.testutil.student.EditStudentDescriptorBuilder;
import tatracker.testutil.student.StudentBuilder;

public class TaTrackerParserTest {

    private final TaTrackerParser parser = new TaTrackerParser();

    private final Group testGroup = new Group("W17-4");
    private final Module testModule = new Module("CS2103T");

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

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditStudentCommand command = (EditStudentCommand) parser.parseCommand(
                EditStudentCommand.DETAILS.getFullCommandWord() + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditStudentCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.DETAILS.getFullCommandWord()) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.DETAILS.getFullCommandWord() + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.DETAILS.getFullCommandWord() + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
