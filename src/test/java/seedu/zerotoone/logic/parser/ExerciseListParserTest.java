package seedu.zerotoone.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.AddCommand;
import seedu.zerotoone.logic.commands.ClearCommand;
import seedu.zerotoone.logic.commands.DeleteCommand;
import seedu.zerotoone.logic.commands.EditCommand;

// import seedu.zerotoone.logic.commands.EditCommand.EditExerciseDescriptor;
import seedu.zerotoone.logic.commands.ExitCommand;
import seedu.zerotoone.logic.commands.FindCommand;
import seedu.zerotoone.logic.commands.HelpCommand;
import seedu.zerotoone.logic.commands.ListCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.NameContainsKeywordsPredicate;
import seedu.zerotoone.testutil.EditExerciseDescriptorBuilder;
import seedu.zerotoone.testutil.ExerciseBuilder;
import seedu.zerotoone.testutil.ExerciseUtil;

public class ExerciseListParserTest {

    private final ParserManager parser = new ParserManager();

    @Test
    public void parseCommand_add() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ExerciseUtil.getAddCommand(exercise));
        assertEquals(new AddCommand(exercise), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_EXERCISE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_EXERCISE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        EditCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(exercise).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EXERCISE.getOneBased() + " " + ExerciseUtil.getEditExerciseDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_EXERCISE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
