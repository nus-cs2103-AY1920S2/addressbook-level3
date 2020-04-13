package seedu.recipe.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.logic.Logic;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.GoalCount;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.plan.GroceryListWindow;
import seedu.recipe.ui.plan.PlanningListPanel;
import seedu.recipe.ui.tab.Tab;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private ObservableList<GoalCount> goalsCountList;

    // Independent Ui parts residing in this Ui container
    private MainTabPanel mainTabPanel;
    private PlanningListPanel planningListPanel;
    private RecipeListPanel recipeListPanel;
    private CookedListPanel cookedListPanel;
    private AchievementCard achievementsListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private GroceryListWindow groceryListWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mainTabPanelPlaceholder;

    @FXML
    private StackPane planningListPanelPlaceholder;

    @FXML
    private StackPane recipeListPanelPlaceholder;

    @FXML
    private StackPane cookedListPanelPlaceholder;

    @FXML
    private StackPane achievementsListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

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

        String groceries = "hi";
        groceryListWindow = new GroceryListWindow(groceries);
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
    void fillInnerParts() throws IOException {

        ObservableList<Recipe> recipeList = logic.getFilteredRecipeList();
        recipeListPanel = new RecipeListPanel(recipeList);

        ObservableList<Plan> plannedList = logic.getFilteredPlannedList();
        planningListPanel = new PlanningListPanel(plannedList);

        ObservableList<Record> cookedList = logic.getFilteredRecordList();
        goalsCountList = logic.getFilteredGoalsTally();
        cookedListPanel = new CookedListPanel(cookedList, goalsCountList);

        ObservableList<Quote> quoteList = logic.getFilteredQuoteList();
        achievementsListPanel = new AchievementCard(quoteList, cookedList);

        mainTabPanel = new MainTabPanel(recipeListPanel, planningListPanel, cookedListPanel, achievementsListPanel);
        mainTabPanelPlaceholder.getChildren().add(mainTabPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getRecipeBookFilePath());
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
     * Opens the grocery list window
     */
    @FXML
    public void handleGroceryList() {
        groceryListWindow.setGroceryListMessage(logic.getGroceryList());
        if (!groceryListWindow.isShowing()) {
            groceryListWindow.show();
        } else {
            groceryListWindow.focus();
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
        groceryListWindow.hide();
        primaryStage.hide();
    }

    public RecipeListPanel getRecipeListPanel() {
        return recipeListPanel;
    }

    /**
     * Switches of tab depending on {@code tab}.
     */
    @FXML
    private void handleSwitchTab(Tab tab) {
        switch (tab) {
        case RECIPES:
            showRecipesTab();
            break;
        case PLANNING:
            showPlanningTab();
            break;
        case GOALS:
            showGoalsTab();
            break;
        case ACHIEVEMENTS:
            showAchievementsTab();
            break;
        default:
            break; //todo throw exception
        }
    }


    /**
     * Executes the command and returns the result.
     *
     * @see seedu.recipe.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowGroceryList()) {
                handleGroceryList();
            }

            if (commandResult.isSwitchTab()) {
                handleSwitchTab(commandResult.getTab());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Switch to recipes tab.
     */
    private void showRecipesTab() {
        mainTabPanel.switchToRecipesTab();
    }

    /**
     * Switch to planning tab.
     */
    private void showPlanningTab() {
        mainTabPanel.switchToPlanningTab();
    }

    /**
     * Switch to goals tab.
     */
    private void showGoalsTab() {
        mainTabPanel.switchToGoalsTab();
        cookedListPanel.setChart(goalsCountList);
    }

    /**
     * Switch to achievements tab.
     */
    private void showAchievementsTab() {
        mainTabPanel.switchToAchievementsTab();
    }
}
