package com.notably.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Contains helper methods for testing suggestion feature.
 */
public class SuggestionTestUtil {
    private static AbsolutePath toCs2103;
    private static AbsolutePath toCs3230;
    private static AbsolutePath toCs2103Week1;
    private static AbsolutePath toCs2103Week2;
    private static AbsolutePath toCs2103Week3;
    private static AbsolutePath toCs2103Week1Lecture;
    private static Model model;

    public static void setUp() throws InvalidPathException {
        // Set up paths
        setUpPaths();

        // Set up model
        setUpModel();

        // Add test data to model
        addTestDataToModel();
    }

    public static void setUpPaths() {
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs3230 = AbsolutePath.fromString("/CS3230");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");
        toCs2103Week3 = AbsolutePath.fromString("/CS2103/Week3");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");
    }

    public static void setUpModel() {
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);
    }

    /**
     * Adds the test data to the model.
     */
    public static void addTestDataToModel() {
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
    }

    public static AbsolutePath getToCs2103() {
        return toCs2103;
    }

    public static Model getModel() {
        return model;
    }

    public static List<SuggestionItem> getExpectedSuggestionsToCs2103() {
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1 = new SuggestionItemImpl(toCs2103Week1.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new
                SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(), null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(),
                null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(),
                null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);
        expectedSuggestions.add(cs2103Week1Lecture);

        return expectedSuggestions;
    }

    public static List<String> getExpectedInputsToCs2103(String commandWord, boolean hasPrefixTitle) {
        List<String> expectedInputs = new ArrayList<>();
        String prefix = "";

        if (hasPrefixTitle) {
            prefix = "-t ";
        }

        expectedInputs.add(commandWord + " " + prefix + toCs2103.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix + toCs2103Week1.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix + toCs2103Week2.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix + toCs2103Week3.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix + toCs2103Week1Lecture.getStringRepresentation());

        return expectedInputs;
    }

    /**
     * Checks the correctness of the suggestions.
     * @param expectedSuggestions The expected suggestions list.
     * @param suggestions The actual suggestions list.
     */
    public static void testSuggestions(List<SuggestionItem> expectedSuggestions, List<SuggestionItem> suggestions) {
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
        }
    }

    /**
     * Checks the correctness of the input stored in CommandInputModel for each suggestion.
     * @param expectedInputs The expected list of inputs.
     * @param suggestions The actual suggestions list.
     */
    public static void testInputs(List<String> expectedInputs, List<SuggestionItem> suggestions) {
        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }
}
