package tatracker.ui;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.Pair;

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

    private static final Pattern COMMAND_FORMAT = Pattern.compile(
            "\\s*(?<word1>\\S+)(?<args1>\\s*(?<word2>$|\\S+)(?<args2>.*))");

    private static final Pattern LAST_PREFIX = Pattern.compile(
            ".*\\s+(?<prefix>\\S+/)(?<value>.*)(?<trailingSpaces>\\s*)");

    private CommandEntry commandEntry;
    private Map<String, PrefixEntry> dictionary;

    // private final Logger logger = LogsCenter.getLogger(getClass());

    private final CommandExecutor commandExecutor;
    private final ResultDisplay resultDisplay;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, ResultDisplay resultDisplay) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.resultDisplay = resultDisplay;

        this.commandEntry = null;
        this.dictionary = Map.of();

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
        // int size = styleClass.size();

        commandTextField.getStyleClass().removeAll(ERROR_STYLE_CLASS, VALID_STYLE_CLASS);

        // if (size > styleClass.size()) {
        //     logger.info("Set style to default");
        // }
    }

    /**
     * Sets the command box style to indicate a valid command.
     */
    private void setStyleToIndicateValidCommand() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (!styleClass.contains(VALID_STYLE_CLASS)) {
            setStyleToDefault();
            styleClass.add(VALID_STYLE_CLASS);
            // logger.info("Set style to valid");
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
            // logger.info("Set style to error");
        }
    }

    /**
     * Applies syntax highlighting to the text area of the command box.
     */
    private void highlightInput(String input) {
        if (input.isEmpty()) {
            // logger.info("No input");
            setStyleToDefault();
            return;
        }

        Pair<String, String> result = parseInput(input);
        final String commandWord = result.getKey();
        final String arguments = result.getValue();

        changeCommandEntry(commandWord);

        if (commandWord.isEmpty()) {
            // logger.info("Invalid input");
            setStyleToIndicateCommandFailure();
            resultDisplay.setFeedbackToUser("");
            return;
        }
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
            // logger.info("BLANK ARGUMENT");
            return;
        }

        Pair<String, String> result = parseArguments(arguments);
        final String prefix = result.getKey();
        final String value = result.getValue();

        if (dictionary.containsKey(prefix)) {
            PrefixEntry prefixEntry = dictionary.get(prefix);

            setStyleToIndicateValidCommand();
            resultDisplay.setFeedbackToUser(prefixEntry.getPrefixWithInfo());

            // logger.info(String.format("Prefix: %s | Value: %s", prefixEntry.getPrefixWithInfo(), value));
        } else {
            setStyleToIndicateCommandFailure();
            resultDisplay.setFeedbackToUser("Invalid prefix");

            // logger.info("Invalid Prefix: " + prefix);
        }
    }

    /**
     * Returns a pair containing the command word and arguments from the given input.
     */
    private Pair<String, String> parseInput(String input) {
        Matcher matcher = COMMAND_FORMAT.matcher(input);

        if (!matcher.matches()) {
            // logger.info("Invalid Command");
            return new Pair<>("", "");
        }

        String word1 = matcher.group("word1");
        String word2 = word1 + " " + matcher.group("word2");

        boolean isBasicCommand = CommandDictionary.hasCommandWord(word1);
        boolean isComplexCommand = CommandDictionary.hasCommandWord(word2);

        if (isComplexCommand) {
            // logger.info("Complex Command");
            return new Pair<>(word2, matcher.group("args2"));
        } else if (isBasicCommand) {
            // logger.info("Basic Command");
            return new Pair<>(word1, matcher.group("args1"));
        } else {
            // logger.info("Unknown Command");
            return new Pair<>("", "");
        }
    }

    /**
     * Returns a pair containing the last prefix and the value associated with it from the given input.
     */
    private Pair<String, String> parseArguments(String arguments) {
        Matcher matcher = LAST_PREFIX.matcher(arguments);

        if (matcher.matches()) {
            // logger.info("Matched prefixes");
            return new Pair<>(matcher.group("prefix"), matcher.group("value"));
        } else {
            // logger.info("No prefixes");
            return new Pair<>("", arguments.trim());
        }
    }

    /**
     * Changes the reference to one of the commands, which is specified by {@code commandWord}.
     */
    private void changeCommandEntry(String commandWord) {
        if (commandWord.isEmpty()) {
            commandEntry = null;
            dictionary = Map.of();
        } else {
            commandEntry = CommandDictionary.getCommandEntry(commandWord);
            dictionary = PrefixDictionary.createPrefixDictionary(
                    commandEntry.getParameters(), commandEntry.getOptionals());
        }

        // logger.info(commandEntry == null ? "NO COMMAND" : commandEntry.toString());
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

}
