package com.notably.logic.commands.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.suggestion.SuggestionTestUtil;
import com.notably.model.Model;
import com.notably.model.suggestion.SuggestionItem;

public class OpenSuggestionCommandTest {
    private static AbsolutePath toCs2103t;
    private static Model model;

    private static final String COMMAND_WORD = "open";

    @BeforeAll
    public static void setUpTree() {
        toCs2103t = SuggestionTestUtil.getToCs2103t();
        model = SuggestionTestUtil.getModel();
    }

    @AfterEach
    public void clearSuggestions() {
        model.clearSuggestions();
        System.out.println("inside clear sug");
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenSuggestionCommand(null, "title"));
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenSuggestionCommand(toCs2103t,
                null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103t,
                toCs2103t.getStringRepresentation());
        assertThrows(NullPointerException.class, () -> openSuggestionCommand.execute(null));
    }

    @Test
    public void execute_blankOldTitle_generatesEmptySuggestion() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103t, "    ");
        openSuggestionCommand.execute(model);

        assertTrue(model.getSuggestions().size() == 0);
    }

    @Test
    public void execute_correctAbsolutePathWithPrefix_generatesResponseCorrectly() {
        model.setInput(COMMAND_WORD + " " + PREFIX_TITLE + " " + toCs2103t.getStringRepresentation());
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103t,
                toCs2103t.getStringRepresentation());
        openSuggestionCommand.execute(model);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSuggestionsToCs2103t();

        // Test suggestions
        SuggestionTestUtil.testSuggestions(expectedSuggestions, suggestions);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsToCs2103t(COMMAND_WORD, true);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions);
    }

    @Test
    public void execute_correctAbsolutePathWithoutPrefix_generatesResponseCorrectly() {
        model.setInput(COMMAND_WORD + " " + toCs2103t.getStringRepresentation());
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103t,
                toCs2103t.getStringRepresentation());
        openSuggestionCommand.execute(model);

        List<SuggestionItem> suggestions = model.getSuggestions();

        // Expected suggestions
        List<SuggestionItem> expectedSuggestions = SuggestionTestUtil.getExpectedSuggestionsToCs2103t();

        // Test suggestions
        SuggestionTestUtil.testSuggestions(expectedSuggestions, suggestions);

        // Expected inputs
        List<String> expectedInputs = SuggestionTestUtil.getExpectedInputsToCs2103t(COMMAND_WORD, false);

        // Test inputs
        SuggestionTestUtil.testInputs(expectedInputs, suggestions);
    }
}
