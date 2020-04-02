package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.Command;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.logic.parser.exceptions.ParseException;
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

class NewCommandParserTest {
    private static AbsolutePath toBlock;
    private static AbsolutePath toAnother;
    private static AbsolutePath toAnotherBlock;
    private static Model model;
    private static NewCommandParser newCommandParser;

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
        newCommandParser = new NewCommandParser(model);

        // Populate model with test data
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("another")));

        model.setCurrentlyOpenBlock(toAnother);
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("toAnother")));
    }

    @Test
    void parse_emptyArgument_throwParseException() throws ParseException {
        assertThrows(ParseException.class, () -> newCommandParser.parse(""));
    }

    @Test
    void parse_emptyTitleArgument_throwParseException() throws ParseException {
        assertThrows(ParseException.class, () -> newCommandParser.parse("-t"));
    }

    @Test
    void parse_jumpToCreatedBlock_openCommand() throws ParseException, CommandException {
        final List<Command> commandList = newCommandParser.parse(" -t Knapsack -o");
        final AbsolutePath expectedPath = AbsolutePath.fromString("/another/Knapsack");

        for (Command command : commandList) {
            command.execute(model);
        }

        assertEquals(expectedPath , model.currentlyOpenPathProperty().getValue());
    }

    @Test
    void parse_emptyBodyArgument_openCommand() throws ParseException, CommandException {
        final NewCommand newCommand = (NewCommand) newCommandParser.parse(" -t Knapsack").get(0);

        final AbsolutePath expectedPath = AbsolutePath.fromString("/another/Knapsack");

        newCommand.execute(model);

        assertTrue(model.hasPath(expectedPath));
    }

    @Test
    void parse_sameTitleArgument_throwsCommandException() throws ParseException, CommandException {
        final NewCommand newCommand = (NewCommand) newCommandParser.parse(" -t block -b lorem").get(0);

        assertThrows(CommandException.class, () -> newCommand.execute(model));
    }

}
