//TODO: To be enabled or changed when refactoring is completed
//package com.notably.logic.parser;
//
//import static com.notably.testutil.Assert.assertThrows;
//import static com.notably.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import com.notably.logic.commands.ClearCommand;
//import com.notably.logic.commands.DeleteCommand;
//import com.notably.logic.commands.ExitCommand;
//import com.notably.logic.commands.HelpCommand;
//import com.notably.logic.parser.exceptions.ParseException;
//
//public class AddressBookParserTest {
//
//    private final AddressBookParser parser = new AddressBookParser();
//
//    @Test
//    public void parseCommand_add() throws Exception {
//
//    }
//
//    @Test
//    public void parseCommand_clear() throws Exception {
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
//    }
//
//    @Test
//    public void parseCommand_delete() throws Exception {
//        DeleteCommand command = (DeleteCommand) parser.parseCommand(
//                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
//        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
//    }
//
//    @Test
//    public void parseCommand_edit() throws Exception {
//
//    }
//
//    @Test
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
//    }
//
//    @Test
//    public void parseCommand_help() throws Exception {
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
//    }
//
//    @Test
//    public void parseCommand_unrecognisedInput_throwsParseException() {
//        assertThrows(ParseException.class, "MESSAGE_INVALID_COMMAND_FORMAT", ()
//            -> parser.parseCommand(""));
//    }
//
//    @Test
//    public void parseCommand_unknownCommand_throwsParseException() {
//        assertThrows(ParseException.class, "MESSAGE_UNKNOWN_COMMAND", () -> parser.parseCommand("unknownCommand"));
//    }
//}
