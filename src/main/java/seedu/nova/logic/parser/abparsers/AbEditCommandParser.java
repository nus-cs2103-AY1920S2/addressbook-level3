package seedu.nova.logic.parser.abparsers;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Set;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbEditCommand;
import seedu.nova.logic.commands.abcommands.AbEditCommand.EditPersonDescriptor;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.category.Category;

/**
 * Parses input arguments and creates a new AbEditCommand object
 */
public class AbEditCommandParser implements Parser<AbEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AbEditCommand
     * and returns an AbEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_CATEGORY);

        Index index;

        if (argMultimap.getValue(CliSyntax.PREFIX_INDEX).isPresent()) {
            index = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_INDEX).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbEditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get()));
        }

        if (argMultimap.getAllValues(PREFIX_CATEGORY).size() == 1) {
            editPersonDescriptor.setCategories(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CATEGORY)));
        } else if (argMultimap.getAllValues(PREFIX_CATEGORY).size() > 1) {
            throw new ParseException("Please only provide 1 category");
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AbEditCommand.MESSAGE_NOT_EDITED);
        }

        return new AbEditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Category>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero tags.
     */
    /*private Optional<Set<Category>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    } */

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Category>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero tags.
     */
    private Set<Category> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        //Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        Collection<String> tagSet = tags;
        return ParserUtil.parseTags(tagSet);
    }

}
