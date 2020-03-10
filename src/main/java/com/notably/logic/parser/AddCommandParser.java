package com.notably.logic.parser;

import static com.notably.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.notably.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static com.notably.logic.parser.CliSyntax.PREFIX_EMAIL;
import static com.notably.logic.parser.CliSyntax.PREFIX_NAME;
import static com.notably.logic.parser.CliSyntax.PREFIX_PHONE;
import static com.notably.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import com.notably.logic.commands.AddCommand;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }


        return new AddCommand("");
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
