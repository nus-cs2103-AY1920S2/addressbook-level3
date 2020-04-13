//@@author potatocombat

package tatracker.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tatracker.commons.core.index.Index;
import tatracker.commons.util.DateTimeUtil;
import tatracker.commons.util.StringUtil;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.logic.commands.sort.SortType;
import tatracker.model.TaTracker;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
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
public class PrefixDictionary {
    private static final List<PrefixDetails> PREFIX_DETAILS = List.of(
            /* Placeholders */
            new PrefixDetails(Prefixes.INDEX,
                    Index.MESSAGE_CONSTRAINTS, StringUtil::isNonZeroUnsignedInteger,
                    "1"
            ),
            new PrefixDetails(Prefixes.TAB_NAME,
                    Tab.MESSAGE_CONSTRAINTS, Tab::isValidTab,
                    "student"
            ),
            new PrefixDetails(Prefixes.RATE,
                    TaTracker.CONSTRAINTS_RATE, StringUtil::isNonZeroUnsignedInteger,
                    "40"
            ),

            /* Session definitions */
            new PrefixDetails(Prefixes.START_TIME,
                    DateTimeUtil.CONSTRAINTS_TIME, DateTimeUtil::isTime,
                    "14:00"
            ),
            new PrefixDetails(Prefixes.END_TIME,
                    DateTimeUtil.CONSTRAINTS_TIME, DateTimeUtil::isTime,
                    "16:00"
            ),
            new PrefixDetails(Prefixes.DATE,
                    DateTimeUtil.CONSTRAINTS_DATE, DateTimeUtil::isDate,
                    "2020-02-19"
            ),
            new PrefixDetails(Prefixes.RECUR,
                    Session.CONSTRAINTS_RECURRING_WEEKS, StringUtil::isUnsignedInteger,
                    "1" // Number of weeks
            ),
            new PrefixDetails(Prefixes.SESSION_TYPE,
                    SessionType.MESSAGE_CONSTRAINTS, SessionType::isValidSessionType,
                    "grading"
            ),
            new PrefixDetails(Prefixes.NOTES,
                    "Location: PLAB 04"
            ),

            /* Module definitions */
            new PrefixDetails(Prefixes.MODULE,
                    Module.CONSTRAINTS_MODULE_CODE, value -> !value.isBlank(),
                    "CS3243"
            ),
            new PrefixDetails(Prefixes.MODULE_ID,
                    Module.CONSTRAINTS_MODULE_CODE, value -> !value.isBlank(),
                    "CS3243"
            ),
            new PrefixDetails(Prefixes.MODULE_NAME,
                    Module.CONSTRAINTS_MODULE_NAME, value -> !value.isBlank(),
                    "Introduction to AI"
            ),
            new PrefixDetails(Prefixes.MODULE_NEW_NAME,
                    Module.CONSTRAINTS_MODULE_NAME, value -> !value.isBlank(),
                    "Software Engineering"
            ),

            /* Group definitions */
            new PrefixDetails(Prefixes.GROUP,
                    Group.CONSTRAINTS_GROUP_CODE, value -> !value.isBlank(),
                    "G06"
            ),
            new PrefixDetails(Prefixes.NEWGROUP,
                    Group.CONSTRAINTS_GROUP_CODE, value -> !value.isBlank(),
                    "G05"
            ),
            new PrefixDetails(Prefixes.TYPE,
                    GroupType.MESSAGE_CONSTRAINTS, GroupType::isValidGroupType,
                    "tutorial"
            ),
            new PrefixDetails(Prefixes.NEWTYPE,
                    GroupType.MESSAGE_CONSTRAINTS, GroupType::isValidGroupType,
                    "lab"
            ),

            /* Student definitions */
            new PrefixDetails(Prefixes.MATRIC,
                    Matric.MESSAGE_CONSTRAINTS, Matric::isValidMatric,
                    "A0181234G"
            ),
            new PrefixDetails(Prefixes.NAME,
                    Name.MESSAGE_CONSTRAINTS, Name::isValidName,
                    "John Doe"
            ),
            new PrefixDetails(Prefixes.PHONE,
                    Phone.MESSAGE_CONSTRAINTS, Phone::isValidPhone,
                    "98765432"
            ),
            new PrefixDetails(Prefixes.EMAIL,
                    Email.MESSAGE_CONSTRAINTS, Email::isValidEmail,
                    "johnd@example.com"
            ),
            new PrefixDetails(Prefixes.RATING,
                    Rating.MESSAGE_CONSTRAINTS, Rating::isValidRating,
                    "3"
            ),
            new PrefixDetails(Prefixes.TAG,
                    Tag.MESSAGE_CONSTRAINTS, Tag::isValidTagName,
                    "friends", "owes money"
            ),

            /* Action definitions */
            new PrefixDetails(Prefixes.SORT_TYPE,
                    SortType.MESSAGE_CONSTRAINTS, SortType::isValidSortType,
                    "alphabetically"
            )
    );

