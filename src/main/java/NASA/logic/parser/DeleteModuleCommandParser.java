package NASA.logic.parser;


import static java.util.Objects.requireNonNull;

import NASA.logic.commands.DeleteActivityCommand;
import NASA.commons.core.index.Index;
import NASA.logic.commands.DeleteActivityCommand;
import NASA.logic.commands.DeleteModuleCommand;
import NASA.logic.parser.ParserUtil;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.module.ModuleCode;
import seedu.address.logic.commands.EditCommand;

import java.util.ArrayList;

import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
