package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.customerCommands.FindCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.customer.AddressContainsKeywordsPredicate;
import seedu.address.model.customer.EmailContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCustomerCommand object
 */
public class FindCommandParser implements Parser<FindCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCustomerCommand
     * and returns a FindCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCustomerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            return new FindCustomerCommand(new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME)));
        } else if (!argMultimap.getAllValues(PREFIX_ADDRESS).isEmpty()) {
            return new FindCustomerCommand(new AddressContainsKeywordsPredicate(
                    argMultimap.getAllValues(PREFIX_ADDRESS)));
        } else if (!argMultimap.getAllValues(PREFIX_EMAIL).isEmpty()) {
            return new FindCustomerCommand(new EmailContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_EMAIL)));
        }

        return new FindCustomerCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
