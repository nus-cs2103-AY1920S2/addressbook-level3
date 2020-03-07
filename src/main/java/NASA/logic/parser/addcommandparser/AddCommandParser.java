package NASA.logic.parser.addcommandparser;

import NASA.logic.commands.addcommands.AddCommand;
import NASA.logic.parser.ArgumentMultimap;
import NASA.logic.parser.Parser;
import NASA.logic.parser.Prefix;

import java.util.stream.Stream;

public abstract class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
