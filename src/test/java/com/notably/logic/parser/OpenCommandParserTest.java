package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.OpenCommand;
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

class OpenCommandParserTest {
    private static AbsolutePath toBlock;
    private static AbsolutePath toAnother;
    private static AbsolutePath toAnotherBlock;
    private static Model model;
    private static OpenCommandParser openCommandParser;
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
        openCommandParser = new OpenCommandParser(model);

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
    void parse_emptyArgument_throwParseException() throws ParseException {
        assertThrows(ParseException.class, () -> openCommandParser.parse(""));
    }

    @Test
    void parse_emptyTitleArgument_throwParseException() throws ParseException {
        assertThrows(ParseException.class, () -> openCommandParser.parse("-t"));
    }

    @Test
    void parse_absolutePathArgument_openCommand() throws ParseException, CommandException {
        final OpenCommand openCommand = openCommandParser.parse(" -t /another/block").get(0);

        openCommand.execute(model);

        assertEquals(toAnotherBlock, model.currentlyOpenPathProperty().getValue());
    }

    @Test
    void parse_relativePathArgument_openCommand() throws ParseException, CommandException {
        final OpenCommand openCommand = openCommandParser.parse(" -t block").get(0);

        // Current directory /another
        openCommand.execute(model);

        //Expected directory after executing Command /another/block
        assertEquals(toAnotherBlock, model.currentlyOpenPathProperty().getValue());
    }
}
