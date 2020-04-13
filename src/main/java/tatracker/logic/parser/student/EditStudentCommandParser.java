//@@author potatocombat

package tatracker.logic.parser.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.student.Matric;
import tatracker.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditStudentCommand object
 */
public class EditStudentCommandParser implements Parser<EditStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStudentCommand
     * and returns an EditStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, MATRIC, MODULE, GROUP, NAME, PHONE, EMAIL, RATING, TAG);

        if (!argMultimap.arePrefixesPresent(MATRIC, MODULE, GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(EditStudentCommand.DETAILS.getUsage()));
        }

        // ==== Identity fields ====

        Matric matric = ParserUtil.parseMatric(argMultimap.getValue(MATRIC).get());

        String moduleCode = argMultimap.getValue(MODULE).map(String::trim).get();
        String groupCode = argMultimap.getValue(GROUP).map(String::trim).get();

        // ==== Optional fields ====
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();

        if (argMultimap.getValue(NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(NAME).get()));
        }
        if (argMultimap.getValue(PHONE).isPresent()) {
            editStudentDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PHONE).get()));
        }
        if (argMultimap.getValue(EMAIL).isPresent()) {
            editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(EMAIL).get()));
        }
        if (argMultimap.getValue(RATING).isPresent()) {
            editStudentDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(RATING).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(TAG)).ifPresent(editStudentDescriptor::setTags);

        // ==== Build Student  ====

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditStudentCommand(matric, moduleCode, groupCode, editStudentDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}

