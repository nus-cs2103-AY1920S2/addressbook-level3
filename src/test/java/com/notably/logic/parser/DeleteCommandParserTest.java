package com.notably.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.correction.AbsolutePathCorrectionEngine;
import com.notably.logic.correction.CorrectionEngine;
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

class DeleteCommandParserTest {
    private static final int CORRECTION_THRESHOLD = 2;
    private static final boolean USE_FORWARD_MATCHING = true;
    private static AbsolutePath toBlock;
    private static AbsolutePath toAnother;
    private static AbsolutePath toAnotherBlock;
    private static Model model;
    private static DeleteCommandParser deleteCommandParser;

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
        CorrectionEngine<AbsolutePath> pathCorrectionEngine = new AbsolutePathCorrectionEngine(model,
                CORRECTION_THRESHOLD, USE_FORWARD_MATCHING);
        deleteCommandParser = new DeleteCommandParser(model, pathCorrectionEngine);

        // Populate model with test data
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("another")));

        model.setCurrentlyOpenBlock(toAnother);
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("toAnother")));
    }

    @Test
    void parse_relativePathWithPrefix_validDeleteCommand() throws ParseException {
        final DeleteCommand deleteCommand = deleteCommandParser.parse(" -t block").get(0);

        assertNotNull(deleteCommand);
    }

    @Test
    void parse_absolutePathWithPrefix_validDeleteCommand() throws ParseException {
        final DeleteCommand deleteCommand = deleteCommandParser.parse(" -t /another/block").get(0);

        assertNotNull(deleteCommand);
    }

    @Test
    void parse_relativePathWithoutPrefix_validDeleteCommand() throws ParseException {
        final DeleteCommand deleteCommand = deleteCommandParser.parse(" block").get(0);

        assertNotNull(deleteCommand);
    }

    @Test
    void parse_absolutePathWithoutPrefix_validDeleteCommand() throws ParseException {
        final DeleteCommand deleteCommand = deleteCommandParser.parse(" /another/block").get(0);

        assertNotNull(deleteCommand);
    }

    @Test
    void parse_invalidBlock_throwParseException() throws ParseException {
        assertThrows(ParseException.class, () -> deleteCommandParser.parse(" -t /nonExistent"));
    }

}
