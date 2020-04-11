package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.SortCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the SortCommand
 * and returns an SortCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class SortCommandParser implements Parser<SortCommand> {

    // Prefixes that we can sort by.
    private static final Prefix[] supportedPrefixes = new Prefix[] {
        PREFIX_EXPIRY_DATE,
        PREFIX_NAME,
        PREFIX_REMIND
    };

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer
                .tokenize(args, supportedPrefixes);

        return new SortCommand(getPrefix(argMultiMap));
    }

    /**
     * Get the prefix in the argumentMultiMap.
     * @param argumentMultimap
     * @return Prefix in the argumentMultiMap
     * @throws ParseException Thrown if argumentMultiMap contains 0 or more than one valid prefix
     */
    private static Prefix getPrefix(ArgumentMultimap argumentMultimap) throws ParseException {
        assert argumentMultimap != null;

        if (hasOnlyOneValidPrefix(argumentMultimap)) {
            for (Prefix prefix : supportedPrefixes) {
                if (argumentMultimap.getValue(prefix).isPresent()) {
                    return prefix;
                }
            }
        }

        // Has more than one prefixes or has none
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SortCommand.MESSAGE_USAGE)
        );
    }

    /**
     * Check if ArgumentMultimap only has one valid prefix. Valid prefixes
     * are those contained in the array supportedPrefixes
     * @param argumentMultimap
     * @return True if ArgumentMultimap only has one valid prefix, false otherwise
     */
    private static boolean hasOnlyOneValidPrefix(ArgumentMultimap argumentMultimap) {
        int count = 0;

        for (Prefix prefix : supportedPrefixes) {
            if (argumentMultimap.getValue(prefix).isPresent()) {
                count++;
            }
        }

        return count == 1;
    }
}
