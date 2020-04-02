package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.EditCommand;
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

class EditCommandParserTest {

    private static AbsolutePath toBlock;
    private static Model model;
    private static EditCommandParser editCommandParser;

    @BeforeEach
    public void setUp() {
        // Set up paths
        toBlock = AbsolutePath.fromString("/block");

        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);
        editCommandParser = new EditCommandParser();

        // Populate model with test data
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
        model.setCurrentlyOpenBlock(toBlock);
    }

    @Test
    void parse_emptyArgumentWithPrefix_validEditCommand() throws ParseException {
        final EditCommand editCommand = editCommandParser.parse("-b").get(0);

        assertNotNull(editCommand);
    }

    @Test
    void parse_emptyTitleArgumentWithoutPrefix_validEditCommand() throws ParseException {
        final EditCommand editCommand = editCommandParser.parse("").get(0);

        assertNotNull(editCommand);
    }

}
