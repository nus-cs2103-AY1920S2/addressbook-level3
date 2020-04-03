package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores a list of all the commands.
 */
public class CommandDictionary {
    private static final Map<String, CommandEntry> FULL_COMMAND_WORDS = Arrays
            .stream(CommandEntry.values())
            .collect(Collectors.toUnmodifiableMap(CommandEntry::getFullCommandWord, entry -> entry));

    private static final List<CommandDetails> COMMAND_DETAILS = Arrays
            .stream(CommandEntry.values())
            .map(CommandEntry::getCommandDetails)
            .collect(Collectors.toUnmodifiableList());

    private static final String HELP_MESSAGE = Arrays
            .stream(CommandEntry.values())
            .map(CommandDictionary::formatHelpMessage)
            .collect(Collectors.joining("\n\n"));

    /**
     * Returns true if the {@code fullCommandWord} is valid.
     */
    public static boolean hasFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.containsKey(fullCommandWord);
    }

    /**
     * Returns the matching CommandEntry based on the {@code fullCommandWord}.
     */
    public static CommandEntry getEntryWithFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.get(fullCommandWord);
    }

    /**
     * Returns the message information of all the commands.
     */
    public static String getHelpMessage() {
        return HELP_MESSAGE;
    }

    /**
     * Returns the message information of all the commands.
     */
    public static List<CommandDetails> getCommandDetails() {
        return COMMAND_DETAILS;
    }

    /**
     * Formats the help info of a command entry.
     */
    private static String formatHelpMessage(CommandEntry entry) {
        return String.format("%s: %s", entry.getFullCommandWord(), entry.getInfo());
    }
}
