package nasa.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import nasa.logic.commands.CommandHint;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final HashMap<String, String> commandList = CommandHint.getCommandList();

    @FXML
    protected TextField commandTextField;

    private final CommandExecutor commandExecutor;
    private final List<String> commandHistory;
    private ListIterator<String> commandHistoryIterator;

    private boolean preferenceShowHint = true;

    private String matchedCommand = "";
    private MainWindow main;

    public CommandBox(CommandExecutor commandExecutor, MainWindow main) {
        super(FXML);

        this.main = main;

        this.commandExecutor = commandExecutor;
        commandHistory = new LinkedList<>();
        commandHistoryIterator = commandHistory.listIterator();


        commandTextField.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            switch (key.getCode()) {
            case UP:
                if (commandHistoryIterator.hasPrevious()) {
                    commandTextField.setText(commandHistoryIterator.previous());
                }
                break;
            case DOWN:
                if (commandHistoryIterator.hasNext()) {
                    commandTextField.setText(commandHistoryIterator.next());
                }
                break;
            case H:
                if (key.isControlDown()) {
                    commandTextField.setText("help");
                    handleCommandEntered();
                }
                break;
            default:
                if (isValidCommand()) {
                    main.getHint(commandList.get(matchedCommand));
                    commandTextField.requestFocus();
                } else if (main.isHintShowing()) {
                    main.hideHint();
                }
                break;
            }
        });
    }

    /**
     * Verifies if text in command field is a valid command.
     * @return
     */
    public boolean isValidCommand() {

        boolean isCommand = false;
        // We match the longest command
        for (String command : commandList.keySet()) {
            if (commandTextField.getText().trim().toLowerCase().startsWith(command)) {
                isCommand = true;
                matchedCommand = command;
            }
        }
        return isCommand;
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandHistory.add(commandTextField.getText());
            commandHistoryIterator = commandHistory
                    .listIterator(commandHistory.size());
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
         * @see nasa.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
