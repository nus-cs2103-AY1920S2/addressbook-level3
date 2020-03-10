package nasa.logic.parser;


import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import nasa.logic.commands.DeleteModuleCommand;
import nasa.logic.parser.exceptions.ParseException;

public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns a DeleteModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        /*TODO add implementation
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());

         */
        return new DeleteModuleCommand(new ArrayList<>());
    }
}
