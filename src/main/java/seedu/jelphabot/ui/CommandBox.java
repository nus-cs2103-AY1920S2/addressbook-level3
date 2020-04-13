package seedu.jelphabot.ui;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.jelphabot.logic.commands.CommandResult;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.logic.parser.exceptions.ParseException;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static List<String> commandsHistory = new CopyOnWriteArrayList<>();

    private final CommandExecutor commandExecutor;

    private ListIterator<String> command;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // Replicates behaviour of CLI when UP or DOWN key is pressed
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                commandTextField.setText(getPrevCommand());
            } else if (event.getCode() == KeyCode.DOWN) {
                commandTextField.setText(getNextCommand());
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandsHistory.add(commandTextField.getText());
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
            command = null;
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
     * Returns the user's previous command in String form.
     */
    private String getPrevCommand() {
        if (command == null) {
            command = commandsHistory.listIterator(commandsHistory.size());
        }
        if (command.hasPrevious()) {
            return command.previous();
        }
        return "";
    }

    /**
     * Returns the user's next command in String form.
     */
    private String getNextCommand() {
        if (command == null) {
            command = commandsHistory.listIterator(commandsHistory.size());
        }
        if (command.hasNext()) {
            return command.next();
        }
        return "";
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.jelphabot.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
