package tatracker.logic.parser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import tatracker.commons.core.index.Index;
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
            "INDEX",
            Index.MESSAGE_CONSTRAINTS, value -> true,
            "1"
    ),
    KEYWORD(
            "KEYWORD",
            "alice"
    ),
    MORE_KEYWORDS(
            "MORE_KEYWORDS",
            "bob", "charlie"
    ),

    /* Session definitions */
    START_TIME(
            "START_TIME",
            Prefixes.START_TIME,
            "", value -> true,
            "14:00"
    ),
    END_TIME(
            "END_TIME",
            Prefixes.END_TIME,
            "", value -> true,
            "16:00"
    ),
    DATE(
            "DATE",
            Prefixes.DATE,
            "", value -> true,
            "19-02-2020"
    ),
    RECUR(
            "RECUR",
            Prefixes.RECUR,
            "", value -> true,
            "1" // Number of weeks
    ),
    SESSION_TYPE(
            "SESSION_TYPE",
            Prefixes.SESSION_TYPE,
            "", value -> true,
            "grading"
    ),
    NOTES(
            "NOTES",
            Prefixes.NOTES,
            "Location: PLAB 04"
    ),

    /* Module definitions */
    MODULE(
            "MODULE",
            Prefixes.MODULE,
            "", value -> true,
            "CS3243"
    ),
    MODULE_NAME(
            "DESCRIPTION",
            Prefixes.MODULE_NAME,
            "", value -> true,
            "Introduction to AI"
    ),

    /* Group definitions */
    GROUP(
            "GROUP",
            Prefixes.GROUP,
            "", value -> true,
            "G06"
    ),
    NEWGROUP(
            "NEW_GROUP",
            Prefixes.NEWGROUP,
            "", value -> true,
            "G05"
    ),
    TYPE(
            "GROUP_TYPE",
            Prefixes.TYPE,
            "", value -> true,
            "tutorial"
    ),
    NEWTYPE(
            "NEW_TYPE",
            Prefixes.NEWTYPE,
            "", value -> true,
            "lab"
    ),

    /* Student definitions */
    MATRIC(
            "MATRIC",
            Prefixes.MATRIC,
            Matric.MESSAGE_CONSTRAINTS, Matric::isValidMatric,
            "A0181234G"
    ),
    NAME(
            "NAME",
            Prefixes.NAME,
            Name.MESSAGE_CONSTRAINTS, Name::isValidName,
            "John Doe"
    ),
    PHONE(
            "PHONE",
            Prefixes.PHONE,
            Phone.MESSAGE_CONSTRAINTS, Phone::isValidPhone,
            "98765432"
    ),
    EMAIL(
            "EMAIL",
            Prefixes.EMAIL,
            Email.MESSAGE_CONSTRAINTS, Email::isValidEmail,
            "johnd@example.com"
    ),
    RATING(
            "RATING",
            Prefixes.RATING,
            Rating.MESSAGE_CONSTRAINTS, Rating::isValidRating,
            "3"
    ),
    TAG(
            "TAG",
            Prefixes.TAG,
            Tag.MESSAGE_CONSTRAINTS, Tag::isValidTagName,
            "friends", "owes money"
    ),

    /* Action definitions */
    SORT_TYPE(
            "SORT_TYPE",
            Prefixes.SORT_TYPE,
            "", value -> true,
            "alphabetically"
    );

    private static final String DELIMITER = "/";

    private static final String OPTIONAL_FORMAT = "[%s]";
    private static final String MULTIPLE_FORMAT = "%s...";

    private final String info;
    private final Prefix prefix;

    private final String constraint;
    private final Function<String, Boolean> validator;

    private final List<String> examples;

    PrefixEntry(String info, Prefix prefix, String constraint, Function<String, Boolean> validator,
                String ... examples) {
        this.info = info;
        this.prefix = prefix;
        this.constraint = constraint;
        this.validator = validator;
        this.examples = List.of(examples);
    }

    PrefixEntry(String info, Prefix prefix, String ... examples) {
        this(info, prefix, "", value -> true, examples);
    }

    PrefixEntry(String info, String constraint, Function<String, Boolean> validator, String ... examples) {
        this(info, new Prefix(""), constraint, validator, examples);
    }

    PrefixEntry(String info, String ... examples) {
        this(info, new Prefix(""), "", value -> true, examples);
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String getConstraint() {
        return constraint;
    }

    public boolean isValidValue(String test) {
        return validator.apply(test);
    }

    public String getInfoWithPrefix() {
        return getMultipleInfoWithPrefix(prefix + info);
    }

    public String getOptionalInfoWithPrefix() {
        return getMultipleInfoWithPrefix(String.format(OPTIONAL_FORMAT, prefix + info));
    }

    public String getExamplesWithPrefix() {
        return examples.stream()
                .map(e -> prefix + e)
                .collect(Collectors.joining(" "));
    }

    private String getMultipleInfoWithPrefix(String usage) {
        if (examples.size() <= 1) {
            return usage;
        } else {
            return String.format(MULTIPLE_FORMAT, usage);
        }
    }
}
