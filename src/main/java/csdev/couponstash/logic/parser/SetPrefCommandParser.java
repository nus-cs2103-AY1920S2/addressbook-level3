package csdev.couponstash.logic.parser;

import static csdev.couponstash.logic.commands.SetPrefCommand.MESSAGE_MISSING_VALUES;
import static csdev.couponstash.logic.commands.SetPrefCommand.MESSAGE_USAGE;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.SetPrefCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetPrefCommand object,
 * given that the input arguments are valid and there is at least
 * one preference to be changed
 */
public class SetPrefCommandParser implements Parser<SetPrefCommand> {

    /**
     * Parses the raw String from Coupon Stash's command line
     * into a SetPrefCommand.
     *
     * @param userInput String representing raw input from user
     *                  (without the command word).
     * @return Returns a new SetPrefCommand with the fields that
     *         need to be changed, as supplied by the user.
     * @throws ParseException If zero fields are supplied in
     *                        the SetPrefCommand.
     */
    @Override
    public SetPrefCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
             ArgumentTokenizer.tokenize(
                     userInput,
                     CliSyntax.PREFIX_MONEY_SYMBOL);
        ParseException emptySymbolException = new ParseException(MESSAGE_MISSING_VALUES
                + "\n" + MESSAGE_USAGE);
        String newSymbol = argMultimap.getValue(CliSyntax.PREFIX_MONEY_SYMBOL)
                .map(String::trim)
                .orElseThrow(() -> emptySymbolException);

        if (newSymbol.isBlank()) {
            throw emptySymbolException;
        }
        return new SetPrefCommand(newSymbol);
    }
}
