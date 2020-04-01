package tatracker.logic.parser.student;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;

import java.util.Set;
import java.util.stream.Stream;

import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class AddStudentCommandParser implements Parser<AddStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddStudentCommand
     * and returns an AddStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MATRIC, MODULE, GROUP,
                        NAME, PHONE, EMAIL, RATING, TAG);

        if (!arePrefixesPresent(argMultimap, MATRIC, MODULE, GROUP, NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddStudentCommand.DETAILS.getUsage()));
        }

        // ==== Identity fields ====

        Matric matric = ParserUtil.parseMatric(argMultimap.getValue(MATRIC).get());

        String module = argMultimap.getValue(MODULE).get().toUpperCase();
        String group = argMultimap.getValue(GROUP).get().toUpperCase();

        Name name = ParserUtil.parseName(argMultimap.getValue(NAME).get());

        // ==== Optional fields ====

        Phone phone = new Phone();
        if (argMultimap.getValue(PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PHONE).get());
        }

        Email email = new Email();
        if (argMultimap.getValue(EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(EMAIL).get());
        }

        Rating rating = new Rating();
        if (argMultimap.getValue(RATING).isPresent()) {
            rating = ParserUtil.parseRating(argMultimap.getValue(RATING).get());
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(TAG));

        // ==== Build Student  ====

        Student student = new Student(matric, name, phone, email, rating, tagList);

        return new AddStudentCommand(student, group, module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
