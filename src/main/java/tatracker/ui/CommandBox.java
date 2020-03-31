package tatracker.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.core.Messages;
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
        this.resultDisplay.setFeedbackToUser(Messages.MESSAGE_WELCOME + Messages.MESSAGE_HELP);

        resetCommandEntry();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((property, oldInput, newInput) -> highlightInput(newInput));
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
            handleNoInput();
            return;
        }

        CommandMatch match = parseInput(input);

        if (!match.hasFullCommandWord()) {
            logger.info("======== [ Invalid input ]");
            handleInvalidInput();
            return;
        }

        changeCommandEntry(match.fullCommandWord);

        if (!match.hasArguments()) {
            handleNoArguments();
            return;
        }

        highlightArguments(match.arguments);
    }

    /**
     * Applies syntax highlighting to command arguments in the text area of the command box.
     */
    private void highlightArguments(String arguments) {
        assert !arguments.isEmpty();

        logger.info("" + getTrailingWhitespaces(arguments));

        if (getTrailingWhitespaces(arguments) > 0) {
            logger.info("======== [ Next argument? ]");
            handleNextArgument(arguments);
            return;
        }

        ArgumentMatch result = parseArguments(arguments);

        boolean needsIndex = dictionary.hasIndex() && !result.hasValidIndex();
        if (needsIndex) {
            logger.info("======== [ Needs Index ]");
        }

        boolean hasRequiredPrefix = dictionary.hasPrefix(result.lastPrefix);
        if (!needsIndex && hasRequiredPrefix) {
            PrefixEntry prefixEntry = dictionary.getPrefixEntry(result.lastPrefix);

            logger.info(String.format("======== [ %s = %s ]", prefixEntry.getPrefixWithInfo(), result.lastValue));
            handleRequiredPrefix(prefixEntry);
        } else {
            logger.info("======== [ No prefix ] ");
            handleNoPrefix();
        }
    }

    private String getCommandFeedback() {
        return String.format("%s\nParameters: %s\nExample: %s",
                commandEntry.getInfo(),
                commandEntry.getUsage(),
                commandEntry.getExample());
    }

    private String getPrefixFeedback(PrefixEntry prefixEntry) {
        return String.format("%s\n%s\nExample: %s",
                prefixEntry.getPrefixWithInfo(),
                prefixEntry.getConstraint(),
                prefixEntry.getPrefixWithExamples());
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

    private void handleNoInput() {
        resetCommandEntry();
        setStyleToDefault();
        resultDisplay.setFeedbackToUser(Messages.MESSAGE_HELP);
    }

    private void handleInvalidInput() {
        resetCommandEntry();
        setStyleToIndicateCommandFailure();
        resultDisplay.setFeedbackToUser("");
    }

    private void handleNoArguments() {
        setStyleToIndicateValidCommand();
        resultDisplay.setFeedbackToUser(getCommandFeedback());
    }

    /**
     * Controls the result of entering trailing spaces at the end of a command.
     * @param arguments to count the number of trailing whitespaces in the command.
     */
    private void handleNextArgument(String arguments) {
        setStyleToDefault();

        if (getTrailingWhitespaces(arguments) > 1) {
            resultDisplay.setFeedbackToUser(getCommandFeedback());
        }
    }

    private void handleNoPrefix() {
        setStyleToIndicateCommandFailure();
        resultDisplay.setFeedbackToUser(getCommandFeedback());
    }

    private void handleRequiredPrefix(PrefixEntry prefixEntry) {
        setStyleToIndicateValidCommand();
        resultDisplay.setFeedbackToUser(getPrefixFeedback(prefixEntry));
    }

    /**
     * Returns a pair containing the command word and arguments from the given input.
     */
    private CommandMatch parseInput(String input) {
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
    private ArgumentMatch parseArguments(String arguments) {
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
     * Returns true if the given input has more than one whitespace at the end.
     */
    private int getTrailingWhitespaces(String input) {
        requireNonNull(input);
        int length = input.length();
        int trimmedLength = input.stripTrailing().length();

        assert trimmedLength <= length;

        return length - trimmedLength;
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
     * Wraps the result of an command matching from a {@code Matcher}.
     */
    private static class CommandMatch {
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
    private static class ArgumentMatch {
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
