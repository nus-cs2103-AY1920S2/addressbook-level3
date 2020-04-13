package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class CommandExceptionTest {

    @Test
     public void unknownCommand_throwUnknownCommandMessage() {
        assertEquals(new CommandException(MESSAGE_UNKNOWN_COMMAND).getMessage(), MESSAGE_UNKNOWN_COMMAND);
    }
}
