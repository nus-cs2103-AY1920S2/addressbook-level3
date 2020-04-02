package tatracker.logic.parser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import tatracker.commons.core.index.Index;
import tatracker.commons.util.DateTimeUtil;
import tatracker.commons.util.StringUtil;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.logic.commands.sort.SortType;
import tatracker.model.group.GroupType;
import tatracker.model.session.SessionType;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.tag.Tag;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public enum PrefixEntry {

    /* Placeholders */
    INDEX(
            Prefixes.INDEX,
            Index.MESSAGE_CONSTRAINTS, StringUtil::isNonZeroUnsignedInteger,
            "1"
    ),
    KEYWORD(
            Prefixes.KEYWORD,
            "alice"
    ),
    MORE_KEYWORDS(
            Prefixes.MORE_KEYWORDS,
            "bob", "charlie"
    ),
    TAB_NAME(
            Prefixes.TAB_NAME,
            Tab.MESSAGE_CONSTRAINTS, Tab::isValidTab,
            "student"
    ),

    /* Session definitions */
    START_TIME(
            Prefixes.START_TIME,
            DateTimeUtil.CONSTRAINTS_TIME, DateTimeUtil::isTime,
            "14:00"
    ),
    END_TIME(
            Prefixes.END_TIME,
            DateTimeUtil.CONSTRAINTS_TIME, DateTimeUtil::isTime,
            "16:00"
    ),
    DATE(
            Prefixes.DATE,
            DateTimeUtil.CONSTRAINTS_DATE, DateTimeUtil::isDate,
            "2020-02-19"
    ),
    RECUR(
            Prefixes.RECUR,
            "Recurring weeks must be an unsigned number", StringUtil::isNonZeroUnsignedInteger,
            "1" // Number of weeks
    ),
    SESSION_TYPE(
            Prefixes.SESSION_TYPE,
            SessionType.MESSAGE_CONSTRAINTS, SessionType::isValidSessionType,
            "grading"
    ),
    NOTES(
            Prefixes.NOTES,
            "Location: PLAB 04"
    ),

    /* Module definitions */
    MODULE(
            Prefixes.MODULE,
            "", value -> true,
            "CS3243"
    ),
    MODULE_ID(
            Prefixes.MODULE_ID,
            "", value -> true,
            "CS3243"
    ),
    MODULE_NAME(
            Prefixes.MODULE_NAME,
            "", value -> true,
            "Introduction to AI"
    ),
    MODULE_NEW_NAME(
            Prefixes.MODULE_NEW_NAME,
            "", value -> true,
            "Software Engineering"
    ),

    /* Group definitions */
    GROUP(
            Prefixes.GROUP,
            "", value -> true,
            "G06"
    ),
    NEWGROUP(
            Prefixes.NEWGROUP,
            "", value -> true,
            "G05"
    ),
    TYPE(
            Prefixes.TYPE,
            GroupType.MESSAGE_CONSTRAINTS, GroupType::isValidGroupType,
            "tutorial"
    ),
    NEWTYPE(
            Prefixes.NEWTYPE,
            GroupType.MESSAGE_CONSTRAINTS, GroupType::isValidGroupType,
            "lab"
    ),

    /* Student definitions */
    MATRIC(
            Prefixes.MATRIC,
            Matric.MESSAGE_CONSTRAINTS, Matric::isValidMatric,
            "A0181234G"
    ),
    NAME(
            Prefixes.NAME,
            Name.MESSAGE_CONSTRAINTS, Name::isValidName,
            "John Doe"
    ),
    PHONE(
            Prefixes.PHONE,
            Phone.MESSAGE_CONSTRAINTS, Phone::isValidPhone,
            "98765432"
    ),
    EMAIL(
            Prefixes.EMAIL,
            Email.MESSAGE_CONSTRAINTS, Email::isValidEmail,
            "johnd@example.com"
    ),
    RATING(
            Prefixes.RATING,
            Rating.MESSAGE_CONSTRAINTS, Rating::isValidRating,
            "3"
    ),
    TAG(
            Prefixes.TAG,
            Tag.MESSAGE_CONSTRAINTS, Tag::isValidTagName,
            "friends", "owes money"
    ),

    /* Action definitions */
    SORT_TYPE(
            Prefixes.SORT_TYPE,
            SortType.MESSAGE_CONSTRAINTS, SortType::isValidSortType,
            "alphabetically"
    );

    private static final String OPTIONAL_FORMAT = "[%s]";
    private static final String MULTIPLE_FORMAT = "%s...";

    private final Prefix prefix;

    private final String constraint;
    private final Function<String, Boolean> validator;

    private final List<String> examples;

    PrefixEntry(Prefix prefix, String constraint, Function<String, Boolean> validator,
                String ... examples) {
        this.prefix = prefix;
        this.constraint = constraint;
        this.validator = validator;
        this.examples = List.of(examples);
    }

    PrefixEntry(Prefix prefix, String ... examples) {
        this(prefix, "", value -> true, examples);
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public boolean isPrefix(String test) {
        return prefix.toString().equals(test);
    }

    public String getInfo() {
        return prefix.getInfo();
    }

    public String getConstraint() {
        return constraint;
    }

    public boolean isValidValue(String test) {
        return validator.apply(test);
    }

    public String getPrefixWithInfo() {
        return addMultiplePrefixInfo(prefix + getInfo());
    }

    public String getPrefixWithOptionalInfo() {
        return addMultiplePrefixInfo(String.format(OPTIONAL_FORMAT, prefix + getInfo()));
    }

    public String getPrefixWithExamples() {
        return examples.stream()
                .map(e -> prefix + e)
                .collect(Collectors.joining(" "));
    }

    /**
     * Adds an ellipsis for all prefixes that can be repeated.
     */
    private String addMultiplePrefixInfo(String usage) {
        if (examples.size() <= 1) {
            return usage;
        } else {
            return String.format(MULTIPLE_FORMAT, usage);
        }
    }
}
