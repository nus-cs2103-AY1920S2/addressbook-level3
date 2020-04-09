package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DURATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_REMARK;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.AddCommand;
import fithelper.logic.parser.exceptions.ParseException;

import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        logger.info(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME, PREFIX_TIME, PREFIX_LOCATION,
                        PREFIX_CALORIE, PREFIX_REMARK, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_NAME, PREFIX_TIME, PREFIX_LOCATION, PREFIX_CALORIE)) {
            logger.info("throwing the format exception");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Calorie calorie = ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIE).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(""));
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).orElse("1"));
        Entry entry = new Entry(type, name, time, location, calorie, remark, duration);
        return new AddCommand(entry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (!argumentMultimap.getValue(prefix).isPresent()) {
                logger.info(prefix.toString());
            } else {
                logger.info("exist: " + prefix.toString());
            }
        }
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
