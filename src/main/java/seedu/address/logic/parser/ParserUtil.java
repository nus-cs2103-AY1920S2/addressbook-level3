package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.calender.Deadline;
import seedu.address.model.calender.Task;
import seedu.address.model.calender.ToDo;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.Priority;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }


    /**
     * Parses a {@code String path, @code String pathType} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     * @param path path and filename to be created
     * @param pathType path type, absolute or relative.
     * @return a new note object.
     * @throws ParseException when the given {@code notesListoperation} is invalid.
     */
    public static Notes parseNotesListOperation(String path, String pathType) throws ParseException {
        requireNonNull(path, pathType);
        String trimmedPath = path.trim();
        String trimmedPathType = pathType.trim();
        if (!Notes.isValidPathType(trimmedPathType)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS_PATH_TYPE);
        }
        return new Notes(trimmedPath, trimmedPathType);
    }

    /**
     * Parses a {@code String path, @code String pathType} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     * @param path path and filename to be created
     * @param pathType path type, absolute or relative.
     * @return a new note object.
     * @throws ParseException when the given {@code notesListoperation} is invalid.
     */
    public static Notes parseNotesCreateOperation(String path, String type, String pathType) throws ParseException {
        requireNonNull(path, pathType);
        requireNonNull(type);
        String trimmedPath = path.trim();
        String trimmedPathType = pathType.trim();
        String trimmedType = type.trim();
        if (!Notes.isValidPathType(trimmedPathType)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS_PATH_TYPE);
        }
        if (!Notes.isValidType(trimmedType)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS_TYPE);
        }
        return new Notes(trimmedPath, trimmedType, trimmedPathType);
    }

    /**
     * Parses a {@code String path, @code String pathType} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     * @param path path and filename to be created
     * @param pathType path type, absolute or relative.
     * @return a new note object.
     * @throws ParseException when the given {@code notesOpenoperation} is invalid.
     */
    public static Notes parseNotesOpenOperation(String path, String pathType) throws ParseException {
        requireNonNull(path, pathType);
        String trimmedPath = path.trim();
        String trimmedPathType = pathType.trim();
        if (!Notes.isValidPathType(trimmedPathType)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS_PATH_TYPE);
        }

        return new Notes(trimmedPath, trimmedPathType);
    }

    /**
     * Parses a {@code String path, @code String pathType} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     * @param path path and filename to be created
     * @param pathType path type, absolute or relative.
     * @return a new note object.
     * @throws ParseException when the given {@code notesOpenoperation} is invalid.
     */
    public static Notes parseNotesDeleteOperation(String path, String pathType) throws ParseException {
        requireNonNull(path, pathType);
        String trimmedPath = path.trim();
        String trimmedPathType = pathType.trim();
        if (!Notes.isValidPathType(trimmedPathType)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS_PATH_TYPE);
        }

        return new Notes(trimmedPath, trimmedPathType);
    }

    /**
     * Parses a {@code String moduleCode} into an {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim().toUpperCase();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim().toUpperCase();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return Grade.getGrade(trimmedGrade);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim().toUpperCase();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return Priority.getPriority(trimmedPriority);
    }

    /**
     * Parses a {@code String major} into an {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code String description, @code String date} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Task parseDeadline(String description, String date, String category) throws ParseException {
        requireNonNull(description, date);
        String trimmedDescription = description.trim();
        String trimmedDate = date.trim();
        String trimmedCategory = category.trim();
        if (!Deadline.isValidDate(trimmedDate)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDescription, trimmedDate, trimmedCategory, "add");
    }

    /**
     * Parses a {@code String description, @code String date} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Task parseRemoveDeadline(String index) throws ParseException {
        requireNonNull(index);
        int indexInt = Integer.parseInt(index.trim());
        return new Deadline(indexInt, "delete");
    }

    /**
     * Parses a {@code String Description} into an {@code Todo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static Task parseTodo(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new ToDo(trimmedDescription);
    }




}
