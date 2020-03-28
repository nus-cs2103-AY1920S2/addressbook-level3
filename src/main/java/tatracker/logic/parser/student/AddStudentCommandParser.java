package tatracker.logic.parser.student;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.PREFIX_EMAIL;
import static tatracker.logic.parser.Prefixes.PREFIX_GROUP;
import static tatracker.logic.parser.Prefixes.PREFIX_MATRIC;
import static tatracker.logic.parser.Prefixes.PREFIX_MODULE;
import static tatracker.logic.parser.Prefixes.PREFIX_NAME;
import static tatracker.logic.parser.Prefixes.PREFIX_PHONE;
import static tatracker.logic.parser.Prefixes.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
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
                ArgumentTokenizer.tokenize(args, PREFIX_MATRIC, PREFIX_MODULE, PREFIX_GROUP,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRIC, PREFIX_MODULE, PREFIX_GROUP, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }

        // ==== Identity fields ====

        Matric matric = ParserUtil.parseMatric(argMultimap.getValue(PREFIX_MATRIC).get());

        Module module = new Module(argMultimap.getValue(PREFIX_MODULE).get());
        Group group = new Group(argMultimap.getValue(PREFIX_GROUP).get());

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        // ==== Optional fields ====

        Phone phone = new Phone();
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        Email email = new Email();
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // ==== Build Student  ====

        Student student = new Student(matric, name, phone, email, tagList);

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
