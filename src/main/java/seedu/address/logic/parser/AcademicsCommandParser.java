package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOMEWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMIT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.academics.AcademicsAddCommand;
import seedu.address.logic.commands.academics.AcademicsCommand;
import seedu.address.logic.commands.academics.AcademicsDeleteCommand;
import seedu.address.logic.commands.academics.AcademicsDisplayCommand;
import seedu.address.logic.commands.academics.AcademicsEditCommand;
import seedu.address.logic.commands.academics.AcademicsExportCommand;
import seedu.address.logic.commands.academics.AcademicsMarkCommand;
import seedu.address.logic.commands.academics.AcademicsSubmitCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.academics.Assessment;

/**
 * Parses the given {@code String} of arguments in the context of the AcademicsCommand
 * and returns an AcademicsCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class AcademicsCommandParser implements Parser<AcademicsCommand> {

    public static final String HELP_MESSAGE = "Academics command has to include an action.\n"
            + AcademicsCommand.MESSAGE_USAGE;

    @Override
    public AcademicsCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD, PREFIX_DELETE, PREFIX_EDIT, PREFIX_HOMEWORK, PREFIX_EXAM,
                        PREFIX_REPORT, PREFIX_SUBMIT, PREFIX_MARK, PREFIX_STUDENT, PREFIX_ASSESSMENT_DESCRIPTION,
                        PREFIX_ASSESSMENT_TYPE, PREFIX_ASSESSMENT_DATE, PREFIX_EXPORT);

        if (args.equals("")) {
            return academicsDisplayCommand(argMultimap, "");
        }

        if (argMultimap.getValue(PREFIX_ADD).isPresent()) {
            return addCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) {
            return deleteCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_EDIT).isPresent()) {
            return editCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_SUBMIT).isPresent()) {
            return submitCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_MARK).isPresent()) {
            return markCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_HOMEWORK).isPresent()) {
            return academicsDisplayCommand(argMultimap, "homework");
        } else if (argMultimap.getValue(PREFIX_EXAM).isPresent()) {
            return academicsDisplayCommand(argMultimap, "exam");
        } else if (argMultimap.getValue(PREFIX_REPORT).isPresent()) {
            return academicsDisplayCommand(argMultimap, "report");
        } else if (argMultimap.getValue(PREFIX_EXPORT).isPresent()) {
            return academicsExportCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
        }
    }

    /**
     * Returns a AcademicsAddCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException, CommandException {
        if (!arePrefixesPresent(argMultimap, PREFIX_ASSESSMENT_DESCRIPTION, PREFIX_ASSESSMENT_TYPE,
                PREFIX_ASSESSMENT_DATE) || !argMultimap.getPreamble(PREFIX_ADD.getPrefix()).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AcademicsAddCommand.MESSAGE_USAGE));
        }

        String description = argMultimap.getValue(PREFIX_ASSESSMENT_DESCRIPTION).get().trim();
        String type = argMultimap.getValue(PREFIX_ASSESSMENT_TYPE).get().trim();
        String date = argMultimap.getValue(PREFIX_ASSESSMENT_DATE).get().trim();

        if (description.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AcademicsAddCommand.MESSAGE_USAGE));
        }
        if (type.isEmpty()) {
            if (argMultimap.getValue(PREFIX_HOMEWORK).isPresent()) {
                type = "homework";
            } else if (argMultimap.getValue(PREFIX_EXAM).isPresent()) {
                type = "exam";
            } else {
                throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_TYPE);
            }
        }
        if (!Assessment.checkValidDate(date)) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, HELP_MESSAGE));
        }

        return new AcademicsAddCommand(description, type, date);
    }

    /**
     * Returns a AcademicsDeleteCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsDeleteCommand deleteCommand(ArgumentMultimap argMultimap) throws ParseException, CommandException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble(PREFIX_DELETE.getPrefix()));
            return new AcademicsDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX, pe);
        }
    }

    /**
     * Returns a AcademicsEditCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsEditCommand editCommand(ArgumentMultimap argMultimap) throws ParseException, CommandException {
        requireNonNull(argMultimap);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble("edit"));
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX, pe);
        }

        AcademicsEditCommand.EditAssessmentDescriptor editAssessmentDescriptor =
                new AcademicsEditCommand.EditAssessmentDescriptor();
        if (argMultimap.getValueOptional(PREFIX_ASSESSMENT_DESCRIPTION).isPresent()) {
            if (argMultimap.getValueOptional(PREFIX_ASSESSMENT_DESCRIPTION).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AcademicsEditCommand.MESSAGE_USAGE));
            }
            editAssessmentDescriptor.setDescription(argMultimap.getValue(PREFIX_ASSESSMENT_DESCRIPTION).get());
        }
        if (argMultimap.getValueOptional(PREFIX_ASSESSMENT_TYPE).isPresent()) {
            String type = argMultimap.getValue(PREFIX_ASSESSMENT_TYPE).get();
            if (type.isEmpty()) {
                if (argMultimap.getValue(PREFIX_HOMEWORK).isPresent()) {
                    type = "homework";
                } else if (argMultimap.getValue(PREFIX_EXAM).isPresent()) {
                    type = "exam";
                } else {
                    throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_TYPE);
                }
            } else {
                if (!type.equals("homework") && !type.equals("exam")) {
                    throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_TYPE);
                }
            }
            editAssessmentDescriptor.setType(type);
        }
        if (argMultimap.getValueOptional(PREFIX_ASSESSMENT_DATE).isPresent()) {
            if (!Assessment.checkValidDate(argMultimap.getValue(PREFIX_ASSESSMENT_DATE).get())) {
                throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, HELP_MESSAGE));
            }
            editAssessmentDescriptor.setDate(argMultimap.getValue(PREFIX_ASSESSMENT_DATE).get());
        }

        if (!editAssessmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AcademicsEditCommand.MESSAGE_NOT_EDITED);
        }

        return new AcademicsEditCommand(index, editAssessmentDescriptor);
    }

    /**
     * Returns a AcademicsSubmitCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsSubmitCommand submitCommand(ArgumentMultimap argMultimap) throws ParseException, CommandException {
        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT)
                || argMultimap.getPreamble(PREFIX_SUBMIT.getPrefix()).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AcademicsSubmitCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble(PREFIX_SUBMIT.getPrefix()));
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX, pe);
        }
        List<String> students = argMultimap.getAllValues(PREFIX_STUDENT);

        return new AcademicsSubmitCommand(index, students);
    }

    /**
     * Returns a AcademicsMarkCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsMarkCommand markCommand(ArgumentMultimap argMultimap) throws ParseException, CommandException {
        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT)
                || argMultimap.getPreamble(PREFIX_MARK.getPrefix()).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AcademicsMarkCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble(PREFIX_MARK.getPrefix()));
        } catch (ParseException pe) {
            throw new ParseException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX, pe);
        }
        List<String> students = argMultimap.getAllValues(PREFIX_STUDENT);

        return new AcademicsMarkCommand(index, students);
    }

    /**
     * Returns a AcademicsDisplayCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsDisplayCommand academicsDisplayCommand(ArgumentMultimap argMultimap, String type)
            throws ParseException, CommandException {
        if (!argMultimap.getPreamble(type).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AcademicsMarkCommand.MESSAGE_USAGE));
        }
        return new AcademicsDisplayCommand(type);
    }

    /**
     * Returns a AcademicsExportCommand object for execution.
     * {@code ArgumentMultimap}.
     */
    private AcademicsExportCommand academicsExportCommand() throws ParseException, CommandException {
        return new AcademicsExportCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
