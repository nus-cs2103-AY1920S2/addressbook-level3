package com.notably.logic.commands.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.suggestion.SuggestionTestUtil;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;
import com.notably.testutil.TypicalBlockModel;

public class SearchSuggestionCommandTest {
    private static Model model;

    private static final String COMMAND_WORD = "search";

    @BeforeAll
    public static void setUp() {
        model = SuggestionTestUtil.getModel();
    }

    @Test
    public void constructor_nullKeyword_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SearchSuggestionCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        SearchSuggestionCommand searchSuggestionCommand = new SearchSuggestionCommand("keywords");
        assertThrows(NullPointerException.class, () -> searchSuggestionCommand.execute(null));
    }

    @Test
    public void execute_generatesResponseCorrectly() {
        SearchSuggestionCommand searchSuggestionCommand = new SearchSuggestionCommand("fALsE");
        searchSuggestionCommand.execute(model);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        SuggestionItem cs2103tTut1 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1.getStringRepresentation(), 1, null);
        SuggestionItem cs2103tTut2 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2.getStringRepresentation(), 2, null);
        SuggestionItem cs2106Tut1 = new SuggestionItemImpl(
                TypicalBlockModel.PATH_TO_CS2106_TUTORIAL_1.getStringRepresentation(), 1, null);
        expectedSuggestions.add(cs2103tTut1);
        expectedSuggestions.add(cs2103tTut2);
        expectedSuggestions.add(cs2106Tut1);

        // Test displayText and frequency
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(expectedSuggestion.getProperty("displayText"), suggestion.getProperty("displayText"));
            assertEquals(expectedSuggestion.getProperty("frequency"), suggestion.getProperty("frequency"));
        }

        // Expected currently open paths
        List<AbsolutePath> expectedCurrentlyOpenPaths = new ArrayList<>();
        expectedCurrentlyOpenPaths.add(TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_1);
        expectedCurrentlyOpenPaths.add(TypicalBlockModel.PATH_TO_CS2103T_TUTORIAL_2);
        expectedCurrentlyOpenPaths.add(TypicalBlockModel.PATH_TO_CS2106_TUTORIAL_1);

        // Test Runnable action and check currentlyOpenPath
        for (int i = 0; i < expectedCurrentlyOpenPaths.size(); i++) {
            SuggestionItem suggestion = suggestions.get(i);
            suggestion.getAction().run();

            assertEquals(expectedCurrentlyOpenPaths.get(i), model.getCurrentlyOpenPath());
        }
    }
}
