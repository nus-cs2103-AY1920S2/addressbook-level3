package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores a list of all the commands.
 */
public class CommandDictionary {
    private static final Map<String, CommandEntry> COMMAND_WORDS = Arrays
            .stream(CommandEntry.values())
            .collect(Collectors.toUnmodifiableMap(CommandEntry::getCommandWord, command -> command));

    private static final String HELP_MESSAGE = Arrays
            .stream(CommandEntry.values())
            .map(command -> command.getCommandWord() + ": " + command.getInfo())
            .collect(Collectors.joining("\n\n"));

    /**
     * Returns the matching CommandEntry.
     */
    public static boolean hasCommandWord(String commandWord) {
        requireNonNull(commandWord);
        return COMMAND_WORDS.containsKey(commandWord);
    }

    /**
     * Returns the matching CommandEntry.
     */
    public static CommandEntry getCommandByCommandWord(String commandWord) {
        requireNonNull(commandWord);
        return COMMAND_WORDS.get(commandWord);
    }

    /**
     * Returns the message information of all the commands.
     */
    public static String getHelpMessage() {
        return HELP_MESSAGE;
    }
}
