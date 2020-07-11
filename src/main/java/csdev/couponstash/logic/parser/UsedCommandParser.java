package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.UsedCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;

/**
 * Parses input arguments and creates a new UsedCommand object
 */
public class UsedCommandParser implements Parser<UsedCommand> {
    private final String moneySymbol;

    /**
     * Constructor for a UsedCommandParser. Requires the
     * money symbol set in UserPrefs as this will be
     * used as the prefix for the monetary amount
     * in the savings field.
     * @param moneySymbol String representing the money symbol.
     */
    public UsedCommandParser(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a UsedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UsedCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Prefix moneySymbolPrefix = new Prefix(moneySymbol);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, moneySymbolPrefix);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Optional<String> savingsArgument = argMultimap.getValue(moneySymbolPrefix);

            if (savingsArgument.isEmpty()) {
                return new UsedCommand(index);
            }

            MonetaryAmount originalAmount =
                    ParserUtil.parseMonetaryAmount(argMultimap.getValue(moneySymbolPrefix).get());
            return new UsedCommand(index, originalAmount);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(
                            pe.getMessage() + "\n\n" + MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format(UsedCommand.MESSAGE_USAGE, moneySymbol, moneySymbol)
                    ),
                    pe
            );
        }
    }
}
