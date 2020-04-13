// @@author potatocombat

package tatracker.logic.commands;

import java.util.List;

import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;

/**
 * Represents all compulsory information for a command.
 */
public class CommandDetails {
    private static final String NO_SUBWORD = "";

    private final String commandWord;
    private final String subWord;
    private final String info;

    private final PrefixDictionary dictionary;
    private final List<Prefix> prefixesForExample;

    private final String usage;
    private final String example;

    public CommandDetails(String commandWord,
                          String info,
                          List<Prefix> parameters, List<Prefix> optionals,
                          Prefix ... prefixesForExample) {
        this(commandWord, NO_SUBWORD, info, parameters, optionals, prefixesForExample);
    }

    public CommandDetails(String commandWord, String subWord,
                          String info,
                          List<Prefix> parameters, List<Prefix> optionals,
                          Prefix ... prefixesForExample) {
        if (commandWord.contains(" ")) {
            throw new IllegalArgumentException("CommandDetails: commandWord cannot have spaces");
        }
        this.commandWord = commandWord;

        if (subWord.contains(" ")) {
            throw new IllegalArgumentException("CommandDetails: subWord cannot have spaces");
        }
        this.subWord = subWord;

        this.info = info;

        this.dictionary = new PrefixDictionary(parameters, optionals);
        this.prefixesForExample = List.of(prefixesForExample);

        this.usage = this.dictionary.getPrefixesWithInfo();
        this.example = this.dictionary.getPrefixesWithExamples(prefixesForExample);
    }

    public String getFullCommandWord() {
        if (subWord.isEmpty()) {
            return commandWord;
        } else {
            return commandWord + " " + subWord;
        }
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getSubWord() {
        return commandWord;
    }

    public String getInfo() {
        return info;
    }

    public PrefixDictionary getPrefixDictionary() {
        return dictionary;
    }

    public List<Prefix> getParameters() {
        return dictionary.getParameters();
    }

    public List<Prefix> getOptionals() {
        return dictionary.getOptionals();
    }

    public List<Prefix> getPrefixesForExample() {
        return prefixesForExample;
    }

    public String getUsage() {
        return usage;
    }

    public String getExample() {
        return example;
    }
}
