//@@author potatocombat

package tatracker.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.core.Messages;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandDictionary;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.PrefixDetails;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.ui.CommandBoxUtil.ArgumentMatch;
import tatracker.ui.CommandBoxUtil.CommandMatch;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> implements Focusable {
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String VALID_STYLE_CLASS = "valid";

    private static final String FXML = "CommandBox.fxml";

    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);

    static {
        logger.setLevel(Level.WARNING);
    }

    private CommandDetails commandDetails = null;
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

        resetCommandDetails();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((property, oldInput, newInput) -> highlightInput(newInput));
    }

    @Override
    public void requestFocus() {
        commandTextField.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return commandTextField.isFocused();
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

        CommandMatch match = CommandBoxUtil.parseInput(input);

        if (!match.hasFullCommandWord()) {
            logger.info("======== [ Invalid input ]");
            handleInvalidInput();
            return;
        }

        changeCommandDetails(match.fullCommandWord);

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

        int numWhitespaces = CommandBoxUtil.countTrailingWhitespaces(arguments);
        logger.info("" + numWhitespaces);

        if (numWhitespaces > 0) {
            logger.info("======== [ Next argument? ]");
            handleNextArgument(arguments);
            return;
        }

        List<ArgumentMatch> matches = CommandBoxUtil.parseArguments(arguments);
        logger.info(matches.toString());

        assert matches.size() > 0; // always have preamble

        if (!dictionary.hasPreamble()) {
            logger.info("======== [ No need preamble ] ");

            String preamble = matches.remove(0).value; // remove preamble
            if (!preamble.isBlank()) {
                handleNoPrefix();
                return;
            }
        }

        for (ArgumentMatch match : matches) {
            if (!dictionary.hasPrefixDetails(match.prefix)) {
                logger.info("======== [ No prefix ] ");
                handleNoPrefix();
                return;
            }

            PrefixDetails prefixEntry = dictionary.getPrefixDetails(match.prefix);
            logger.info(String.format("======== [ %s = %s ]", prefixEntry.getPrefixWithInfo(), match.value));

            if (!prefixEntry.isValidValue(match.value.trim())) {
                logger.info("======== [ Invalid ] ");
                handleInvalidPrefix(prefixEntry);
                return;
            }
            logger.info("======== [ Valid ] ");
            handleRequiredPrefix(prefixEntry);
        }
    }

    private String getCommandFeedback() {
        return String.format("%s\nParameters: %s\nExample: %s",
                commandDetails.getInfo(),
                commandDetails.getUsage(),
                commandDetails.getExample());
    }

    private String getPrefixFeedback(PrefixDetails prefixEntry) {
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
        resetCommandDetails();
        setStyleToDefault();
    }

    private void handleInvalidInput() {
        resetCommandDetails();
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

        if (CommandBoxUtil.countTrailingWhitespaces(arguments) > 1) {
            resultDisplay.setFeedbackToUser(getCommandFeedback());
        }
    }

    private void handleNoPrefix() {
        setStyleToIndicateCommandFailure();
        resultDisplay.setFeedbackToUser(getCommandFeedback());
    }

    private void handleRequiredPrefix(PrefixDetails prefixEntry) {
        setStyleToIndicateValidCommand();
        resultDisplay.setFeedbackToUser(getPrefixFeedback(prefixEntry));
    }

    private void handleInvalidPrefix(PrefixDetails prefixEntry) {
        setStyleToIndicateCommandFailure();
        resultDisplay.setFeedbackToUser(getPrefixFeedback(prefixEntry));
    }

    /**
     * Changes the reference to one of the commands, which is specified by {@code commandWord}.
     */
    private void resetCommandDetails() {
        commandDetails = null;
        dictionary = PrefixDictionary.getEmptyDictionary();
        logger.fine("Clear command details");
    }

    /**
     * Changes the reference to one of the commands, which is specified by {@code commandWord}.
     */
    private void changeCommandDetails(String fullCommandWord) {
        // Call this only if full command word is valid
        assert CommandDictionary.hasFullCommandWord(fullCommandWord);

        CommandDetails oldDetails = commandDetails;

        commandDetails = CommandDictionary.getDetailsWithFullCommandWord(fullCommandWord);
        dictionary = commandDetails.getPrefixDictionary();

        // Compare with reference. If the same command word is logged, it means that
        //  a new CommandDetail with same internal fields was created.
        if (oldDetails == commandDetails) {
            logger.info(String.format("======== [ %s ]", commandDetails.getFullCommandWord()));
        }
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
