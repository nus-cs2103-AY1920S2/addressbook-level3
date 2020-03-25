package com.notably.logic.suggestion.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.RelativePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.parser.ArgumentMultimap;
import com.notably.logic.parser.ArgumentTokenizer;
import com.notably.logic.parser.ParserUtil;
import com.notably.logic.parser.Prefix;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.logic.suggestion.commands.OpenSuggestionCommand;
import com.notably.model.Model;
import com.notably.model.ModelManager;

/**
 * Represents a Parser for OpenSuggestionCommand.
 */
public class OpenSuggestionCommandParser implements SuggestionCommandParser<OpenSuggestionCommand> {
    Model model;

    public OpenSuggestionCommandParser(Model model) {
        this.model = model;
    }

    /**
     * Parses user input in the context of the OpenSuggestionCommand.
     * @param userInput The user's input.
     * @return An OpenSuggestionCommand object with an absolute path.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public OpenSuggestionCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TITLE);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid input"));
        }

        String titles = argMultimap.getValue(PREFIX_TITLE).get();
        OpenSuggestionCommand openSuggestionCommand;

        try {
            if (titles.charAt(0) == '/') {
                openSuggestionCommand = new OpenSuggestionCommand(AbsolutePath.fromString(titles));
            } else {
                // convert relative path to absolute path first, as suggestion commands only deal with absolute path.
                RelativePath relativePath = RelativePath.fromString(titles);
                AbsolutePath currentWorkingPath = model.getCurrentlyOpenPath();
                AbsolutePath absolutePath = AbsolutePath.fromRelativePath(relativePath, currentWorkingPath);

                openSuggestionCommand = new OpenSuggestionCommand(absolutePath);
            }
            return openSuggestionCommand;
        } catch (InvalidPathException ex) {
            throw new ParseException(ex.getMessage());
        }
    }
}
