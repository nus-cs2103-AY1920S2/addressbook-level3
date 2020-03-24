package csdev.couponstash.logic.parser;

import static csdev.couponstash.logic.commands.SetPrefCommand.MESSAGE_MISSING_VALUES;
import static csdev.couponstash.logic.commands.SetPrefCommand.MESSAGE_USAGE;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.SetPrefCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

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
        String newSymbol = argMultimap.getValue(CliSyntax.PREFIX_MONEY_SYMBOL)
                .orElseThrow(() -> new ParseException(MESSAGE_MISSING_VALUES
                        + "\n" + MESSAGE_USAGE));
        return new SetPrefCommand(newSymbol);
    }
}
