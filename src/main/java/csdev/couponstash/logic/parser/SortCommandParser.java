package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.SortCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_EXPIRY_DATE);

        if (
                argMultiMap.getValue(PREFIX_NAME).isPresent() &&
                        !argMultiMap.getValue(PREFIX_EXPIRY_DATE).isPresent()
        ) {
            // Only has PREFIX_NAME
            return new SortCommand(PREFIX_NAME);
        } else if (
                !argMultiMap.getValue(PREFIX_NAME).isPresent() &&
                        argMultiMap.getValue(PREFIX_EXPIRY_DATE).isPresent()
        ) {
            // Only has PREFIX_EXPIRY_DATE
            return new SortCommand(PREFIX_EXPIRY_DATE);
        } else {
            // Has both prefixes or has none
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SortCommand.MESSAGE_USAGE)
            );
        }
    }
}
