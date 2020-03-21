package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;


public class NotablyParserTest {
    private final NotablyParser parser = new NotablyParser();

    @Test
    public void parseCommand_newCommandInput_newCommand() throws Exception {
        Object command = parser.parseCommand("new -t CS2103 -b Hi").get(0);

        assertTrue(command instanceof NewCommand);
    }

    @Test
    public void parseCommand_newCommandInputJump_false() throws Exception {
        List<Command> commands =  parser.parseCommand("new -t CS2103 -b Hi");

        assertTrue(commands.size() == 1);
    }

    @Test
    public void parseCommand_newCommandInputJump_true() throws Exception {
        List<Command> commands = parser.parseCommand("new -t CS2103 -b Hi -o");
        assertTrue(commands.size() == 2);
    }

    @Test
    public void parseCommand_openCommandInput_openCommand() throws Exception {
        Object command = parser.parseCommand("open -t CS2103").get(0);

        assertTrue(command instanceof OpenCommand);
    }

    @Test
    public void parseCommand_deleteCommandInput_deleteCommand() throws Exception {
        Object command = parser.parseCommand("delete -t CS2103").get(0);

        assertTrue(command instanceof DeleteCommand);
    }


}
