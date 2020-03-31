package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.trie.SimilarWordsResult;
import seedu.address.logic.autocomplete.AutoComplete;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandHistory;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    // A generous estimate for the length of the user input string
    private static final int CARET_POSITION_INDEX = Integer.MAX_VALUE;

    private final CommandExecutor commandExecutor;
    private final ResultDisplay resultDisplay;
    private final CommandHistory commandHistory;
    private final AutoComplete autoComplete;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, ResultDisplay resultDisplay) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.resultDisplay = resultDisplay;
        commandHistory = new CommandHistory();
        autoComplete = new AutoComplete();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // handles the up and down arrow keys for command history
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.UP) {
                    String prevCommand = commandHistory.getPrevCommand();
                    commandTextField.setText(prevCommand);
                    commandTextField.positionCaret(CARET_POSITION_INDEX);
                } else if (ke.getCode() == KeyCode.DOWN) {
                    String nextCommand = commandHistory.getNextCommand();
                    commandTextField.setText(nextCommand);
                    commandTextField.positionCaret(CARET_POSITION_INDEX);
                }
            }
        });

        // handles the tab key for auto complete
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.TAB) {
                ke.consume();

                String currCommand = commandTextField.getText();

                // command has already been completed
                if (currCommand.contains(" ")) {
                    return;
                }

                SimilarWordsResult similarWords = autoComplete.listAllSimilarCommands(currCommand);
                String longestPrefix = similarWords.longestPrefix;
                ArrayList<String> similarCommands = similarWords.list;
                if (similarCommands.isEmpty()) {
                    resultDisplay.setFeedbackToUser("No commands found");
                } else if (similarCommands.size() == 1) {
                    resultDisplay.setFeedbackToUser("");
                    commandTextField.setText(similarCommands.get(0));
                    commandTextField.positionCaret(CARET_POSITION_INDEX);
                } else {
                    commandTextField.setText(longestPrefix);
                    commandTextField.positionCaret(CARET_POSITION_INDEX);
                    resultDisplay.setFeedbackToUser("Similar commands found: " + similarCommands.toString());
                }
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String enteredCommand = commandTextField.getText();
        commandHistory.add(enteredCommand);
        try {
            commandTextField.setText("");
            commandExecutor.execute(enteredCommand);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
