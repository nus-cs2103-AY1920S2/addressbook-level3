package com.notably.logic.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.commons.path.exceptions.InvalidPathException;
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

public class SuggestionEngineImplTest {
    private static AbsolutePath toRoot;
    private static AbsolutePath toCs2103;
    private static AbsolutePath toCs3230;
    private static AbsolutePath toCs2103Week1;
    private static AbsolutePath toCs2103Week2;
    private static AbsolutePath toCs2103Week3;
    private static AbsolutePath toCs2103Week1Lecture;
    private static Model model;
    private static SuggestionEngine suggestionEngine;

    @BeforeAll
    public static void setUp() throws InvalidPathException {
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

        // Set up SuggestionEngine
        suggestionEngine = new SuggestionEngineImpl(model);
    }

    @Test
    public void suggest_inputLengthTooShort_suggestionsAndResponseTextCleared() {
        model.setInput("");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());

        model.setInput("o");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());
    }

    @Test
    public void suggest_uncorrectedCommand_returnErrorSuggestionCommand() {
        model.setInput("opensesame -t CS2103");

        // Expected result
        String expectedResponseText = "Invalid command. To see the list of available commands, type: help";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedOpenCommandValidArgs_returnOpenSuggestionCommand() {
        model.setInput("oen -t Lecture");

        // Test response text
        String expectedResponseText = "Open a note";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());

        // Expected result
        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
                null);
        expectedSuggestions.add(cs2103Week1Lecture);

        List<SuggestionItem> suggestions = model.getSuggestions();

        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add("oen -t " + toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }

    }

    @Test
    public void suggest_correctedOpenCommandInvalidArgs_returnErrorSuggestionCommand() {
        model.setInput("opn -t CS2222");

        // Expected result
        String expectedResponseText = "Invalid path";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedNewCommand_returnNewSuggestionCommand() {
        model.setInput("nw -t NewNote");

        // Expected result
        String expectedResponseText = "Create a new note";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedEditCommand_returnEditSuggestionCommand() {
        model.setInput("edt -t NewNote -b lorem ipsum");

        // Expected result
        String expectedResponseText = "Edit a currently open note";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedHelpCommand_returnHelpSuggestionCommand() {
        model.setInput("hAlp");

        // Expected result
        String expectedResponseText = "Display a list of available commands";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedExitCommand_returnExitSuggestionCommand() {
        model.setInput("ex");

        // Expected result
        String expectedResponseText = "Exit the application";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
