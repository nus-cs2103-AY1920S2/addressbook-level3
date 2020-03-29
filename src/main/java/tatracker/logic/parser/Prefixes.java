package tatracker.logic.parser;

import java.util.Arrays;
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
public class Prefixes {

    /* Placeholders */
    public static final Prefix INDEX = new Prefix("", Index.MESSAGE_CONSTRAINTS, "INDEX", "1");

    /* Session definitions */
    public static final Prefix START_TIME = new Prefix("s/", "", "START_TIME", "14:00");
    public static final Prefix END_TIME = new Prefix("e/", "", "END_TIME", "16:00");
    public static final Prefix DATE = new Prefix("d/", "", "DATE", "19-02-2020");
    public static final Prefix RECUR = new Prefix("w/");
    public static final Prefix SESSION_TYPE = new Prefix("t/", "", "SESSION_TYPE", "grading");
    public static final Prefix NOTES = new Prefix("n/", "", "NOTES", "Location: PLAB 04");

    /* Module definitions */
    public static final Prefix MODULE = new Prefix("m/", "", "MODULE", "CS3243");
    public static final Prefix MODULE_NAME = new Prefix("n/", "", "DESCRIPTION", "Introduction to AI");

    /* Group definitions */
    public static final Prefix GROUP = new Prefix("g/", "", "GROUP", "G06");
    public static final Prefix NEWGROUP = new Prefix("ng/", "", "NEW_GROUP", "G05");
    public static final Prefix TYPE = new Prefix("t/", "", "GROUP_TYPE", "tutorial");
    public static final Prefix NEWTYPE = new Prefix("nt/", "", "NEW_TYPE", "lab");

    /* Student definitions */
    public static final Prefix MATRIC = new Prefix("id/", Matric.MESSAGE_CONSTRAINTS, "MATRIC", "A0181234G");
    public static final Prefix NAME = new Prefix("n/", Name.MESSAGE_CONSTRAINTS, "NAME", "John Doe");
    public static final Prefix PHONE = new Prefix("p/", Phone.MESSAGE_CONSTRAINTS, "PHONE", "98765432");
    public static final Prefix EMAIL = new Prefix("e/", Email.MESSAGE_CONSTRAINTS, "EMAIL", "johnd@example.com");
    public static final Prefix RATING = new Prefix("r/", Rating.MESSAGE_CONSTRAINTS, "RATING", "3");
    public static final Prefix TAG = new Prefix("t/", Tag.MESSAGE_CONSTRAINTS, "TAG", "friends", "owes money");

    /* Action definitions */
    public static final Prefix SORT_TYPE = new Prefix("t/", "", "SORT_TYPE", "alphabetically");

    public static String getExamples(Prefix ... prefixes) {
        return formatPrefixes(Arrays.asList(prefixes), Prefix::getExamples);
    }

    public static String getUsages(List<Prefix> parameters) {
        return getUsages(parameters, List.of());
    }

    public static String getUsages(List<Prefix> parameters, List<Prefix> optionals) {
        return String.format("%s %s",
                formatPrefixes(parameters, Prefix::getUsage),
                formatPrefixes(optionals, Prefix::getOptionalUsage));
    }

    private static String formatPrefixes(List<Prefix> prefixes, Function<Prefix, String> mapper) {
        return prefixes.stream()
                .map(mapper)
                .collect(Collectors.joining(" "));
    }
}
