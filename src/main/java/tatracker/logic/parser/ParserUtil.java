package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import tatracker.commons.core.index.Index;
import tatracker.commons.util.DateTimeUtil;
import tatracker.commons.util.StringUtil;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.logic.commands.sort.SortType;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.TaTracker;
import tatracker.model.group.GroupType;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_UNSIGNED_INT = "Number is not an unsigned integer.";

    /**
     * Parses a {@code String integer} into an integer primitive.
     * This is different from the standard Java version as it does not
     * allow any signed values (i.e. the following values cannot be parsed: +5, -2).
     */
    public static int parseUnsignedInteger(String integer) throws ParseException {
        requireNonNull(integer);
        String trimmedInteger = integer.trim();

        if (!StringUtil.isUnsignedInteger(trimmedInteger)) {
            throw new ParseException(MESSAGE_INVALID_UNSIGNED_INT);
        }

        return Integer.parseUnsignedInt(trimmedInteger);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS);
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
        String trimmedName = name.trim().toLowerCase();
        StringBuilder nameBuilder = new StringBuilder();
        String[] nameParts = trimmedName.split(" ");

        for (int i = 0; i < nameParts.length; ++i) {
            if (nameParts[i].isBlank()) {
                continue;
            }
            nameBuilder.append(Character.toUpperCase(nameParts[i].charAt(0)));
            nameBuilder.append(nameParts[i].substring(1) + " ");
        }

        trimmedName = nameBuilder.toString().trim();

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
     * Parses a {@code String matric} into an {@code Matric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matric} is invalid.
     */
    public static Matric parseMatric(String matric) throws ParseException {
        requireNonNull(matric);
        String trimmedMatric = matric.trim();
        if (!Matric.isValidMatric(trimmedMatric)) {
            throw new ParseException(Matric.MESSAGE_CONSTRAINTS);
        }
        return new Matric(trimmedMatric);
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
     * Parses a {@code String date} into a {@code LocalDate}
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();

        if (!DateTimeUtil.isDate(trimmedDate)) {
            throw new ParseException(DateTimeUtil.CONSTRAINTS_DATE);
        }
        return LocalDate.parse(trimmedDate);
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        if (!DateTimeUtil.isTime(trimmedTime)) {
            throw new ParseException(DateTimeUtil.CONSTRAINTS_TIME);
        }
        return LocalTime.parse(trimmedTime);
    }

    /**
     * Parses a {@code String sessionType} into a {@code SessionType}
     */
    public static SessionType parseSessionType(String sessionType) throws ParseException {
        requireNonNull(sessionType);
        String trimmedType = sessionType.trim();

        switch (trimmedType) {
        case "tutorial":
            return SessionType.TUTORIAL;
        case "lab":
            return SessionType.LAB;
        case "consultation":
            return SessionType.CONSULTATION;
        case "grading":
            return SessionType.GRADING;
        case "preparation":
            return SessionType.PREPARATION;
        case "other":
            return SessionType.OTHER;
        default:
            throw new ParseException(SessionType.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses and returns Group Type of group.
     */
    public static GroupType parseGroupType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();

        switch (trimmedType) {
        case "tutorial":
            return GroupType.TUTORIAL;
        case "lab":
            return GroupType.LAB;
        case "other":
            return GroupType.OTHER;
        default:
            throw new ParseException(GroupType.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses and returns Group Type of group.
     */
    public static SortType parseSortType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();

        if (!SortType.isValidSortType(trimmedType)) {
            throw new ParseException(SortType.MESSAGE_CONSTRAINTS);
        }
        return SortType.getSortType(trimmedType);
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();

        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }

        int parsedRating = Integer.parseUnsignedInt(trimmedRating);
        return new Rating(parsedRating);
    }

    /**
     * Parses a {@code String numWeeks} into a number of weeks.
     */
    public static int parseNumWeeks(String numWeeks) throws ParseException {
        try {
            return parseUnsignedInteger(numWeeks);
        } catch (ParseException pe) {
            throw new ParseException(Session.CONSTRAINTS_RECURRING_WEEKS);
        }
    }

    /**
     * Parses and returns the tab name specified by the user in the goto command
     *
     * @param tabName user input
     * @return the tab specified by the user
     * @throws ParseException invalid tab name
     */
    public static Tab parseTabName(String tabName) throws ParseException {
        requireNonNull(tabName);
        String trimmedType = tabName.trim();

        if (!Tab.isValidTab(trimmedType)) {
            throw new ParseException(Tab.MESSAGE_CONSTRAINTS);
        }
        return Tab.getTab(trimmedType);
    }

    /**
     * Parses and returns the pay rate specified by the user in the setrate command
     *
     * @param rate user input
     * @return the pay rate specified by the user
     * @throws ParseException invalid pay rate
     */
    public static int parseRate(String rate) throws ParseException {
        try {
            int parsedRate = parseUnsignedInteger(rate);
            if (parsedRate <= 0) {
                throw new NumberFormatException();
            }
            return parsedRate;
        } catch (NumberFormatException | ParseException e) {
            throw new ParseException(TaTracker.CONSTRAINTS_RATE);
        }
    }
}
