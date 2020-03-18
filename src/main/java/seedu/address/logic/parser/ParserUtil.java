package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.Date;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Note;
import seedu.address.model.activity.Priority;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
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
    public static seedu.address.model.person.Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!seedu.address.model.person.Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.person.Name(trimmedName);
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
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
     * Parses {@code String date} into a {@code Date}.
     * Checks if the string date is of valid form.
     * @param date of the user input
     * @return Date object created based on user input
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String dateTrimmed = date.trim();
        if (!Date.isValidDate(dateTrimmed)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(dateTrimmed);
    }

    /**
     * Parses {@code String name} into a {@code Name}.
     * Checks if the String name is not empty or does not only consists of whitespaces.
     * @param name of the activity
     * @return Name object of the activity
     * @throws ParseException
     */
    public static Name parseActivityName(String name) throws ParseException {
        requireNonNull(name);
        String nameTrimmed = name.trim();
        if (!Name.isValidName(nameTrimmed)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(nameTrimmed);
    }

    /**
     * Parses {@code String note} into a {@code Note}
     * Checks if the String note isn't empty or doesn't only contain whitespaces.
     * @param note of the activity
     * @return Note object of the activity
     * @throws ParseException
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String noteTrimmed = note.trim();
        if (!Note.isValidNote(noteTrimmed)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(noteTrimmed);
    }

    /**
     * Parses {@code String priority} into a {@code Priority}
     * Checks if the String priority is a correct integer.
     * @param priority of the activity
     * @return Priority object of the activity
     * @throws ParseException
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String priorityTrimmed = priority.trim();
        if (!Priority.isValidPriorityValue(priorityTrimmed)) {
            throw new ParseException(Priority.PRIORITY_RANGE_CONSTRAINTS);
        }
        return new Priority(priorityTrimmed);
    }

}
