package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;

public class AddIntervieweeCommandParser {
    public AddIntervieweeCommand parse(String arguments) throws ParseException {
        /*String argumentBeforePrefix;
        try {
            argumentBeforePrefix = ParserUtil.parseArgumentsBeforePrefix(arguments);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIntervieweeCommand.MESSAGE_USAGE), pe);
        }*/
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_ALIAS);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS) || !argMultimap.getPreamble().isEmpty()) {
            System.out.println("INITUH");
            return new AddIntervieweeCommand(arguments);
        } else {
            System.out.println("APA GITU LOH");

            return new AddIntervieweeCommand(arguments, argMultimap.getValue(PREFIX_ALIAS).get());
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
