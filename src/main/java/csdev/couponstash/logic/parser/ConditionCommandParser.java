package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.logic.commands.ConditionCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.Condition;


/**
 * Parses input arguments and creates a new {@code ConditionCommand} object
 */
public class ConditionCommandParser implements Parser<ConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ConditionCommand}
     * and returns a ConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConditionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONDITION);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ConditionCommand.MESSAGE_USAGE), ive);
        }
        String condition = argMultimap.getValue(PREFIX_CONDITION).orElse("");

        return new ConditionCommand(index, new Condition(condition));
    }


}
