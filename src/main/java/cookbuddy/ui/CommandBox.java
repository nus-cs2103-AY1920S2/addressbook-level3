package cookbuddy.ui;

import cookbuddy.logic.commands.CommandResult;
import cookbuddy.logic.commands.exceptions.CommandException;
import cookbuddy.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextArea commandTextArea;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        setHandler();
        // calls #setStyleToDefault() whenever there is a change to the text of the
        commandTextArea.textProperty().addListener((unused1, unused2, unused3) -> setStyleToMonospace());
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void setHandler() {
        this.commandTextArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String commandString;
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    commandString = commandTextArea.getText();
                    try {
                        commandExecutor.execute(commandString);
                        commandTextArea.setText("");
                    } catch (ParseException | CommandException e) {
                        ;
                    } finally {
                        keyEvent.consume();
                    }
                }
            }
        });
    }

    /**
     * Sets the command box style to use the default style: monospace black.
     */
    @FXML
    private void setStyleToMonospace() {
        commandTextArea.setStyle("-fx-font-family: Consolas, 'Menlo', 'Hack', 'Liberation Mono', 'monospace';");
        commandTextArea.setStyle("-fx-text-fill: black;");
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();

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
         * @see cookbuddy.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
