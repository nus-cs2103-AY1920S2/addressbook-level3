package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Location;
import seedu.address.model.event.tutorial.TutorialName;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.model.reminder.Description;
import seedu.address.model.student.Email;
import seedu.address.model.student.MatricNumber;
import seedu.address.model.student.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_STUDENT_ATTENDANCE_INDEX = "Index is not a non-zero unsigned integer,"
            + " or \"all\".";
    public static final String MESSAGE_INVALID_DATE_TIME = "Format of date and time is not supported.";
    public static final String MESSAGE_INVALID_DATE = "Format of date is not supported.";
    public static final String MESSAGE_INVALID_TIME = "Format of time should be HH:MM";
    public static final String MESSAGE_INVALID_DAY = "Day should be an integer from 1 (Monday) to 7 (Sunday).";
    public static final String MESSAGE_INVALID_FILEPATH = "Filepath specified is invalid.";
    public static final String MESSAGE_INVALID_FILEPATH_EXTENSION = "Filepath specified does not end in .csv";

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
     * Parses {@code studentIndex} into an {@code Index} and returns it. If {@code studentIndex} is "all",
     * a 0 index will be returned. Otherwise, it will be a one-based index.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseAttendanceStudent(String studentIndex) throws ParseException {
        String trimmedIndex = studentIndex.trim();
        if (trimmedIndex.toLowerCase().equals("all")) {
            return Index.fromZeroBased(0);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_STUDENT_ATTENDANCE_INDEX);
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
     * Parses a {@code String matricNumber} into a {@code MatricNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matricNumber} is invalid.
     */
    public static MatricNumber parseMatricNumber(String matricNumber) throws ParseException {
        requireNonNull(matricNumber);
        String trimmedMatricNumber = matricNumber.trim();
        if (!MatricNumber.isValidMatricNumber(trimmedMatricNumber)) {
            throw new ParseException(MatricNumber.MESSAGE_CONSTRAINTS);
        }
        return new MatricNumber(trimmedMatricNumber);
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
     * Parses {@code <String> tags} into a {@code Set<Tag>}.
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
     * Parses a {@code String location} into an {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parsePlace(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}.
     * The format of the LocalDateTime will be in the format yyyy-MM-dd HH:mm.
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedDateTime;
        try {
            formattedDateTime = LocalDateTime.parse(trimmedDateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        }
        return formattedDateTime;
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * The format of the LocalDate will be in the format yyyy-MM-dd.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDate;
        try {
            formattedDate = LocalDate.parse(trimmedDate, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        return formattedDate;
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * The format of the LocalTime will be in the format HH:mm.
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime formattedTime;
        try {
            formattedTime = LocalTime.parse(trimmedTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_TIME);
        }
        return formattedTime;
    }

    /**
     * Parses a {@code String day} into a {@code DayOfWeek}.
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static DayOfWeek parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();

        DayOfWeek parsedDay;
        try {
            parsedDay = DayOfWeek.of(Integer.parseInt(trimmedDay));
            return parsedDay;
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Parses a {@code String week} into an {@code int} which only accepts values which can be converted to an integer
     * and is between 3 and 13 inclusive.
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static int parseTutorialWeek(String week) throws ParseException {
        requireNonNull(week);
        String trimmedWeek = week.trim();

        int parsedWeek;
        try {
            parsedWeek = Integer.parseInt(trimmedWeek);
            if (parsedWeek < 3 || parsedWeek > 13) {
                throw new ParseException(Messages.MESSAGE_INVALID_WEEK);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_WEEK);
        }

        return parsedWeek;
    }

    /**
     * Parses a {@code String tutorialName} into a {@code TutorialName}.
     * @throws ParseException if the given {@code tutorialName} is invalid.
     */
    public static TutorialName parseTutorialName(String tutorialName) throws ParseException {
        requireNonNull(tutorialName);
        String trimmedTutorialName = tutorialName.trim();

        if (!TutorialName.isValidTutorialName(trimmedTutorialName)) {
            throw new ParseException(TutorialName.MESSAGE_CONSTRAINTS);
        }

        return new TutorialName(trimmedTutorialName);
    }

    /**
     * Parses a {@code String csvFilePath} into a {@code Path} object, ensuring that it points to a csv file.
     * @throws ParseException if the given {@code csvFilePath} is invalid.
     */
    public static Path parseCsvFilePath(String csvFilePath) throws ParseException {
        requireNonNull(csvFilePath);
        String trimmedCsvFilePath = csvFilePath.trim();

        if (!FileUtil.isValidPath(trimmedCsvFilePath) || trimmedCsvFilePath.length() < 5) {
            throw new ParseException(MESSAGE_INVALID_FILEPATH);
        }

        if (!trimmedCsvFilePath
            .substring(trimmedCsvFilePath.length() - 4)
            .equals(".csv")) {
            throw new ParseException(MESSAGE_INVALID_FILEPATH_EXTENSION);
        }

        return Paths.get(csvFilePath);
    }

    /**
     * Parses a {@code String modCode} into a {@code ModCode}.
     *
     * @throws ParseException if the given {@code modCode} is invalid.
     */
    public static ModCode parseModCode(String modCode) throws ParseException {
        requireNonNull(modCode);
        String trimmedModCode = modCode.trim().toUpperCase();

        if (!ModCode.isValidModCode(trimmedModCode)) {
            throw new ParseException(ModCode.MESSAGE_CONSTRAINTS);
        }

        return new ModCode(trimmedModCode);
    }

    /**
     * Parses a {@code String modLink} into a {@code ModLink}.
     *
     * @throws ParseException if the given {@code modLink} is invalid.
     */
    public static ModLink parseModLink(String modLink) throws ParseException {
        requireNonNull(modLink);
        String trimmedModLink = modLink.trim();

        if (!ModLink.isValidModLink(trimmedModLink)) {
            throw new ParseException(ModLink.MESSAGE_CONSTRAINTS);
        }

        return new ModLink(trimmedModLink);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
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
        return new Description(description);
    }

    /**
     * Parses a {@code String snoozeDuration} into a {@code long} which only accepts values which can be
     * converted to a long and is positive.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static long parseSnoozeDuration(String snoozeDuration) throws ParseException {
        requireNonNull(snoozeDuration);
        String trimmedSnoozeDuration = snoozeDuration.trim();
        long duration;
        try {
            duration = Long.parseLong(trimmedSnoozeDuration);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_REMINDER_INVALID_SNOOZE_DURATION);
        }
        if (duration <= 0) {
            throw new ParseException(Messages.MESSAGE_REMINDER_INVALID_SNOOZE_DURATION);
        }
        return duration;
    }
}
