package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.notably.commons.core.path.RelativePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.commands.Command;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Represent a Parser for OpenCommand.
 */
public class OpenCommandParser implements CommandParser {

    /**
     * TODO: integrate with CorrectionEngine.
     * Creates OpenCommand with user input.
     * @param args to be parse by into CorrectionEngine.
     * @return List of command containing OpenCammnd.
     * @throws ParseException
     */
    public List<Command> parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid input"));
        }

        String titles = argMultimap.getValue(PREFIX_TITLE).get();
        List<Command> openCommandList = new ArrayList<>();

        try {
            RelativePath path = RelativePath.fromString(titles);
            openCommandList.add(new OpenCommand(path));
            return openCommandList;
        } catch (InvalidPathException ex) {
            throw new ParseException(ex.getMessage());
        }

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
