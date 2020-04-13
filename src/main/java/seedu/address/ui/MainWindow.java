package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private NotesListPanel notesListPanel;
    private DiaryListPanel diaryListPanel;
    private CalenderPanel calenderPanel;
    private CalenderListPanel calenderListPanel;
    private ModulesTakenListPanel modulesTakenListPanel;
    private ModulesTakenListPanel modulesTakenListPanel2;
    private ProfileMainScreen profileMainScreen;
    private DiaryEntryMainPage diaryEntryMainPage;
    private ModulesYetTaken modulesYetTaken;


    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane notesListPanelPlaceholder;

    @FXML
    private AnchorPane diaryListPanelPlaceholder;

    @FXML
    private AnchorPane diaryFullViewPlaceholder;

    @FXML
    private AnchorPane modulesTaken;

    @FXML
    private AnchorPane profile;

    @FXML
    private AnchorPane profileMainScreenplaceholder;

    @FXML
    private AnchorPane taskLists;

    @FXML
    private AnchorPane calenderPanelPlaceholder;

    @FXML
    private AnchorPane deadlinePanelPlaceholder;

    @FXML
    private AnchorPane modulesTakenBefore;

    @FXML
    private AnchorPane modulesYetTakenplaceholder;

    @FXML
    private SplitPane profileSplitPane;

    @FXML
    private SplitPane calenderSplitPane;

    @FXML
    private SplitPane profilePlaceholder;

    @FXML
    private SplitPane diarySplitPane;

    @FXML
    private SplitPane modPlanSplitPane;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab addBookTab;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
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
     * Helper method to build image to tabs
     *
     * @param imgPatch image path
     * @return
     */
    private static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch);
        ImageView imageView = new ImageView();
        //You can set width and height
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        defaultSettings();

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        diaryListPanel = new DiaryListPanel(logic.getDiaryList());
        diaryListPanelPlaceholder.getChildren().add(diaryListPanel.getRoot());
        setAnchorPaneSize(diaryListPanelPlaceholder, diaryListPanelPlaceholder.getChildren().get(0));


        //diaryEntryMainPage = new DiaryEntryMainPage();
        //diaryFullViewPlaceholder.getChildren().add(diaryEntryMainPage.getRoot());
        //setAnchorPaneSize(diaryFullViewPlaceholder, diaryFullViewPlaceholder.getChildren().get(0));

        notesListPanel = new NotesListPanel(logic.getFilesInFolderList());
        notesListPanelPlaceholder.getChildren().add(notesListPanel.getRoot());

        calenderPanel = new CalenderPanel();
        calenderPanelPlaceholder.getChildren().add(calenderPanel.getRoot());
        setAnchorPaneSize(calenderPanelPlaceholder, calenderPanelPlaceholder.getChildren().get(0));

        calenderListPanel = new CalenderListPanel(logic.getDeadlineTaskList());
        deadlinePanelPlaceholder.getChildren().add(calenderListPanel.getRoot());
        setAnchorPaneSize(deadlinePanelPlaceholder, deadlinePanelPlaceholder.getChildren().get(0));



        profileMainScreen = new ProfileMainScreen(logic.getProfile());
        profileMainScreenplaceholder.getChildren().add(profileMainScreen.getRoot());
        setAnchorPaneSize(profile, profile.getChildren().get(0));

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());


        modulesTakenListPanel = new ModulesTakenListPanel(logic.getModulesListTaken());
        modulesTakenListPanel2 = new ModulesTakenListPanel(logic.getModulesListTaken());
        modulesTakenBefore.getChildren().add(modulesTakenListPanel.getRoot());
        modulesTaken.getChildren().add(modulesTakenListPanel2.getRoot());
        setAnchorPaneSize(modulesTaken, modulesTaken.getChildren().get(0));
        setAnchorPaneSize(modulesTakenBefore, modulesTakenBefore.getChildren().get(0));

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Default Settings.
     */
    void defaultSettings() {
        profileSplitPane.setDividerPositions(0.25f, 0.75f);
        profileSplitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));
        calenderSplitPane.setDividerPositions(0.25f, 0.75f);
        calenderSplitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));

        profilePlaceholder.setDividerPositions(0.75f, 0.25f);
        profilePlaceholder.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));

        diarySplitPane.setDividerPositions(0.25f, 0.75f);
        diarySplitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));

        modPlanSplitPane.setDividerPositions(0.50f, 0.50f);
        modPlanSplitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));


    }


    void setAnchorPaneSize(AnchorPane anchorPane, Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
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

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSwitchTab()) {
                showSelectedTab(commandText);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }


    /**
     * Shows the selected tab based on the command text.
     * @param commandText the selected tab to be shown
     */
    private void showSelectedTab(String commandText) {

        // diary = 0, modplan = 1, addbook = 2, calender = 3, notes = 4, profile = 5

        String tabName = commandText.split(" ")[0];
        if (tabName.equals("notes")) {

            tabPane.getSelectionModel().select(4);

        } else if (tabName.equals("calender")) {

            tabPane.getSelectionModel().select(0);
        } else if (tabName.equals("addressbook")) {

            tabPane.getSelectionModel().select(3);
        } else if (tabName.equals("diary")) {

            tabPane.getSelectionModel().select(1);
        } else if (tabName.equals("profile")) {

            tabPane.getSelectionModel().select(5);
        } else if (tabName.equals("modplan")) {

            tabPane.getSelectionModel().select(2);
        }

    }
}
