package com.notably.view;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import com.notably.commons.GuiSettings;
import com.notably.commons.LogsCenter;
import com.notably.logic.Logic;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;
import com.notably.view.blockcontent.BlockContent;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends ViewPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Model model;

    // Independent View parts residing in this View container
    private HelpWindow helpWindow;
    private SideBarTreeView sidebarTreeView;
    private BlockContent blockContent;
    private SuggestionsWindowView suggestionsWindowView;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane sideBarPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane blockContentPlaceholder;

    @FXML
    private VBox suggestionsWindow;

    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

        // Configure the VIEW
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = initializeHelpWindow(model);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
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
         * consume function-key events. Because CommandBox contains a TextField, some accelerators (e.g F1)
         * will not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox.
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
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getBlockDataFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, model.inputProperty());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        sidebarTreeView = new SideBarTreeView(model.getBlockTree(), model.currentlyOpenPathProperty());
        sideBarPlaceholder.getChildren().add(sidebarTreeView.getRoot());

        blockContent = new BlockContent(blockContentPlaceholder, model);

        suggestionsWindowView = new SuggestionsWindowView(model.getSuggestions(), model.responseTextProperty());
        suggestionsWindow.getChildren().add(suggestionsWindowView.getRoot());
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

    private HelpWindow initializeHelpWindow(Model model) {
        model.helpOpenProperty().addListener((Observable observable) -> {
            if (model.isHelpOpen()) {
                handleHelp();
            }
            model.setHelpOpen(false);
        });
        HelpWindow helpWindow = new HelpWindow();
        requireNonNull(helpWindow);
        return helpWindow;
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
    }

    /**
     * Executes the command and returns the result.
     *
     * @see com.notably.logic.Logic#execute(String)
     */
    private void executeCommand(String commandText) throws CommandException, ParseException {
        try {
            logic.execute(commandText);
            // logger.info("Result: " + commandResult.getFeedbackToUser());

            // if (commandResult.isShowHelp()) {
            //     handleHelp();
            // }

            // if (commandResult.isExit()) {
            //     handleExit();
            // }

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            throw e;
        }
    }
}
