package tatracker.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.util.StringUtil;
import tatracker.logic.commands.CommandDictionary;

/**
 * Analyses the input in the CommandBox in order to perform syntax highlight.
 */
public class CommandBoxParser {
    private static final Pattern COMMAND_FORMAT = Pattern.compile(
            "\\s*(?<word1>\\S+)(?<args1>\\s*(?<word2>$|\\S+)(?<args2>.*))");

    private static final Pattern LAST_PREFIX = Pattern.compile(
            ".*\\s+(?<prefix>\\S+/)(?<value>.*)(?<trailingSpaces>\\s*)");

    private static final Pattern FIRST_INDEX = Pattern.compile(
            "\\s*(?<index>.*)($|\\s+\\S+/.*)");

    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);

    static {
        logger.setLevel(Level.WARNING);
    }

    /**
     * Returns a pair containing the command word and arguments from the given input.
     */
    public static CommandMatch parseInput(String input) {
        Matcher matcher = COMMAND_FORMAT.matcher(input);

        if (!matcher.matches()) {
            logger.info("============ [ Invalid Command ]");
            return new CommandMatch();
        }

        String word1 = matcher.group("word1");
        String word2 = word1 + " " + matcher.group("word2");

        boolean isBasicCommand = CommandDictionary.hasFullCommandWord(word1);
        boolean isComplexCommand = CommandDictionary.hasFullCommandWord(word2);

        if (isComplexCommand) {
            logger.fine("============ [ Complex Command ]");
            return new CommandMatch(word2, matcher.group("args2"));
        } else if (isBasicCommand) {
            logger.fine("============ [ Basic Command ]");
            return new CommandMatch(word1, matcher.group("args1"));
        } else {
            logger.fine("============ [ Unknown Command ]");
            return new CommandMatch();
        }
    }

    /**
     * Returns a pair containing the last prefix and the value associated with it from the given input.
     */
    public static ArgumentMatch parseArguments(String arguments) {
        Matcher indexMatcher = FIRST_INDEX.matcher(arguments);
        Matcher prefixMatcher = LAST_PREFIX.matcher(arguments);

        boolean hasIndex = indexMatcher.matches();
        boolean hasPrefix = prefixMatcher.matches();

        if (!hasIndex && !hasPrefix) {
            logger.info("============ [ No prefixes ]");
            return new ArgumentMatch(arguments.trim());
        }

        String index = "";
        String prefix = "";
        String value = arguments;

        if (hasIndex) {
            logger.info("============ [ Matched index ]");
            index = indexMatcher.group("index");
        }

        if (hasPrefix) {
            logger.info("============ [ Matched prefixes ]");
            prefix = prefixMatcher.group("prefix");
            value = prefixMatcher.group("value");
        }

        return new ArgumentMatch(index, prefix, value);
    }

    /**
     * Returns true if the given input has more than one whitespace at the end.
     */
    public static int countTrailingWhitespaces(String input) {
        requireNonNull(input);
        int length = input.length();
        int trimmedLength = input.stripTrailing().length();

        assert trimmedLength <= length;

        return length - trimmedLength;
    }

    /**
     * Wraps the result of an command matching from a {@code Matcher}.
     */
    public static class CommandMatch {
        public final String fullCommandWord;
        public final String arguments;

        public CommandMatch() {
            this("", "");
        }

        public CommandMatch(String fullCommandWord, String arguments) {
            this.fullCommandWord = fullCommandWord;
            this.arguments = arguments;
        }

        public boolean hasFullCommandWord() {
            return !fullCommandWord.isEmpty();
        }

        public boolean hasArguments() {
            return !arguments.isEmpty();
        }
    }

    /**
     * Wraps the result of an argument matching from a {@code Matcher}.
     */
    public static class ArgumentMatch {
        public final String index;
        public final String lastPrefix;
        public final String lastValue;

        public ArgumentMatch(String arguments) {
            this("", "", arguments);
        }

        public ArgumentMatch(String index, String lastPrefix, String lastValue) {
            this.index = index;
            this.lastPrefix = lastPrefix;
            this.lastValue = lastValue;
        }

        public boolean hasValidIndex() {
            return StringUtil.isNonZeroUnsignedInteger(index);
        }
    }
}
