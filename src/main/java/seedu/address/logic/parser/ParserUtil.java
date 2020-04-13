package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX =
            "Invalid index: The index provided is not a non-zero unsigned integer!";
    public static final String MESSAGE_INVALID_REDIT_ITEM_INDEX = "Invalid index: The index provided for one of the"
            + "items is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String level} into a {@code Level}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static Level parseLevel(String level) throws ParseException {
        requireNonNull(level);
        String trimmedLevel = level.trim();
        for (Level value : Level.values()) {
            if (value.toString().equalsIgnoreCase(trimmedLevel)) {
                return value;
            }
        }
        throw new ParseException(Level.MESSAGE_CONSTRAINTS);
    }

    /**
     *
     * Parses a {@code String website} into a {@code Website}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static Website parseWebsite(String website) throws ParseException {
        requireNonNull(website);
        String trimmedWebsite = website.trim();
        if (!Website.isValidWebsite(trimmedWebsite)) {
            throw new ParseException(Website.MESSAGE_CONSTRAINTS);
        }
        return new Website(trimmedWebsite);
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
     * Parses a {@code String description} into an {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
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
        if (!Tag.isValidTagName(trimmedTag) || tag.length() == 0) {
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
     * Parses an {@code String itemType} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static String parseItemType(String itemType) throws ParseException {
        requireNonNull(itemType);
        String trimmedItemType = itemType.trim();
        if (!Item.isValidItemType(trimmedItemType)) {
            throw new ParseException(Item.MESSAGE_INVALID_ITEM_TYPE);
        }
        return trimmedItemType;
    }

    /**
     * Parses a {@code String time} in MM-YYYY format into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses the Item Indices to give the required optional.
     *
     * @throws ParseException if the given {@code indices} is invalid.
     */
    public static Optional<List<Integer>> parseReditItemIndices(String indices) throws ParseException {
        if (indices == null) {
            return Optional.empty();
        } else if (indices.equals("")) {
            // Empty string will return an InvocationTargetException in the streams
            return Optional.of(new ArrayList<>());
        } else {
            boolean isValidIndices = Arrays
                    .stream(indices.split("\\s+"))
                    .allMatch(StringUtil::isNonZeroUnsignedInteger);

            if (!isValidIndices) {
                throw new ParseException(MESSAGE_INVALID_REDIT_ITEM_INDEX);
            }

            List<Integer> mappedIndices = Arrays.stream(indices.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            return Optional.of(mappedIndices);
        }
    }

    /**
     * Parses a {@code String displayFilePath} into a {@code DisplayPicture}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static DisplayPicture parseDisplayPicture(String displayFilePath) throws ParseException {
        requireNonNull(displayFilePath);
        String trimmedDisplayFilePath = displayFilePath.trim();
        if (!DisplayPicture.isValidDisplayPicture(trimmedDisplayFilePath)) {
            throw new ParseException(DisplayPicture.MESSAGE_CONSTRAINTS_FILE_TYPE);
        }
        return new DisplayPicture(trimmedDisplayFilePath);
    }

    /**
     * Parses a {@code String github} into a {@code Github}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code github} is invalid.
     */
    public static Github parseGithub(String github) throws ParseException {
        requireNonNull(github);
        String trimmedGithub = github.trim();
        if (!Github.isValidGithub(trimmedGithub)) {
            throw new ParseException(Github.MESSAGE_CONSTRAINTS);
        }
        return new Github(trimmedGithub);
    }

    /**
     * Parses a {@code String university} into a {@code University university}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code github} is invalid.
     */
    public static University parseUniversity(String university) throws ParseException {
        requireNonNull(university);
        String trimmedUniversity = university.trim();
        if (!University.isValidUniversity(trimmedUniversity)) {
            throw new ParseException(University.MESSAGE_CONSTRAINTS);
        }
        return new University(trimmedUniversity);
    }

    /**
     * Parses a {@code String major} into a {@code Major major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code major} is invalid.
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
     * Parses a {@code String cap} into a {@code Double cap}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cap} is invalid.
     */
    public static Cap parseCap(String cap) throws ParseException {
        requireNonNull(cap);
        String[] trimmedValues = cap.trim().split(" ");
        if (trimmedValues.length == 2 && Verifier.isValidCap(trimmedValues[0], trimmedValues[1])) {
            double current = Double.valueOf(trimmedValues[0]);
            current = Math.round(current * 100.00) / 100.00;
            double max = Double.valueOf(trimmedValues[1]);
            max = Math.round(max * 100.00) / 100.00;
            return new Cap(current + " " + max);
        }
        throw new ParseException(Cap.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses a {@code String role} into a {@code Role role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses a {@code String reverse} choice into a boolean option.
     * A default value of false is returned if null is provided.
     *
     * @throws ParseException if the given {@code reverse} is invalid.
     */
    public static boolean parseReverse(String reverse) throws ParseException {
        // trimming is not done before null check to avoid NullPointerException
        if (reverse == null || reverse.trim().equalsIgnoreCase("false")) {
            return false;
        } else if (reverse.trim().equalsIgnoreCase("true")) {
            return true;
        } else {
            throw new ParseException("Reverse choice can only be true of false.");
        }

    }
}
