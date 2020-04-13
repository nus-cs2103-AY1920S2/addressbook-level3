package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PATH = "Path provided must exist.";
    public static final String MESSAGE_FILE_ALREADY_EXIST = "File already exist. Please specify another name.";
    public static final String MESSAGE_INVALID_PARAMETER = "Invalid suggestion parameter entered. "
            + "Please input person/place/activity.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String address} into an {@code Address}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses {@code String path} into a trimmed path if file exists. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @throws ParseException if {@code file} does not exist.
     */
    public static String parsePath(String path) throws ParseException {
        requireNonNull(path);
        String trimmedPath = path.strip();
        File file = new File(trimmedPath);
        if (!file.exists()) {
            throw new ParseException(MESSAGE_INVALID_PATH);
        }
        return trimmedPath;
    }

    /**
     * Parses {@code String path} into a trimmed path if file does not exist.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if {@code file} exist.
     */
    public static String parseExportPath(String path) throws ParseException, IOException {
        requireNonNull(path);
        String trimmedPath = path.strip();
        File file = new File(trimmedPath);
        if (trimmedPath.isEmpty()) {
            throw new ParseException(String.format("Empty file name to export provided."));
        }
        if (file.exists()) {
            throw new ParseException(String.format(MESSAGE_FILE_ALREADY_EXIST + trimmedPath));
        }
        return trimmedPath;
    }

    /**
     * Parses {@code String time} into a {@code Time}
     */
    public static Time parseTime(String time) {
        requireNonNull(time);
        String[] arr = time.split(" ");
        // get hours
        String hours = arr[0].substring(0, arr[0].length() - 1);
        String minutes = arr[1].substring(0, arr[1].length() - 1);
        return new Time(Integer.valueOf(minutes), Integer.valueOf(hours));
    }

    /**
     * Parses {@code String suggest} into a trimmed parameter. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if {@code suggest} parameter not equal to person.
     */
    public static String parseSuggest(String suggest) throws ParseException {
        requireNonNull(suggest);
        String trimmedSuggestParameter = suggest.trim();
        if (trimmedSuggestParameter.equalsIgnoreCase("person")) {
            return trimmedSuggestParameter;
        } else if (trimmedSuggestParameter.equalsIgnoreCase("place")) {
            return trimmedSuggestParameter;
        } else if (trimmedSuggestParameter.equalsIgnoreCase("activity")) {
            return trimmedSuggestParameter;
        } else {
            throw new ParseException(MESSAGE_INVALID_PARAMETER);
        }
    }

    /**
     * Parses {@code String input} into {@code PlaceList}
     */
    public static PlaceList parsePlaces(String input) {
        List<String> toAdd = new ArrayList<>();
        String[] process = input.split(", ");
        for (String s : process) {
            toAdd.add(s);
        }
        return new PlaceList(toAdd);
    }

    /**
     * Parses {@code String input} into {@code ActivityList}
     */
    public static ActivityList parseActivities(String input) {
        List<String> toAdd = new ArrayList<>();
        String[] process = input.split(", ");
        for (String s : process) {
            toAdd.add(s);
        }
        return new ActivityList(toAdd);
    }

    /**
     * Parses {@code String input} into {@code TimeList}
     */
    public static TimeList parseTimes(String input) {
        List<String> toAdd = new ArrayList<>();
        String[] process = input.split(", ");
        for (String s : process) {
            toAdd.add(s);
        }
        return new TimeList(toAdd);
    }
}
