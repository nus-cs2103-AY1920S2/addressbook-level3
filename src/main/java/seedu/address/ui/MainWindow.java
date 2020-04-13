package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.Date;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final int WALLET_TAB_INDEX = 0;
    private static final int PEOPLE_TAB_INDEX = 1;

    private static ResultDisplay resultDisplay;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private WalletTransactionsPanel walletTransactionsPanel;
    private WalletStatisticsPanel walletStatisticsPanel;
    private HelpWindow helpWindow;
    private EnterUserDataWindow enterUserDataWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane walletTransactionsPanelPlaceholder;

    @FXML
    private StackPane walletStatisticsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane mainWindowTabPane;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        enterUserDataWindow = new EnterUserDataWindow(this::storeUserData);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        walletTransactionsPanel = new WalletTransactionsPanel(logic.getFilteredTransactionList());
        walletTransactionsPanelPlaceholder.getChildren().add(walletTransactionsPanel.getRoot());

        walletStatisticsPanel = new WalletStatisticsPanel(logic.getWallet().getBudget(Date.getDefault().getMonth(),
                Date.getDefault().getYear()),
                logic.getFilteredTransactionList());
        walletStatisticsPanelPlaceholder.getChildren().add(walletStatisticsPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Handles the event when the edit user data button in the menu is clicked.
     */
    @FXML
    public void handleEditUserData() {
        openUserDataWindow();
        enterUserDataWindow.getRoot().setTitle("Edit User Data");
        enterUserDataWindow.instructionMessage.setText("Edit your details: ");

        if (!logic.isUserDataNull()) {
            enterUserDataWindow.fillInUserDetails(logic.getUserData().getUser());
        }
    }

    /**
     * Opens the window to enter user data or focuses on it if it's already opened.
     */
    public void openUserDataWindow() {
        if (!enterUserDataWindow.isShowing()) {
            enterUserDataWindow.show();
        } else {
            enterUserDataWindow.focus();
        }
    }

    /**
     * Edits the text shown in ResultDisplay.
     */
    public static void editResultDisplay(String text) {
        resultDisplay.setFeedbackToUser(text);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        enterUserDataWindow.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public WalletTransactionsPanel getWalletTransactionsPanel() {
        return walletTransactionsPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            resultDisplay.resetStyles();
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                    resultDisplay.setStyleToIndicatePass();
                }
            });

            if (commandResult.isShowHelp()) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        handleHelp();
                        resultDisplay.setStyleToIndicateNeutral();
                    }
                });
            }

            if (commandResult.isExit()) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        handleExit();
                        resultDisplay.setStyleToIndicateNeutral();
                    }
                });
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    switchPanels(commandText);
                }
            });

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateWalletPanels();
                }
            });

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    resultDisplay.setFeedbackToUser(e.getMessage());
                    resultDisplay.setStyleToIndicateFailure();
                }
            });
            throw e;
        }
    }

    /**
     * Updates the UI panels related to the wallets when a command has executed.
     */
    private void updateWalletPanels() {
        walletTransactionsPanel.update(logic.getFilteredTransactionList());
        walletStatisticsPanel.update(logic.getWallet().getBudget(Date.getDefault().getMonth(),
                Date.getDefault().getYear()),
                logic.getTransactionList());
    }

    /**
     * Switches the tabs based on whether a people or wallet command was entered.
     */
    private void switchPanels(String commandText) {
        if (commandText.contains("people")) {
            mainWindowTabPane.getSelectionModel().select(PEOPLE_TAB_INDEX);
        } else if (commandText.contains("wallet")) {
            mainWindowTabPane.getSelectionModel().select(WALLET_TAB_INDEX);
        }
    }


    /**
     * Stores the user data keyed in by the user.
     *
     * @see seedu.address.logic.Logic#storeUserData(String, String, String)
     */
    private void storeUserData(String name, String phone, String email)
            throws IllegalArgumentException, IOException {
        logic.storeUserData(name, phone, email);
    }
}
