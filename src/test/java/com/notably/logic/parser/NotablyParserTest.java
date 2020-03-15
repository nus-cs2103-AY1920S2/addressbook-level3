package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.notably.logic.commands.NewCommand;
import com.notably.model.block.BlockStub;

public class NotablyParserTest {
    private final NotablyParser parser = new NotablyParser();

    @Test
    public void NewCommandTest() throws Exception{
        NewCommand command = (NewCommand) parser.parseCommand(
                NewCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TITLE + " CS2103" + CliSyntax.PREFIX_BODY + " Hi");
        assertEquals(new NewCommand(new BlockStub("CS2103", "Hi"), false), command);
    }
}
