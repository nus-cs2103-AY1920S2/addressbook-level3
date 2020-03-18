package com.notably.logic.parser;

import static com.notably.logic.parser.CliSyntax.PREFIX_BODY;
import static com.notably.logic.parser.CliSyntax.PREFIX_JUMP;
import static com.notably.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import com.notably.logic.commands.NewCommand;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

/**
 * Represents a Parser for New Command.
 */
public class NewCommandParser implements CommandParser<NewCommand> {

    /**
     * Parse input and create NewCommand.
     * @param args userInput used to create block.
     * @return NewCommand
     * @throws ParseException when input is invalid.
     */
    public NewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_BODY, PREFIX_JUMP);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_BODY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        String title = argMultimap.getValue(PREFIX_TITLE).get();
        String body = argMultimap.getValue(PREFIX_BODY).get();

        Block block = new BlockImpl(new Title(title), new Body(body));

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_BODY, PREFIX_JUMP)) {
            return new NewCommand(block, false);
        }
        return new NewCommand(block, true);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
