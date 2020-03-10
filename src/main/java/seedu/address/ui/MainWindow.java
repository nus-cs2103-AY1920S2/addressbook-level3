package seedu.address.ui;

import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.uiCourse.CourseListPanel;
import seedu.address.ui.uiFinance.FinanceListPanel;
import seedu.address.ui.uiStudent.StudentListPanel;
import seedu.address.ui.uiTeacher.TeacherListPanel;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where
 * other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

  private static final String FXML = "MainWindow.fxml";

  private final Logger logger = LogsCenter.getLogger(getClass());

  private Stage primaryStage;
  private Logic logic;

  // Independent Ui parts residing in this Ui container
  private StudentListPanel studentListPanel;
  private TeacherListPanel teacherListPanel;
  private CourseListPanel courseListPanel;
  private FinanceListPanel financeListPanel;
  private ResultDisplay resultDisplay;
  private HelpWindow helpWindow;

  private FadeTransition ftListPanel;

  @FXML
  private StackPane commandBoxPlaceholder;

  @FXML
  private MenuItem helpMenuItem;

  @FXML
  private StackPane dataListPanelPlaceholder;

  @FXML
  private StackPane studentListPanelPlaceholder;

  @FXML
  private StackPane teacherListPanelPlaceholder;

  @FXML
  private StackPane courseListPanelPlaceholder;

  @FXML
  private StackPane financeListPanelPlaceholder;

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
    resultDisplay = new ResultDisplay();
    resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

    StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
    statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

    CommandBox commandBox = new CommandBox(this::executeCommand);
    commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

    studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
    teacherListPanel = new TeacherListPanel(logic.getFilteredTeacherList());
    courseListPanel = new CourseListPanel(logic.getFilteredCourseList());
    financeListPanel = new FinanceListPanel(logic.getFilteredFinanceList());

    dataListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

    ftListPanel = getFadeTransition(Duration.millis(150), dataListPanelPlaceholder);
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
   * Switch to the Person view.
   */
  @FXML
  private void handleSwitchToStudent() {
    switchList(studentListPanel.getRoot());
  }

  /**
   * Switch to the Teacher view.
   */
  @FXML
  private void handleSwitchToTeacher() {
    switchList(teacherListPanel.getRoot());
  }

  /**
   * Switch to the Course view.
   */
  @FXML
  private void handleSwitchToCourse() {
    switchList(courseListPanel.getRoot());
  }

  /**
   * Switch to the Finance view.
   */
  @FXML
  private void handleSwitchToFinance() {
    switchList(financeListPanel.getRoot());
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
   * Create the fade transition for the StackPane and set value from 0 to 1.
   */
  private FadeTransition getFadeTransition(Duration duration, StackPane placeholder) {
    FadeTransition ft = new FadeTransition(duration, placeholder);
    ft.setFromValue(0);
    ft.setToValue(1);
    return ft;
  }

  /**
   * Switch the list panel to the given region
   */
  private void switchList(Region region) {
    dataListPanelPlaceholder.getChildren().clear();
    dataListPanelPlaceholder.getChildren().add(region);
    ftListPanel.play();
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

      return commandResult;
    } catch (CommandException | ParseException e) {
      logger.info("Invalid command: " + commandText);
      resultDisplay.setFeedbackToUser(e.getMessage());
      throw e;
    }
  }
}
