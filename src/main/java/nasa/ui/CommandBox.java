package nasa.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

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

    private static final HashMap<String, String> commandList = CommandHint.commandList;

    private final CommandExecutor commandExecutor;
    private final List<String> commandHistory;
    private ListIterator<String> commandHistoryIterator;

    private boolean preferenceShowHint = true;

    private String matchedCommand = "";

    private String hintPreviousCommand = "";
    //private Stage main;
    private MainWindow main;

    @FXML
    protected TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, Stage hintWindow, MainWindow main) {
        super(FXML);

        this.main = main;

        //popupCmdHint.show(this.getRoot(),100, 100);
        //popupCmdHint.ssetFocusable(false);
        //addPopup(commandTextField, popupCmdHint);

        /*
        txtCmdHint.setFocusTraversalKeysEnabled(false);
        txtCmdHint.setFocusCycleRoot(false);
        txtCmdHint.setFocusable(false);

         */
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
                    } else {
                        main.hideHint();
                    }
                    break;
            }
        });
    }

    public boolean isValidCommand() {

        boolean isCommand = false;
        // We match the longest command
        for (String command : commandList.keySet()) {
            if (commandTextField.getText().trim().startsWith(command)) {
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
           main.hideHint();
        } catch (CommandException | ParseException e) {
        }
    }

    /*
    protected static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {
                // popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

     */


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
