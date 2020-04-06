package com.notably.logic.commands.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.SuggestionTestUtil;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;

public class DeleteSuggestionCommandTest {
    private static AbsolutePath toCs2103;
    private static Model model;

    private static final String COMMAND_WORD = "delete";

    @BeforeAll
    public static void setUpTree() {
        SuggestionTestUtil.setUp();
        toCs2103 = SuggestionTestUtil.getToCs2103();
        model = SuggestionTestUtil.getModel();
    }

    @AfterEach
    public void clearSuggestions() {
        model.clearSuggestions();
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSuggestionCommand(null, "title"));
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSuggestionCommand(toCs2103,
            null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteSuggestionCommand deleteSuggestionCommand = new DeleteSuggestionCommand(toCs2103,
            toCs2103.getStringRepresentation());
        assertThrows(NullPointerException.class, () -> deleteSuggestionCommand.execute(null));
    }

    @Test
    public void execute_blankOldTitle_generatesEmptySuggestion() {
        DeleteSuggestionCommand deleteSuggestionCommand = new DeleteSuggestionCommand(toCs2103, "    ");
        deleteSuggestionCommand.execute(model);

        assertTrue(model.getSuggestions().size() == 0);
    }

    @Test
    public void execute_correctAbsolutePathWithPrefix_generatesResponseCorrectly() {
        model.setInput(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103.getStringRepresentation());
        DeleteSuggestionCommand deleteSuggestionCommand = new DeleteSuggestionCommand(toCs2103,
            toCs2103.getStringRepresentation());
        deleteSuggestionCommand.execute(model);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSuggestionsToCs2103();

        // Test suggestions
        SuggestionTestUtil.testSuggestions(expectedSuggestions, suggestions);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsToCs2103(COMMAND_WORD, true);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions);
    }

    @Test
    public void execute_correctAbsolutePathWithoutPrefix_generatesResponseCorrectly() {
        model.setInput(COMMAND_WORD + " " + toCs2103.getStringRepresentation());
        DeleteSuggestionCommand deleteSuggestionCommand = new DeleteSuggestionCommand(toCs2103,
                toCs2103.getStringRepresentation());
        deleteSuggestionCommand.execute(model);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSuggestionsToCs2103();

        // Test suggestions
        SuggestionTestUtil.testSuggestions(expectedSuggestions, suggestions);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsToCs2103(COMMAND_WORD, false);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions);
    }
}
