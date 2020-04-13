//@@author potatocombat

package tatracker.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.CommandDictionary;

/**
 * Analyses the input in the CommandBox in order to perform syntax highlight.
 */
public class CommandBoxUtil {
    private static final Pattern COMMAND_FORMAT = Pattern.compile(
            "\\s*(?<word1>\\S+)(?<args1>\\s*(?<word2>$|\\S+)(?<args2>.*))");

    private static final Pattern PREFIX = Pattern.compile("(?<prefix>\\S+/)");

    private static final Logger logger = LogsCenter.getLogger(CommandBoxUtil.class);

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
    public static List<ArgumentMatch> parseArguments(String arguments) {
        Matcher prefixer = PREFIX.matcher(arguments);

        int start = 0;

        String prefix = ""; // None

        List<ArgumentMatch> matchedArguments = new ArrayList<>();

        while (prefixer.find()) {
            int end = prefixer.start();

            if (end > start) {
                String value = arguments.substring(start, end);
                matchedArguments.add(new ArgumentMatch(prefix, value));

                prefix = prefixer.group("prefix");
            }

            start = prefixer.end();
        }
        matchedArguments.add(new ArgumentMatch(prefix, arguments.substring(start)));
        return matchedArguments;
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
        public final String prefix;
        public final String value;

        public ArgumentMatch(String prefix, String value) {
            this.prefix = prefix;
            this.value = value;
        }
    }
}
