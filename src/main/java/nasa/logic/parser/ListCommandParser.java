package nasa.logic.parser;

import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.ArrayList;
import nasa.logic.commands.ListCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;
import nasa.model.module.NameContainsKeywordsPredicate;

public class ListCommandParser implements Parser<ListCommand> {

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
