package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COLOR_STRING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECURRENCE_TYPE;
import static seedu.address.commons.util.EventUtil.BAD_DATE_FORMAT;
import static seedu.address.commons.util.EventUtil.DAILY_RECUR_RULE;
import static seedu.address.commons.util.EventUtil.NO_RECUR_RULE;
import static seedu.address.commons.util.EventUtil.WEEKLY_RECUR_RULE;
import static seedu.address.commons.util.EventUtil.dateTimeToLocalDateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.RecurrenceType;
import seedu.address.model.student.Address;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.NextOfKin;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Temperature;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String temperature} into an {@code Temperature}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code temperature} is invalid.
     */
    public static Temperature parseTemperature(String temperature) throws ParseException {
        requireNonNull(temperature);
        String trimmedTemperature = temperature.trim();
        if (!Temperature.isValidTemperatureFirst(trimmedTemperature)) {
            throw new ParseException(Temperature.MESSAGE_CONSTRAINTS_1);
        }
        if (!Temperature.isValidTemperatureSecond(trimmedTemperature)) {
            throw new ParseException(Temperature.MESSAGE_CONSTRAINTS_2);
        }
        return new Temperature(trimmedTemperature);
    }

    /**
     * Parses a {@code String attendance} into an {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attendance} is invalid.
     */
    public static Attendance parseAttendance(String attendance) throws ParseException {
        requireNonNull(attendance);
        String trimmedAttendance = attendance.trim();
        if (!Attendance.isValidAttendance(trimmedAttendance)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return new Attendance(trimmedAttendance);
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
     * Parses a {@code String nok} into an {@code Nok}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Nok} is invalid.
     */
    public static NextOfKin parseNok(String nok) throws ParseException {
        requireNonNull(nok);
        String trimmedNok = nok.trim();
        boolean bool = NextOfKin.isValidNok(trimmedNok);
        System.out.println(bool);
        if (!NextOfKin.isValidNok(trimmedNok)) {
            throw new ParseException(NextOfKin.MESSAGE_CONSTRAINTS);
        }
        return new NextOfKin(trimmedNok);
    }

    /**
     * Parses a {@code String eventName} into a {@code eventName}.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static String parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        return eventName.trim();
    }

    /**
     * Parses {@code String localDateTimeString} into a {@code LocalDateTime}.
     */
    public static LocalDateTime parseLocalDateTime(String localDateTimeString) throws ParseException {
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(localDateTimeString);
        } catch (DateTimeParseException e) {
            throw new ParseException(BAD_DATE_FORMAT, e);
        }
        return result;
    }

    /**
     * Parses {@code String localDateString} into a {@code LocalDate}.
     */
    public static LocalDateTime parseLocalDate(String localDateString) throws ParseException {
        try {
            LocalDateTime targetDateTime = dateTimeToLocalDateTimeFormatter(localDateString);
            return targetDateTime;
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses {@code String recurrenceTypeString} into a {@code RecurrenceType}.
     */
    public static RecurrenceRule parseRecurrenceType(String recurrenceTypeString) throws ParseException {
        if (!validateRecurType(recurrenceTypeString)) {
            throw new ParseException(MESSAGE_INVALID_RECURRENCE_TYPE);
        }
        RecurrenceRule result;
        try {
            result = stringToRecurrenceRuleMapper(recurrenceTypeString);
        } catch (IllegalValueException ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * Validates if recur type is valid
     */
    public static boolean validateRecurType(String recurTypeString) {
        if (recurTypeString.equalsIgnoreCase(RecurrenceType.WEEKLY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.DAILY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.NONE.name())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Maps recurrence string into RecurrenceRule class
     */
    public static RecurrenceRule stringToRecurrenceRuleMapper(String recurrenceString) throws IllegalValueException {
        if (recurrenceString.equalsIgnoreCase("weekly")) {
            return RecurrenceRule.parse(WEEKLY_RECUR_RULE);
        } else if (recurrenceString.equalsIgnoreCase("daily")) {
            return RecurrenceRule.parse(DAILY_RECUR_RULE);
        } else if (recurrenceString.equalsIgnoreCase("none")) {
            return RecurrenceRule.parse(NO_RECUR_RULE);
        } else {
            throw new IllegalValueException("recurrence type is  invalid. Input passed: " + recurrenceString);
        }
    }

    /**
     * Parses {@code String colorCode} into a {@code colorCategoryList}.
     * @param colorCode
     * @return
     * @throws ParseException
     */
    public static ArrayList<Categories> parseColorCode(String colorCode) throws ParseException {
        if (!validateColorCode(colorCode)) {
            throw new ParseException(MESSAGE_INVALID_COLOR_STRING);
        }
        String colorCategoryString = "group" + (Integer.parseInt(colorCode) < 10 ? "0" : "") + colorCode;;
        Categories colorCategory = new Categories(colorCategoryString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        return colorCategoryList;
    }

    /**
     * Validates if color code is valid
     * valid from 0 to 23 inclusive
     */
    public static boolean validateColorCode(String colorCode) {
        // to check if the color code is in range
        try {
            Integer colorNumberInt = Integer.parseInt(colorCode);
            boolean result = colorNumberInt <= 23 && colorNumberInt >= 0;
            return result;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

