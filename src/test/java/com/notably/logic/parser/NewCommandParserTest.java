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
import com.notably.model.block.BlockModel;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.testutil.TypicalBlockModel;

class NewCommandParserTest {
    private static AbsolutePath toAnother;
    private static Model model;
    private static NewCommandParser newCommandParser;

    @BeforeEach
    public void setUp() {
        // Set up model
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);
        newCommandParser = new NewCommandParser(model);

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
        final AbsolutePath expectedPath = AbsolutePath.fromString("/Y2S2/Knapsack");

        for (Command command : commandList) {
            command.execute(model);
        }

        assertEquals(expectedPath , model.currentlyOpenPathProperty().getValue());
    }

    @Test
    void parse_emptyBodyArgument_openCommand() throws ParseException, CommandException {
        final NewCommand newCommand = (NewCommand) newCommandParser.parse(" -t Knapsack").get(0);

        final AbsolutePath expectedPath = AbsolutePath.fromString("/Y2S2/Knapsack");

        newCommand.execute(model);

        assertTrue(model.hasPath(expectedPath));
    }

    @Test
    void parse_duplicateTitleArgument_throwsCommandException() throws ParseException, CommandException {
        final NewCommand newCommand = (NewCommand) newCommandParser.parse(" -t CS2103T").get(0);

        assertThrows(CommandException.class, () -> newCommand.execute(model));
    }

}