    /** Collects all the prefix entries into a lookup table. */
    private static final Map<Prefix, PrefixDetails> PREFIXES = PREFIX_DETAILS
            .stream()
            .collect(Collectors.toUnmodifiableMap(PrefixDetails::getPrefix,
                entry -> entry, (key1, key2) -> {
                    throw new IllegalArgumentException("PrefixDictionary: cannot have duplicate prefixes");
                }));

    private static final PrefixDictionary EMPTY_DICTIONARY = new PrefixDictionary();

    private static final String MESSAGE_UNKNOWN_ENTRY = "PrefixDictionary Instance: does not contain the prefix: %s";

    // ======== Instance Declaration ========

    private final Map<String, PrefixDetails> dictionary;

    private final List<Prefix> parameters;
    private final List<Prefix> optionals;

    public PrefixDictionary() {
        this(List.of(), List.of());
    }

    public PrefixDictionary(List<Prefix> parameters) {
        this(parameters, List.of());
    }

    public PrefixDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        requireNonNull(parameters);
        requireNonNull(optionals);

        this.parameters = List.copyOf(parameters);
        this.optionals = List.copyOf(optionals);

        this.dictionary = createDictionary(this.parameters, this.optionals);
    }

    /**
     * Creates a new Prefix dictionary for syntax matching.
     *
     * If there are duplicate prefixes, the first inserted prefix is kept, and the rest are skipped.
     * Duplicates are skipped since repeated prefixes must mean the same thing, or else
     * it will be unclear what the prefix represents.
     *
     * Each prefix in {@code parameters} and {@code optionals} must have a matching {@code PrefixDetails}.
     */
    private static Map<String, PrefixDetails> createDictionary(List<Prefix> parameters, List<Prefix> optionals) {
        List<Prefix> arguments = new ArrayList<>(parameters);
        arguments.addAll(optionals);

        if (!arguments.stream().allMatch(PREFIXES::containsKey)) {
            throw new IllegalArgumentException(
                    "PrefixDictionary Instance: prefix is not recognized");
        }

        return arguments.stream()
                .collect(Collectors.toUnmodifiableMap(Prefix::getPrefix,
                    PREFIXES::get, (key1, key2) -> {
                        throw new IllegalArgumentException(
                                "PrefixDictionary Instance: arguments cannot have the same prefix");
                    }));
    }

    /**
     * Returns the same unmodifiable empty {@code PrefixDictionary}.
     */
    public static PrefixDictionary getEmptyDictionary() {
        return EMPTY_DICTIONARY;
    }

    public List<Prefix> getParameters() {
        return parameters;
    }

    public List<Prefix> getOptionals() {
        return optionals;
    }

    /**
     * Returns true if the dictionary requires a preamble.
     */
    public boolean hasPreamble() {
        return dictionary.keySet().stream().anyMatch(String::isBlank);
    }

    /**
     * Returns true if the {@code prefix} is part of the dictionary.
     */
    public boolean hasPrefixDetails(String prefix) {
        requireNonNull(prefix);
        return dictionary.containsKey(prefix);
    }

    public PrefixDetails getPrefixDetails(String prefix) {
        requireNonNull(prefix);
        return dictionary.get(prefix);
    }

    /**
     * Returns true if the {@code prefix} is part of the dictionary.
     */
    public String getPrefixesWithInfo() {
        Stream<String> arguments = Stream.concat(
                parameters.stream()
                        .map(Prefix::getPrefix)
                        .map(dictionary::get)
                        .map(PrefixDetails::getPrefixWithInfo),
                optionals.stream()
                        .map(Prefix::getPrefix)
                        .map(dictionary::get)
                        .map(PrefixDetails::getPrefixWithOptionalInfo)
        );
        return arguments.collect(Collectors.joining(" "));
    }

    /**
     * Returns a String joining all the prefix examples in {@code prefixes}.
     * Each prefix in {@code prefixes} must have a matching PrefixDetails in the current dictionary.
     *
     * @throws IllegalArgumentException if there is a prefix in {@code prefixes}
     * that does not match an entries in the current dictionary.
     */
    public String getPrefixesWithExamples(Prefix ... prefixes) throws IllegalArgumentException {
        Set<Prefix> arguments = new HashSet<>();
        for (Prefix p : prefixes) {
            if (arguments.contains(p)) {
                throw new IllegalArgumentException("PrefixDictionary Instance: cannot have duplicate prefixes");
            }
            arguments.add(p);
        }

        return arguments.stream()
                .map(p -> {
                    String prefixId = p.getPrefix();
                    if (!hasPrefixDetails(prefixId)) {
                        throw new IllegalArgumentException(String.format(MESSAGE_UNKNOWN_ENTRY, p.getInfo()));
                    }
                    return dictionary.get(prefixId).getPrefixWithExamples();
                })
                .collect(Collectors.joining(" "));
    }
}
