package com.notably.logic.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.testutil.TypicalBlockModel;

/**
 * Contains helper methods for testing suggestion feature.
 */
public class SuggestionTestUtil {
    private static Model model;

    public static Model getModel() {
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        return model;
    }

    public static AbsolutePath getToCs2103t() {
        return TypicalBlockModel.PATH_TO_CS2103T;
    }

    public static List<SuggestionItem> getExpectedSuggestionsToCs2103t() {
        SuggestionItem cs2103t = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T.getStringRepresentation(), null);
        SuggestionItem cs2103tLect = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_LECTURES.getStringRepresentation(), null);
        SuggestionItem cs2103tTut = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIALS.getStringRepresentation(), null);
        SuggestionItem cs2103tTut1 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1.getStringRepresentation(), null);
        SuggestionItem cs2103tTut2 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2.getStringRepresentation(), null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103t);
        expectedSuggestions.add(cs2103tLect);
        expectedSuggestions.add(cs2103tTut);
        expectedSuggestions.add(cs2103tTut1);
        expectedSuggestions.add(cs2103tTut2);

        return expectedSuggestions;
    }

    public static List<String> getExpectedInputsToCs2103t(String commandWord, boolean hasPrefixTitle) {
        List<String> expectedInputs = new ArrayList<>();
        String prefix = "";

        if (hasPrefixTitle) {
            prefix = "-t ";
        }

        expectedInputs.add(commandWord + " " + prefix + TypicalBlockModel.PATH_TO_CS2103T.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix
                + TypicalBlockModel.PATH_TO_CS2103T_LECTURES.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIALS.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1.getStringRepresentation());
        expectedInputs.add(commandWord + " " + prefix
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2.getStringRepresentation());

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
