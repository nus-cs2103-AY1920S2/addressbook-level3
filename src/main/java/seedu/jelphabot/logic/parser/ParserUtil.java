package seedu.jelphabot.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.commons.util.StringUtil;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;

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
     * Parses a {@code String name} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
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
     * Parses a {@code String dateTime} into an {@code dateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDatetime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDatetime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDatetime);
    }

    /**
     * Parses a {@code String module code} into an {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code module code} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return Priority.toPriority(trimmedPriority);
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
     * Parses a {@code String reminderDay} into a {@code ReminderDay}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code reminderDay} is invalid.
     */
    public static ReminderDay parseReminderDay(String reminderDay) throws ParseException {
        requireNonNull(reminderDay);
        String trimmedReminderDay = reminderDay.trim();
        //int convertedReminderDay = Integer.parseInt(trimmedReminderDay);
        if (!ReminderDay.isValidReminderDay(trimmedReminderDay)) {
            throw new ParseException(ReminderDay.MESSAGE_CONSTRAINTS);
        }
        //int convertedReminderDay = Integer.parseInt(trimmedReminderDay);
        return new ReminderDay(trimmedReminderDay);
    }

    /**
     * Parses a {@code String reminderHour} into a {@code ReminderHour}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code reminderHour} is invalid.
     */
    public static ReminderHour parseReminderHour(String reminderHour) throws ParseException {
        requireNonNull(reminderHour);
        String trimmedReminderHour = reminderHour.trim();
        //int convertedReminderHour = Integer.parseInt(trimmedReminderHour);
        if (!ReminderHour.isValidReminderHour(trimmedReminderHour)) {
            throw new ParseException(ReminderHour.MESSAGE_CONSTRAINTS);
        }
        return new ReminderHour(trimmedReminderHour);
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
}
