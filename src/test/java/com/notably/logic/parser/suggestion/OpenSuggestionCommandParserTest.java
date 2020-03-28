package com.notably.logic.parser.suggestion;

import static com.notably.logic.parser.suggestion.SuggestionCommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.suggestion.OpenSuggestionCommand;
import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class OpenSuggestionCommandParserTest {
    private static AbsolutePath toRoot;
    private static AbsolutePath toCs2103;
    private static AbsolutePath toCs3230;
    private static AbsolutePath toCs2103Week1;
    private static AbsolutePath toCs2103Week2;
    private static AbsolutePath toCs2103Week3;
    private static AbsolutePath toCs2103Week1Lecture;
    private static Model model;
    private static OpenSuggestionCommandParser openSuggestionCommandParser;

    @BeforeAll
    public static void setUp() {
        // Set up paths
        toRoot = AbsolutePath.fromString("/");
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs3230 = AbsolutePath.fromString("/CS3230");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");
        toCs2103Week3 = AbsolutePath.fromString("/CS2103/Week3");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");

        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        // Add test data to model
        Block cs2103 = new BlockImpl(new Title("CS2103"));
        Block cs3230 = new BlockImpl(new Title("CS3230"));
        model.addBlockToCurrentPath(cs2103);
        model.addBlockToCurrentPath(cs3230);

        Block week1 = new BlockImpl(new Title("Week1"));
        Block week2 = new BlockImpl(new Title("Week2"));
        Block week3 = new BlockImpl(new Title("Week3"));
        model.setCurrentlyOpenBlock(toCs2103);
        model.addBlockToCurrentPath(week1);
        model.addBlockToCurrentPath(week2);
        model.addBlockToCurrentPath(week3);

        Block lecture = new BlockImpl(new Title("Lecture"));
        model.setCurrentlyOpenBlock(toCs2103Week1);
        model.addBlockToCurrentPath(lecture);

        // initialize parser
        openSuggestionCommandParser = new OpenSuggestionCommandParser(model);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(openSuggestionCommandParser, " /CS2103",
            "Invalid input");

        assertParseFailure(openSuggestionCommandParser, " /CS2 103",
                "Invalid input");

        assertParseFailure(openSuggestionCommandParser, " preamble -t /CS2103",
            "Invalid input");
    }

    @Test
    public void parse_uncorrectedPath_throwsParseException() throws ParseException {
        assertParseFailure(openSuggestionCommandParser, " -t randomBlock", "Invalid path");
    }

    @Test
    public void parse_validArgs_returnsOpenSuggestionCommand() throws ParseException {
        SuggestionCommand command = openSuggestionCommandParser.parse(" -t /CS2103/Week1/Lecture");

        assertTrue(command instanceof OpenSuggestionCommand);
    }
}
