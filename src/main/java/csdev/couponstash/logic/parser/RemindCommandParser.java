package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.logic.commands.RemindCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;


public class RemindCommandParser implements Parser<RemindCommand>{

    public RemindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMIND);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemindCommand.MESSAGE_USAGE), ive);
        }
        String remark = argMultimap.getValue(PREFIX_REMIND).orElse("");

        return new RemindCommand(index, remark);
    }


}
