package nasa.logic.parser;

import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.logic.commands.ListCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parser for {@code ListCommand}.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses user input command and returns a {@code ListCommandParser}.
     * @param args user input command
     * @return {@code ListCommandParser}
     * @throws ParseException
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        ModuleCode moduleCode = null;

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        }
        return new ListCommand(moduleCode);
    }
}
