package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.expensesplitter.logic.parser.CliSyntax.PREFIX_INDEX;

import java.math.BigDecimal;
import java.util.stream.Stream;

import seedu.eylah.expensesplitter.logic.commands.PaidCommand;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Parses input arguments and creates a new PaidCommandItem object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {

    /**
     * Parsing.
     * @param args
     * @return
     * @throws ParseException
     */
    @Override
    public PaidCommand parse(String args) throws ParseException {

        System.out.println(args.substring(1));
        System.out.println(args.substring(2).trim());


        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        /*
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }



        int index = ParserUtil.parseIndexV2(argMultimap.getValue(PREFIX_INDEX).get());

         */


        Person p = new Person(new Name("Willy"), new Amount(new BigDecimal("1")));

        return new PaidCommand(p);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}




