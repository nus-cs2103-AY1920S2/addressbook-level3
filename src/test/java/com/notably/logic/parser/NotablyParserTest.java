package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.EditCommand;
import com.notably.logic.commands.ExitCommand;
import com.notably.logic.commands.HelpCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class NotablyParserTest {

    private static AbsolutePath toBlock;
    private static AbsolutePath toAnother;
    private static AbsolutePath toAnotherBlock;
    private static Model model;
    private static NotablyParser parser;

    @BeforeEach
    public void setUp() {
        // Set up paths
        toBlock = AbsolutePath.fromString("/block");
        toAnother = AbsolutePath.fromString("/another");
        toAnotherBlock = AbsolutePath.fromString("/another/block");

        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        // Populate model with test data
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("another")));

        model.setCurrentlyOpenBlock(toAnother);
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("toAnother")));

        parser = new NotablyParser(model);
    }


    @Test
    public void parseCommand_newCommandInput_newCommand() throws Exception {
        Command command = parser.parseCommand("new -t 1234 -b i love 2103").get(0);

        assertTrue(command instanceof NewCommand);

        command.execute(model);

        assertTrue(model.hasPath(ParserUtil.createAbsolutePath("1234", model.getCurrentlyOpenPath())));
    }

    @Test
    public void parseCommand_existingNoteInput_throwsError() throws Exception {
        Command command = parser.parseCommand("new -t block -b Hi").get(0);

        assertTrue(command instanceof NewCommand);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void parseCommand_newCommandInputJump_false() throws Exception {
        List<? extends Command> commands = parser.parseCommand("new -t CS2103 -b Hi");

        assertTrue(commands.size() == 1);
    }

    @Test
    public void parseCommand_newCommandInputJump_true() throws Exception {
        List<? extends Command> commands = parser.parseCommand("new -t CS2103 -b Hi -o");
        assertTrue(commands.size() == 2);
    }

    @Test
    public void parseCommand_openCommandInput_openCommand() throws Exception {
        Command command = parser.parseCommand("open -t CS2104").get(0);

        assertTrue(command instanceof OpenCommand);

        command.execute(model);

        assertEquals(AbsolutePath.fromString("/another/CS2103"), model.getCurrentlyOpenPath());
    }

    @Test
    public void parseCommand_openCommandShorthandInput() throws Exception {
        Command command = parser.parseCommand("o -t CS2104").get(0);

        assertTrue(command instanceof OpenCommand);

        command.execute(model);

        assertEquals(AbsolutePath.fromString("/another/CS2103"), model.getCurrentlyOpenPath());
    }

    @Test
    public void parseCommand_deleteCommandInput_deleteCommand() throws Exception {
        Command command = parser.parseCommand("delete -t ../CS2103").get(0);

        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parseCommand_deleteCommandInputNoPrefix_deleteCommand() throws Exception {
        Command command = parser.parseCommand("delete ../CS2103").get(0);

        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parseCommand_editCommandInput_editCommand() throws Exception {
        Command command = parser.parseCommand("edit -b lorem ").get(0);

        assertTrue(command instanceof EditCommand);
    }

    @Test
    public void parseCommand_editCommandInputNoPrefix_editCommand() throws Exception {
        Command command = parser.parseCommand("edit lorem").get(0);

        assertTrue(command instanceof EditCommand);
    }

    @Test
    public void parseCommand_helpCommandInput_helpCommand() throws Exception {
        Command command = parser.parseCommand("help").get(0);

        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpCommandShorthandInput_helpCommand() throws Exception {
        Command command = parser.parseCommand("h").get(0);

        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void parseCommand_exitCommandInput_exitCommand() throws Exception {
        Command command = parser.parseCommand("exit").get(0);

        assertTrue(command instanceof ExitCommand);
    }
}
