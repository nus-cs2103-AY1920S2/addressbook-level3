package com.notably.logic.parser.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;
import static com.notably.logic.parser.suggestion.SuggestionCommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;
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

    private static final String COMMAND_WORD = "open";
    private static final String RESPONSE_MESSAGE = "Open a note";

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
    public void parse_uncorrectedPath_throwsParseException() {
        assertParseFailure(openSuggestionCommandParser, " -t randomBlock", "Invalid path");
    }

    @Test
    public void parse_correctAbsolutePathWithPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " " + PREFIX_TITLE + " " + toCs2103.getStringRepresentation();
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath =
            openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        // Expected result
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1 = new SuggestionItemImpl(toCs2103Week1.getStringRepresentation(), null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(), null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
            null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);
        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week2.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week3.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctAbsolutePathWithoutPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " " + toCs2103.getStringRepresentation();
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath =
            openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        // Expected result
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1 = new SuggestionItemImpl(toCs2103Week1.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(), null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(), null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);
        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + toCs2103.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week2.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week3.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctedAbsolutePathWithPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " " + PREFIX_TITLE + " /CS2104";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectedPath =
            openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectedPath instanceof OpenSuggestionCommand);

        commandCorrectedPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        // Expected result
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1 = new SuggestionItemImpl(toCs2103Week1.getStringRepresentation(), null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(), null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);
        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week2.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week3.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctedAbsolutePathWithoutPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " /CS2104";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath = openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        // Expected result
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1 = new SuggestionItemImpl(toCs2103Week1.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(), null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(), null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);
        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + toCs2103.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week2.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week3.getStringRepresentation());
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctRelativePathWithPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " -t Lecture";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath = openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        //Expected result
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();

        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctRelativePathWithoutPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " Lecture";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath = openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        //Expected result
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();

        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctedRelativePathWithPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " " + PREFIX_TITLE + " Lectre";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath =
                openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        //Expected result
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();

        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

    @Test
    public void parse_correctedRelativePathWithoutPrefix_returnsOpenSuggestionCommand() throws ParseException {
        String arg = " Lectre";
        model.setInput(COMMAND_WORD + arg);
        SuggestionCommand commandCorrectPath =
                openSuggestionCommandParser.parse(arg);
        assertTrue(commandCorrectPath instanceof OpenSuggestionCommand);

        commandCorrectPath.execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE), model.responseTextProperty().getValue());

        //Expected result
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();

        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // check display text
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(COMMAND_WORD + " " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

}
