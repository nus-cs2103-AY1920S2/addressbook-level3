package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;

import com.notably.logic.commands.OpenCommand;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Represent a Parser for OpenCommand.
 */
public class OpenCommandParser implements CommandParser<OpenCommand> {

    /**
     * TODO: integrate with CorrectionEngine.
     * Creates OpenCommand with user input.
     * @param args to be parse by into CorrectionEngine.
     * @return List of command containing OpenCammnd.
     * @throws ParseException
     */
    public List<OpenCommand> parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid input"));
        }

        String titles = argMultimap.getValue(PREFIX_TITLE).get();
        List<OpenCommand> openCommandList = new ArrayList<>();

        openCommandList.add(new OpenCommand(ParserUtil.createPath(titles)));
        return openCommandList;
    }
}
