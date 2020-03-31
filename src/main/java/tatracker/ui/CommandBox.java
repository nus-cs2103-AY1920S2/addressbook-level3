package tatracker.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.Pair;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.util.StringUtil;
import tatracker.logic.commands.CommandDictionary;
import tatracker.logic.commands.CommandEntry;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.logic.parser.PrefixEntry;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String VALID_STYLE_CLASS = "valid";

    private static final String FXML = "CommandBox.fxml";

    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);

    static {
        logger.setLevel(Level.WARNING);
    }

    private static final Pattern COMMAND_FORMAT = Pattern.compile(
            "\\s*(?<word1>\\S+)(?<args1>\\s*(?<word2>$|\\S+)(?<args2>.*))");

    private static final Pattern LAST_PREFIX = Pattern.compile(
            ".*\\s+(?<prefix>\\S+/)(?<value>.*)(?<trailingSpaces>\\s*)");

    private static final Pattern FIRST_INDEX = Pattern.compile(
            "\\s*(?<index>.*)($|\\s+\\S+/.*)");

    private CommandEntry commandEntry = null;
    private PrefixDictionary dictionary = PrefixDictionary.getEmptyDictionary();

    private final CommandExecutor commandExecutor;
    private final ResultDisplay resultDisplay;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, ResultDisplay resultDisplay) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.resultDisplay = resultDisplay;

        resetCommandEntry();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((property, oldInput, newInput) -> highlightInput(newInput));
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        int size = styleClass.size();

        styleClass.removeAll(ERROR_STYLE_CLASS, VALID_STYLE_CLASS);

        if (size > styleClass.size()) {
            logger.finer("Set style to default");
        }
    }

    /**
     * Sets the command box style to indicate a valid command.
     */
    private void setStyleToIndicateValidCommand() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (!styleClass.contains(VALID_STYLE_CLASS)) {
            setStyleToDefault();
            styleClass.add(VALID_STYLE_CLASS);
            logger.finer("Set style to valid");
        }
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            setStyleToDefault();
            styleClass.add(ERROR_STYLE_CLASS);
            logger.finer("Set style to error");
        }
    }

    /**
     * Applies syntax highlighting to the text area of the command box.
     */
    private void highlightInput(String input) {
        if (input.isEmpty()) {
            logger.info("======== [ No input ]");
            resetCommandEntry();
            setStyleToDefault();
            return;
        }

        Pair<String, String> result = parseInput(input);
        final String commandWord = result.getKey();
        final String arguments = result.getValue();

        if (commandWord.isEmpty()) {
            logger.info("======== [ Invalid input ]");
            resetCommandEntry();
            setStyleToIndicateCommandFailure();
            resultDisplay.setFeedbackToUser("");
            return;
        }

        changeCommandEntry(commandWord);

        if (arguments.isEmpty()) {
            setStyleToIndicateValidCommand();
            resultDisplay.setFeedbackToUser(commandEntry.getUsage());
            return;
        }

        highlightArguments(arguments);
    }

    /**
     * Applies syntax highlighting to command arguments in the text area of the command box.
     */
    private void highlightArguments(String arguments) {
        assert !arguments.isEmpty();

        if (hasTrailingSpaces(arguments)) {
            setStyleToDefault();
            resultDisplay.setFeedbackToUser(commandEntry.getUsage());
            logger.info("======== [ Next argument? ]");
            return;
        }

        ArgumentResult result = parseArguments(arguments);

        boolean needsIndex = dictionary.hasIndex() && !StringUtil.isNonZeroUnsignedInteger(result.index);
        if (needsIndex) {
            logger.info("======== [ Needs Index ]");
        }

        if (!needsIndex && dictionary.hasPrefix(result.lastPrefix)) {
            PrefixEntry prefixEntry = dictionary.getPrefixEntry(result.lastPrefix);

            setStyleToIndicateValidCommand();
            resultDisplay.setFeedbackToUser(prefixEntry.getPrefixWithInfo());

            logger.info(String.format("======== [ %s = %s ]", prefixEntry.getPrefixWithInfo(), result.lastValue));
        } else {
            setStyleToIndicateCommandFailure();
            resultDisplay.setFeedbackToUser(commandEntry.getUsage());

            logger.info("======== [ No prefix ] ");
        }
    }

    /**
     * Returns a pair containing the command word and arguments from the given input.
     */
    private Pair<String, String> parseInput(String input) {
        Matcher matcher = COMMAND_FORMAT.matcher(input);

        if (!matcher.matches()) {
            logger.info("============ [ Invalid Command ]");
            return new Pair<>("", "");
        }

        String word1 = matcher.group("word1");
        String word2 = word1 + " " + matcher.group("word2");

        boolean isBasicCommand = CommandDictionary.hasFullCommandWord(word1);
        boolean isComplexCommand = CommandDictionary.hasFullCommandWord(word2);

        if (isComplexCommand) {
            logger.fine("============ [ Complex Command ]");
            return new Pair<>(word2, matcher.group("args2"));
        } else if (isBasicCommand) {
            logger.fine("============ [ Basic Command ]");
            return new Pair<>(word1, matcher.group("args1"));
        } else {
            logger.fine("============ [ Unknown Command ]");
            return new Pair<>("", "");
        }
    }

    /**
     * Returns a pair containing the last prefix and the value associated with it from the given input.
     */
    private ArgumentResult parseArguments(String arguments) {
        Matcher indexMatcher = FIRST_INDEX.matcher(arguments);
        Matcher prefixMatcher = LAST_PREFIX.matcher(arguments);

        boolean hasIndex = indexMatcher.matches();
        boolean hasPrefix = prefixMatcher.matches();

        if (!hasIndex && !hasPrefix) {
            logger.info("============ [ No prefixes ]");
            return new ArgumentResult(arguments.trim());
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

        return new ArgumentResult(index, prefix, value);
    }

    /**
     * Changes the reference to one of the commands, which is specified by {@code commandWord}.
     */
    private void resetCommandEntry() {
        commandEntry = null;
        dictionary = PrefixDictionary.getEmptyDictionary();
        logger.fine("Clear command entry");
    }

    /**
     * Changes the reference to one of the commands, which is specified by {@code commandWord}.
     */
    private void changeCommandEntry(String fullCommandWord) {
        // Call this only if full command word is valid
        assert CommandDictionary.hasFullCommandWord(fullCommandWord);

        CommandEntry oldEntry = commandEntry;

        commandEntry = CommandDictionary.getEntryWithFullCommandWord(fullCommandWord);
        dictionary = commandEntry.getDictionary();

        if (oldEntry != commandEntry) {
            logger.info(String.format("======== [ %s ]", commandEntry.toString()));
        }
    }

    /**
     * Returns true if the given input has trailing white spaces.
     */
    private boolean hasTrailingSpaces(String input) {
        return !input.equals(input.stripTrailing());
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see tatracker.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Wraps the result of an argument matching from a {@code Matcher}.
     */
    private static class ArgumentResult {
        public final String index;
        public final String lastPrefix;
        public final String lastValue;

        public ArgumentResult(String arguments) {
            this("", "", arguments);
        }

        public ArgumentResult(String index, String lastPrefix, String lastValue) {
            this.index = index;
            this.lastPrefix = lastPrefix;
            this.lastValue = lastValue;
        }
    }
}
