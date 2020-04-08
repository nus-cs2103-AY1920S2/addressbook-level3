package com.notably.logic.parser.suggestion;

import org.junit.jupiter.api.BeforeAll;

import com.notably.logic.suggestion.SuggestionTestUtil;
import com.notably.model.Model;

public class NewSuggestionCommandParserTest {
    private static NewSuggestionCommandParser newSuggestionCommandParser;
    private static Model model;

    private static final String RESPONSE_MESSAGE = "Create a new note";
    private static final String RESPONSE_MESSAGE_WITH_TITLE = "Create a new note titled \"%s\".";
    private static final String RESPONSE_MESSAGE_WITH_TITLE_AND_OPEN = "Create a new note titled \"%s\" and open it.";
    private static final String ERROR_MESSAGE = "\"%s\" is an invalid command format. "
            + "The correct format is \"new -t TITLE [-b BODY] [-o]\"";
    private static final String ERROR_MESSAGE_INVALID_TITLE = "Title \"%s\" is invalid. "
            + "Titles should only contain alphanumeric characters";

    @BeforeAll
    public static void setUp() {
        model = SuggestionTestUtil.getModel();

        // initialize parser
        newSuggestionCommandParser = new NewSuggestionCommandParser(model);
    }

    /*
    @Test
    public void parse() {
        String arg = "";

        Optional<? extends SuggestionCommand> command = newSuggestionCommandParser.parse(arg);

        assertEquals(Optional.of(RESPONSE_MESSAGE),
                model.responseTextProperty().getValue());
    }*/
}
