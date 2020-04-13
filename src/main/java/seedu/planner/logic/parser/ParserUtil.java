package seedu.planner.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.grades.LetterGrade;
import seedu.planner.model.module.ModuleCode;
import seedu.planner.model.student.Major;
import seedu.planner.model.student.Name;
import seedu.planner.model.time.DegreeYear;
import seedu.planner.model.time.Semester;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index must be a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DEGREE_YEAR = DegreeYear.MESSAGE_CONSTRAINTS;

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
     * Parses {@code year} into a {@code DegreeYear} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified degree year is invalid.
     */
    public static DegreeYear parseYear(String year) throws ParseException {
        String trimmedYear = year.trim();
        if (!StringUtil.isNonNegativeUnsignedInteger(trimmedYear)) {
            throw new ParseException(MESSAGE_INVALID_DEGREE_YEAR);
        } else {
            int yearInt = Integer.parseInt(trimmedYear);
            if (yearInt < DegreeYear.MIN_VALUE || yearInt > DegreeYear.MAX_VALUE) { // Year 1 to 6 inclusive
                throw new ParseException(MESSAGE_INVALID_DEGREE_YEAR);
            }
        }
        return new DegreeYear(Integer.parseInt(trimmedYear));
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ModuleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedName = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedName)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedName);
    }

    /**
     * Parses a {@code String moduleCodes} into a list of {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if any of the given {@code ModuleCode} is invalid.
     */
    public static List<ModuleCode> parseModuleCodes(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedName = moduleCode.trim();
        String[] moduleCodeStrings = trimmedName.split("\\s+");
        List<ModuleCode> moduleCodes = new ArrayList<>();
        for (String moduleCodeString : moduleCodeStrings) {
            moduleCodes.add(parseModuleCode(moduleCodeString));
        }
        return moduleCodes;
    }

    /**
     * Parses a {@code String semester} into a {@code Semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Semester} is invalid.
     */
    public static Semester parseSemester(String semester) throws ParseException {
        requireNonNull(semester);
        switch (semester.trim()) {
        case "1":
            return Semester.ONE;
        case "2":
            return Semester.TWO;
        case "st1":
            return Semester.SPECIAL_TERM_ONE;
        case "st2":
            return Semester.SPECIAL_TERM_TWO;
        default:
            throw new ParseException(Semester.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        try {
            return new Name(name.trim());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String major} into a {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        try {
            return new Major(major.trim());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String letterGrade} into a {@code LetterGrade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LetterGrade} is invalid.
     */
    public static LetterGrade parseLetterGrade(String letterGrade) throws ParseException {
        requireNonNull(letterGrade);
        try {
            return LetterGrade.fromInputName(letterGrade.trim());
        } catch (IllegalArgumentException e) {
            throw new ParseException(LetterGrade.MESSAGE_CONSTRAINTS);
        }
    }

    //@@author gruntultra

    /**
     * Parses a {@code String lessonNumber} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String} is invalid.
     */
    public static String parseLessonNumber(String lessonNumber) throws ParseException {
        requireNonNull(lessonNumber);
        try {
            return lessonNumber;
        } catch (IllegalArgumentException e) {
            throw new ParseException("Lessons Should be a number");
        }
    }
    //@@author

    //@@author thetruevincentchow

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    //@@author
}
