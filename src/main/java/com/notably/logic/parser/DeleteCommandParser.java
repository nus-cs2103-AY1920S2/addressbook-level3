package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;

import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser<DeleteCommand> {

    /**
     * TODO: integrate with CorrectionEngine.
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public List<DeleteCommand> parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        String title = argMultimap.getValue(PREFIX_TITLE).get();
        List<DeleteCommand> deleteCommand = new ArrayList<>();

        deleteCommand.add(new DeleteCommand(ParserUtil.createPath(title)));
        return deleteCommand;
    }
}
