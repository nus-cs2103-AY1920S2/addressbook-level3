package com.notably.logic.parser.suggestion;

import static com.notably.logic.parser.CliSyntax.PREFIX_BODY;
import static com.notably.logic.parser.CliSyntax.PREFIX_JUMP;
import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.Command;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.commands.suggestion.NewSuggestionCommand;
import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.logic.parser.ArgumentMultimap;
import com.notably.logic.parser.ArgumentTokenizer;
import com.notably.logic.parser.ParserUtil;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

/**
 * Represents a Parser for New Command.
 */
public class NewSuggestionCommandParser implements SuggestionCommandParser<SuggestionCommand> {
    private Model model;

    private static final String RESPONSE_MESSAGE = "Create a new note";
    private static final String RESPONSE_MESSAGE_WITH_TITLE = RESPONSE_MESSAGE + " with title ";

    public NewSuggestionCommandParser(Model model) {
        this.model = model;
    }
    
    /**
     * Parses input and displays the appropriate response text.
     * @param userInput .
     * @return List of command to execute.
     * @throws ParseException when input is invalid.
     */
    public Optional<SuggestionCommand> parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE, PREFIX_BODY, PREFIX_JUMP);

        String title;
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            title = userInput.trim();
        } else {
            title = argMultimap.getValue(PREFIX_TITLE).get();
        }

        if (title.isBlank()) {
            model.setResponseText(RESPONSE_MESSAGE);
        } else if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_JUMP)) { // If user does NOT type "-o"
            model.setResponseText(RESPONSE_MESSAGE_WITH_TITLE + "\"" + title + "\"");
        } else {
            model.setResponseText(RESPONSE_MESSAGE_WITH_TITLE + "\"" + title + "\"" + " and open this note");
        }

        return Optional.empty();

    }
}
