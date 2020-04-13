package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.task.TaskMarkAsDoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.lesson.exceptions.InvalidDayAndTimeException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NON_INTEGER_INDEX = "Index is not an integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_NON_INTEGER_INDEX);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
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
     * Parses a {@code String description} into a {@code description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        String trimmedDescription;
        if (description == null) {
            trimmedDescription = null;
        } else {
            trimmedDescription = description.trim();
            if (!Description.isValidDescription(trimmedDescription)) {
                throw new ParseException(Description.MESSAGE_CONSTRAINTS);
            }
        }
        return new Description(trimmedDescription);
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
        String trimmedPhone;
        if (phone == null) {
            trimmedPhone = null;
        } else {
            trimmedPhone = phone.trim();
            if (!Phone.isValidPhone(trimmedPhone)) {
                throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
            }
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String office} into an {@code Office}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code office} is invalid.
     */
    public static Office parseOffice(String office) throws ParseException {
        String trimmedOffice;
        if (office == null) {
            trimmedOffice = null;
        } else {
            trimmedOffice = office.trim();
            if (!Office.isValidOffice(trimmedOffice)) {
                throw new ParseException(Office.MESSAGE_CONSTRAINTS);
            }
        }
        return new Office(trimmedOffice);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        String trimmedEmail;
        if (email == null) {
            trimmedEmail = null;
        } else {
            trimmedEmail = email.trim();
            if (!Email.isValidEmail(trimmedEmail)) {
                throw new ParseException(Email.MESSAGE_CONSTRAINTS);
            }
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code Set<ModuleCode>}.
     */
    public static Set<ModuleCode> parseModuleCodes(Collection<String> moduleCodes) throws ParseException {
        requireNonNull(moduleCodes);
        final Set<ModuleCode> moduleCodeSet = new HashSet<>();
        for (String moduleCode : moduleCodes) {
            if (moduleCode.length() > 10) {
                moduleCodeSet.addAll(parseModuleCodes(moduleCode.split("\\s+")));
            } else {
                moduleCodeSet.add(parseModuleCode(moduleCode));
            }
        }
        return moduleCodeSet;
    }

    /**
     * Parses {@code String... moduleCodes} into a {@code Set<ModuleCode>}.
     */
    public static Set<ModuleCode> parseModuleCodes(String... moduleCodes) throws ParseException {
        requireNonNull(moduleCodes);
        final Set<ModuleCode> moduleCodeSet = new HashSet<>();
        for (String moduleCode : moduleCodes) {
            moduleCodeSet.add(parseModuleCode(moduleCode));
        }
        return moduleCodeSet;
    }

    /**
     * Parses {@code String lessonType} into a {@code LessonType}.
     */
    public static LessonType parseLessonType(String lessonType) throws ParseException {
        requireNonNull(lessonType);
        String trimmedType = lessonType.trim().toUpperCase();
        if (!Lesson.isValidType(trimmedType)) {
            throw new ParseException(LessonType.MESSAGE_CONSTRAINTS);
        }
        return Lesson.convertStringToLessonType(trimmedType);
    }

    /**
     * Parses {@code String venue} into {@code String}.
     */
    public static String parseVenue(String venue) throws ParseException {
        if (venue.equals("")) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS_VENUE);
        } else {
            return venue.trim();
        }
    }

    /**
     * Parses {@code String week} into a {@code Calendar} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static Calendar parseWeek(String week) throws ParseException {
        requireNonNull(week);
        String trimmedWeek = week.trim();
        if (trimmedWeek.equals("this")) {
            return Calendar.getNowCalendar();
        } else if (trimmedWeek.equals("next")) {
            return Calendar.getNextWeekCalendar();
        } else {
            throw new ParseException(Calendar.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String taskDate} into a {@code TaskDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskDate} is invalid.
     */
    public static TaskDateTime parseDateForTask(String taskDate) throws ParseException {
        requireNonNull(taskDate);
        String trimmedDate = taskDate.trim();
        if (taskDate.length() != 10 || !TaskDateTime.isValidTaskTime(trimmedDate)) {
            throw new ParseException(TaskDateTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskDateTime(trimmedDate);
    }

    /**
     * Parses {@code String taskDate} and {@code String taskTime} into a {@code TaskDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskDate} or {@code taskTime} is invalid.
     */
    public static TaskDateTime parseDateTimeForTask(String taskDate, String taskTime) throws ParseException {
        requireAllNonNull(taskDate, taskTime);
        String trimmedDate = taskDate.trim();
        String trimmedTime = taskTime.trim();
        String toTest = trimmedDate + " " + trimmedTime;
        if (!TaskDateTime.isValidTaskTime(toTest)) {
            throw new ParseException(TaskDateTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskDateTime(trimmedDate, trimmedTime);
    }

    /**
     * Parses a {@code String taskTime} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskTime} is invalid.
     */
    public static int parseTaskId(String taskId) throws ParseException {
        String trimmedId;
        if (taskId == null) {
            trimmedId = null;
        } else {
            trimmedId = taskId.trim();
        }

        int value;
        try {
            value = Integer.parseInt(trimmedId);
        } catch (NumberFormatException error) {
            throw new ParseException(TaskMarkAsDoneCommand.MESSAGE_TASK_ID_INVALID);
        }
        return value;
    }

    /**
     * Parses a {@code String dayAndTime} into a {@code DayAndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dayAndTimeString} is invalid.
     */
    public static DayAndTime parseDayAndTime(String dayAndTime) throws ParseException {
        requireNonNull(dayAndTime);
        String trimmed = dayAndTime.trim();
        try {
            return new DayAndTime(trimmed);
        } catch (InvalidDayAndTimeException e) {
            throw new ParseException(DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
        } catch (InvalidTimeRangeException e) {
            throw new ParseException(DayAndTime.MESSAGE_CONSTRAINTS_TIME);
        }
    }

    /**
     * Parses a {@code String dayString} into a {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dayString} is invalid.
     */
    public static DayOfWeek parseDay(String dayString) throws ParseException {
        dayString = dayString.trim().toUpperCase();
        if (!DayAndTime.isValidDay(dayString)) {
            throw new ParseException(DayAndTime.MESSAGE_CONSTRAINTS_DAY);
        }

        return DayOfWeek.valueOf(dayString);
    }
}
