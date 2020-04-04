package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SPECIALISATION;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_SEMESTER;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_SPECIALISATION;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.Specialisation;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SEMESTER = "Please input a positive integer as current semester!";


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
        String formattedName = name.trim().toLowerCase();
        if (name.equals("")) {
            throw new ParseException(MESSAGE_MISSING_NAME);
        }
        if (!Name.isValidName(formattedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(formattedName);
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
        if (moduleCode.equals("")) {
            throw new ParseException(MESSAGE_MISSING_MODULE);
        }
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new ParseException(MESSAGE_INVALID_MODULE);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Checks that input is an integer
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a {@code String semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static int parseSemester(String semester) throws ParseException {
        String trimmedSemester = semester.trim();
        if (semester.equals("")) {
            throw new ParseException(MESSAGE_MISSING_SEMESTER);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedSemester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        return Integer.parseInt(trimmedSemester);
    }

    /**
     * Parses a {@code String grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static String parseGrade(String grade) throws ParseException {
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return trimmedGrade;
    }

    /**
     * Parses a {@code String deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static String parseDeadline(String deadline) throws ParseException {
        String trimmedDeadline = deadline.trim();
        String[] dateTime = trimmedDeadline.split(" ");
        if (dateTime.length < 2 || !Deadline.isValidDeadline(dateTime[0], dateTime[1])) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return trimmedDeadline;
    }

    /**
     * Parses a {@code String courseName} into a {@code CourseName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code courseName} is invalid.
     */
    public static CourseName parseCourseName(String courseName) throws ParseException {
        requireNonNull(courseName);
        String trimmedCourseName = courseName.trim().toUpperCase();
        if (trimmedCourseName.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE);
        }
        if (!CourseName.isValid(trimmedCourseName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COURSE, NewCommand.MESSAGE_USAGE));
        }
        return new CourseName(trimmedCourseName);
    }

    /**
     * Parses a {@code String specialisationName} into a {@code Specialisation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code specialisationName} is invalid.
     */
    public static Specialisation parseSpecialisation(CourseName courseName, String specialisationName)
            throws ParseException {
        requireNonNull(specialisationName);
        String trimmedSpecialisation = specialisationName.trim().toUpperCase();
        if (trimmedSpecialisation.equals("")) {
            throw new ParseException(MESSAGE_MISSING_SPECIALISATION);
        }
        if (!Specialisation.isValid(courseName, trimmedSpecialisation)) {
            System.out.println(trimmedSpecialisation);
            throw new ParseException(String.format(MESSAGE_INVALID_SPECIALISATION, NewCommand.MESSAGE_USAGE));
        }
        return new Specialisation(trimmedSpecialisation);
    }

    /**
     * Parses a {@code String focusArea}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code focusArea} is invalid.
     */
    public static String parseFocusArea(String focusArea) throws ParseException {
        requireNonNull(focusArea);
        String trimmedFocusArea = focusArea.trim();
        if (trimmedFocusArea.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE_FOCUS_AREA);
        }
        return trimmedFocusArea;
    }
}
