package com.notably.logic.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public static Model getModel() {
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        Model model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        return model;
    }

    public static AbsolutePath getToCs2103t() {
        return TypicalBlockModel.PATH_TO_CS2103T;
    }

    public static List<SuggestionItem> getExpectedSugForCs2103tPathInput() {
        SuggestionItem cs2103t = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T.getStringRepresentation(), () -> {});
        SuggestionItem cs2103tLect = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_LECTURES.getStringRepresentation(), () -> {});
        SuggestionItem cs2103tTut = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIALS.getStringRepresentation(), () -> {});
        SuggestionItem cs2103tTut1 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1.getStringRepresentation(), () -> {});
        SuggestionItem cs2103tTut2 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2.getStringRepresentation(), () -> {});

        List<SuggestionItem> expectedSuggestions;
        SuggestionItem[] items = new SuggestionItem[]{cs2103t, cs2103tLect, cs2103tTut, cs2103tTut1, cs2103tTut2};
        expectedSuggestions = List.of(items);

        return expectedSuggestions;
    }

    public static List<String> getExpectedInputsForCs2103tPathInput(String userInputWithoutPath) {
        List<String> expectedInputs = new ArrayList<>();

        expectedInputs.add(userInputWithoutPath + TypicalBlockModel.PATH_TO_CS2103T.getStringRepresentation());
        expectedInputs.add(userInputWithoutPath + TypicalBlockModel.PATH_TO_CS2103T_LECTURES.getStringRepresentation());
        expectedInputs.add(userInputWithoutPath
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIALS.getStringRepresentation());
        expectedInputs.add(userInputWithoutPath
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1.getStringRepresentation());
        expectedInputs.add(userInputWithoutPath
                + TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2.getStringRepresentation());

        return expectedInputs;
    }

    /**
     * Checks the correctness of the suggestions.
     * @param expectedSuggestions The expected suggestions list.
     * @param suggestions The actual suggestions list.
     */
    public static void testSuggestions(List<SuggestionItem> expectedSuggestions, List<SuggestionItem> suggestions) {
        assertEquals(expectedSuggestions.stream().map(s -> s.getProperty("displayText")).collect(Collectors.toList()),
                suggestions.stream().map(s -> s.getProperty("displayText")).collect(Collectors.toList()));
    }

    /**
     * Checks the correctness of the input stored in CommandInputModel for each suggestion.
     * @param expectedInputs The expected list of inputs.
     * @param suggestions The actual suggestions list.
     */
    public static void testInputs(List<String> expectedInputs, List<SuggestionItem> suggestions, Model model) {
        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }
}
