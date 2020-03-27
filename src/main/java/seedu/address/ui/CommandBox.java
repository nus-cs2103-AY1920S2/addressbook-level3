package seedu.address.ui;

import java.util.LinkedList;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int MAX_BUFFER_SIZE = 100; // size of buffer to store command history

    private final CommandExecutor commandExecutor;

    private LinkedList<String> commandHistory = new LinkedList<>();
    private ListIterator<String> historyIterator = commandHistory.listIterator(0);

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.setOnKeyPressed(event -> keyPressedEvent(event));
    }

    /**
     * Event handler for text field when an key is pressed.
     * @param event KeyEvent to process.
     */
    private void keyPressedEvent(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            traverseHistory(-1);
            break;
        case DOWN:
            traverseHistory(1);
            break;
        default:
            break;
        }
    }

    /**
     * Goes through the text field history.
     * @param index positive to traverse down, negative to traverse up.
     */
    private void traverseHistory(int index) {
        String command = "";
        if (index > 0 && historyIterator.hasNext()) {
            command = historyIterator.next();
        } else if (index < 0 && historyIterator.hasPrevious()) {
            command = historyIterator.previous();
        } else {
            command = commandTextField.getText();
        }
        commandTextField.setText(command);
        commandTextField.positionCaret(command.length()); // position the text cursor at the end of the command
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String command = commandTextField.getText();
            if (commandHistory.size() >= MAX_BUFFER_SIZE) {
                commandHistory.pop();
            }
            commandHistory.offer(command);
            historyIterator = commandHistory.listIterator(commandHistory.size());
            commandExecutor.execute(command);
            commandTextField.setText("");
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
