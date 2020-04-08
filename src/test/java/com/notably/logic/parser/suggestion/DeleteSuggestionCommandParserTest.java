package com.notably.logic.parser.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.suggestion.DeleteSuggestionCommand;
import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.logic.correction.AbsolutePathCorrectionEngine;
import com.notably.logic.correction.CorrectionEngine;
import com.notably.logic.suggestion.SuggestionTestUtil;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;

public class DeleteSuggestionCommandParserTest {
    private static AbsolutePath toCs2103t;
    private static DeleteSuggestionCommandParser deleteSuggestionCommandParser;
    private static Model model;

    private static final String COMMAND_WORD = "delete";
    private static final String RESPONSE_MESSAGE = "Delete a note";
    private static final String RESPONSE_MESSAGE_WITH_TITLE = "Delete a note titled \"%s\"";
    private static final String ERROR_MESSAGE_CANNOT_DELETE_NOTE = "Cannot delete \"%s\". Invalid path.";

    private static final int CORRECTION_THRESHOLD = 2;
    private static final boolean USE_FORWARD_MATCHING = true;

    @BeforeAll
    public static void setUp() {
        model = SuggestionTestUtil.getModel();
        toCs2103t = SuggestionTestUtil.getToCs2103t();

        // initialize parser
        CorrectionEngine<AbsolutePath> pathCorrectionEngine = new AbsolutePathCorrectionEngine(model,
                CORRECTION_THRESHOLD, USE_FORWARD_MATCHING);
        deleteSuggestionCommandParser = new DeleteSuggestionCommandParser(model, pathCorrectionEngine);
    }

    @Test
    public void parse_correctAbsolutePathWithPrefix_returnsDeleteSuggestionCommand() {
        String userInputWithoutPath = COMMAND_WORD + " " + PREFIX_TITLE + " ";
        String userInput = userInputWithoutPath + toCs2103t.getStringRepresentation();
        String arg = userInput.replace(COMMAND_WORD, "");

        model.setInput(userInput);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(String.format(RESPONSE_MESSAGE_WITH_TITLE, toCs2103t.getStringRepresentation())),
                model.responseTextProperty().getValue());

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSugForCs2103tPathInput();

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Test display text
        SuggestionTestUtil.testDisplayTexts(expectedSuggestions, suggestions, model);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsForCs2103tPathInput(userInputWithoutPath);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions, model);
    }

    @Test
    public void parse_correctAbsolutePathWithoutPrefix_returnsDeleteSuggestionCommand() {
        String userInputWithoutPath = COMMAND_WORD + " ";
        String userInput = userInputWithoutPath + toCs2103t.getStringRepresentation();
        String arg = userInput.replace(COMMAND_WORD, "");

        model.setInput(userInput);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(String.format(RESPONSE_MESSAGE_WITH_TITLE, toCs2103t.getStringRepresentation())),
                model.responseTextProperty().getValue());

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSugForCs2103tPathInput();

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Test display text
        SuggestionTestUtil.testDisplayTexts(expectedSuggestions, suggestions, model);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsForCs2103tPathInput(userInputWithoutPath);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions, model);
    }

    @Test
    public void parse_correctedAbsolutePathWithPrefix_returnsDeleteSuggestionCommand() {
        String userInputWithoutPath = COMMAND_WORD + " " + PREFIX_TITLE + " ";
        String path = "/Y2S2/CS2104";
        String userInput = userInputWithoutPath + path;
        String arg = userInput.replace(COMMAND_WORD, "");

        model.setInput(userInput);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(String.format(RESPONSE_MESSAGE_WITH_TITLE, path)),
                model.responseTextProperty().getValue());

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSugForCs2103tPathInput();

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Test display text
        SuggestionTestUtil.testDisplayTexts(expectedSuggestions, suggestions, model);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsForCs2103tPathInput(userInputWithoutPath);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions, model);
    }

    @Test
    public void parse_correctedAbsolutePathWithoutPrefix_returnsDeleteSuggestionCommand() {
        String userInputWithoutPath = COMMAND_WORD + " ";
        String path = "/Y2S2/CS2104";
        String userInput = userInputWithoutPath + path;
        String arg = userInput.replace(COMMAND_WORD, "");

        model.setInput(userInput);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(String.format(RESPONSE_MESSAGE_WITH_TITLE, path)),
                model.responseTextProperty().getValue());

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSugForCs2103tPathInput();

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Test display text
        SuggestionTestUtil.testDisplayTexts(expectedSuggestions, suggestions, model);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsForCs2103tPathInput(userInputWithoutPath);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions, model);
    }

    /*
    @Test
    public void parse_correctRelativePathWithPrefix_returnsDeleteSuggestionCommand() {
        String arg = " -t Lecture";
        model.setInput(COMMAND_WORD + arg);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE + " titled \"Lecture\""),
                model.responseTextProperty().getValue());

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
    public void parse_correctRelativePathWithoutPrefix_returnsDeleteSuggestionCommand() {
        String arg = "Lecture";
        model.setInput(COMMAND_WORD + " " + arg);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE + " titled \"" + arg + "\""),
                model.responseTextProperty().getValue());

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
    public void parse_correctedRelativePathWithPrefix_returnsDeleteSuggestionCommand() {
        String arg = " " + PREFIX_TITLE + " Lectre";
        model.setInput(COMMAND_WORD + arg);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE + " titled \"Lectre\""),
                model.responseTextProperty().getValue());

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
    public void parse_correctedRelativePathWithoutPrefix_returnsDeleteSuggestionCommand() {
        String arg = "Lectre";
        model.setInput(COMMAND_WORD + " " + arg);
        Optional<? extends SuggestionCommand> command = deleteSuggestionCommandParser.parse(arg);
        assertTrue(command.get() instanceof DeleteSuggestionCommand);

        command.get().execute(model);

        assertEquals(Optional.of(RESPONSE_MESSAGE + " titled \"" + arg + "\""),
                model.responseTextProperty().getValue());

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
    }*/
}
