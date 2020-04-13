package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Stream;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.TimeSpent;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        PREFIX_DESCRIPTION,
                        PREFIX_MODULE_CODE,
                        PREFIX_PRIORITY,
                        PREFIX_DATETIME,
                        PREFIX_TAG
                );

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_MODULE_CODE, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        //noinspection OptionalGetWithoutIsPresent guaranteed present return from ParserUtil
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        //noinspection OptionalGetWithoutIsPresent guaranteed present return from ParserUtil
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        //noinspection OptionalGetWithoutIsPresent guaranteed present return from ParserUtil
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Priority priority = Priority.MEDIUM;
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Task task = new Task(description, Status.INCOMPLETE, dateTime, moduleCode, priority, tagList,
            new TimeSpent(Duration.ZERO));

        return new AddCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
