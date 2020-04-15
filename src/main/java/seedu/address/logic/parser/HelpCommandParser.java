package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HELP;

import seedu.address.logic.commands.HelpCommand;

/**
 * Parses input arguments and creates a new {@code AddInfoCommand} object
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     */
    public HelpCommand parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HELP);
        int type = Integer.parseInt(argMultimap.getValue(PREFIX_HELP).orElse("-1"));

        return new HelpCommand(type);
    }

}
