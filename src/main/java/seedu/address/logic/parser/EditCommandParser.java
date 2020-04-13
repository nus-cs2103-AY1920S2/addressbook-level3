package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.UNREACHABLE_STATEMENT_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.edit.EditInternshipCommand;
import seedu.address.logic.commands.edit.EditInternshipDescriptor;
import seedu.address.logic.commands.edit.EditNoteCommand;
import seedu.address.logic.commands.edit.EditNoteDescriptor;
import seedu.address.logic.commands.edit.EditProjectCommand;
import seedu.address.logic.commands.edit.EditProjectDescriptor;
import seedu.address.logic.commands.edit.EditResumeCommand;
import seedu.address.logic.commands.edit.EditResumeDescriptor;
import seedu.address.logic.commands.edit.EditSkillCommand;
import seedu.address.logic.commands.edit.EditSkillDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ItemUtil;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_ITEM, PREFIX_FROM, PREFIX_TO,
                        PREFIX_ROLE, PREFIX_DESCRIPTION, PREFIX_WEBSITE, PREFIX_LEVEL);

        if (argMultimap.getPreamble().isEmpty() && !argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        if (!argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(Item.MESSAGE_CONSTRAINTS);
        }

        String itemType = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_ITEM).get());

        // Checks for presence of values are done inside the parseXXX methods instead of ItemDescriptors to prevent
        // dependency of ItemDescriptor to argMultimap
        switch (itemType) {
        case ItemUtil.RESUME_ALIAS:
            return parseResume(index, argMultimap);
        case ItemUtil.INTERNSHIP_ALIAS:
            return parseInternship(index, argMultimap);
        case ItemUtil.PROJECT_ALIAS:
            return parseProject(index, argMultimap);
        case ItemUtil.SKILL_ALIAS:
            return parseSkill(index, argMultimap);
        case ItemUtil.NOTE_ALIAS:
            return parseNote(index, argMultimap);
        default:
            throw new AssertionError(UNREACHABLE_STATEMENT_REACHED);
        }

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

    /**
     * Parses the arguments in the context of editing a Resume item.
     */
    private EditResumeCommand parseResume(Index index, ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        // ===== Start of updating item descriptor =====
        EditResumeDescriptor editResumeDescriptor = new EditResumeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editResumeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editResumeDescriptor::setTags);
        // ===== End of updating item descriptor =====

        if (!editResumeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditResumeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditResumeCommand(index, editResumeDescriptor);
    }

    /**
     * Parses the arguments in the context of editing a Internship item.
     */
    private EditInternshipCommand parseInternship(Index index, ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        // ===== Start of updating item descriptor =====
        EditInternshipDescriptor editInternshipDescriptor = new EditInternshipDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editInternshipDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editInternshipDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            editInternshipDescriptor.setFrom(ParserUtil.parseTime(argMultimap.getValue(PREFIX_FROM).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            editInternshipDescriptor.setTo(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TO).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editInternshipDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get().trim()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editInternshipDescriptor::setTags);
        // ===== End of updating item descriptor =====

        if (!editInternshipDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditInternshipCommand.MESSAGE_NOT_EDITED);
        }

        return new EditInternshipCommand(index, editInternshipDescriptor);
    }

    /**
     * Parses the arguments in the context of editing a Project item.
     */
    private EditProjectCommand parseProject(Index index, ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        // ===== Start of updating item descriptor =====
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editProjectDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editProjectDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_WEBSITE).isPresent()) {
            editProjectDescriptor.setWebsite(ParserUtil
                    .parseWebsite(argMultimap.getValue(PREFIX_WEBSITE).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editProjectDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get().trim()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editProjectDescriptor::setTags);
        // ===== End of updating item descriptor =====

        if (!editProjectDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProjectCommand(index, editProjectDescriptor);
    }

    /**
     * Parses the arguments in the context of editing a Skill item.
     */
    private EditSkillCommand parseSkill(Index index, ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        // ===== Start of updating item descriptor =====
        EditSkillDescriptor editSkillDescriptor = new EditSkillDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editSkillDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            editSkillDescriptor.setLevel(ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editSkillDescriptor::setTags);
        // ===== End of updating item descriptor =====

        if (!editSkillDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSkillCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSkillCommand(index, editSkillDescriptor);
    }

    /**
     * Parses the arguments in the context of editing a Note item.
     */
    private EditNoteCommand parseNote(Index index, ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        // ===== Start of updating item descriptor =====
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editNoteDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editNoteDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editNoteDescriptor::setTags);
        // ===== End of updating item descriptor =====

        if (!editNoteDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditNoteCommand.MESSAGE_NOT_EDITED);
        }

        return new EditNoteCommand(index, editNoteDescriptor);
    }
}
