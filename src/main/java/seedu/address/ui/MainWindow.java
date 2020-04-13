package seedu.address.ui;

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
import seedu.address.ui.uiAssignments.AssignmentDetailedPanel;
import seedu.address.ui.uiAssignments.AssignmentListPanel;
import seedu.address.ui.uiCourse.CourseDetailedPanel;
import seedu.address.ui.uiCourse.CourseListPanel;
import seedu.address.ui.uiFinance.FinanceDetailedPanel;
import seedu.address.ui.uiFinance.FinanceListPanel;
import seedu.address.ui.uiStaff.StaffDetailedPanel;
import seedu.address.ui.uiStaff.StaffListPanel;
import seedu.address.ui.uiStudent.StudentDetailedPanel;
import seedu.address.ui.uiStudent.StudentListPanel;

import java.util.logging.Logger;

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
    private StaffListPanel staffListPanel;
    private CourseListPanel courseListPanel;
    private FinanceListPanel financeListPanel;
    private AssignmentListPanel assignmentListPanel;
    private SummaryPanel summaryPanel;

    private StudentDetailedPanel studentDetailedPanel;
    private StaffDetailedPanel staffDetailedPanel;
    private CourseDetailedPanel courseDetailedPanel;
    private FinanceDetailedPanel financeDetailedPanel;
    private AssignmentDetailedPanel assignmentDetailedPanel;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private FadeTransition ftListPanelLeft;
    private FadeTransition ftListPanelRight;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane summaryPanelPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane dataListPanelPlaceholder;

    @FXML
    private StackPane extraListPanelPlaceholder;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane staffListPanelPlaceholder;

    @FXML
    private StackPane courseListPanelPlaceholder;

    @FXML
    private StackPane financeListPanelPlaceholder;

    @FXML
    private StackPane assignmentListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    private double originalExtraPanelWidth;
    private double originalExtraPanelHeight;

    private String currentView;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        logic.setMainWindow(this);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        currentView = "STUDENT";
    }

    public String getCurrentView() {
        return currentView;
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
    void fillInnerParts() throws CommandException {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, this);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        studentListPanel = new StudentListPanel(logic.getFilteredStudentList(), commandBox);
        staffListPanel = new StaffListPanel(logic.getFilteredStaffList(), commandBox);
        courseListPanel = new CourseListPanel(logic.getFilteredCourseList(), commandBox);
        financeListPanel = new FinanceListPanel(logic.getFilteredFinanceList(), commandBox);
        assignmentListPanel = new AssignmentListPanel(logic.getFilteredAssignmentList(), commandBox);

        summaryPanel = new SummaryPanel();

        //    {
//      "details": Student,
//      "courses": [
//          {
//            "info": Course,
//            "progress_list": [Progress],
//            "number_of_done_progress": Integer,
//          }
//      ]
//    }

        studentDetailedPanel = new StudentDetailedPanel(logic.getFilteredStudentDetailsMap(), commandBox);
        staffDetailedPanel = new StaffDetailedPanel(logic.getFilteredStaffDetailsMap(), commandBox);
        courseDetailedPanel = new CourseDetailedPanel(logic.getFilteredCourseDetailsMap(), commandBox);
        financeDetailedPanel = new FinanceDetailedPanel(logic.getFilteredFinanceDetailsMap(), commandBox);
        assignmentDetailedPanel = new AssignmentDetailedPanel(logic.getFilteredAssignmentDetailsMap(), commandBox);

//    Set<ID> assignedCourses = new HashSet<ID>();
//    assignedCourses.add(new ID("11"));
//    assignedCourses.add(new ID("12"));
//    Set<Tag> assignedTags = new HashSet<Tag>();
//    assignedTags.add(new Tag("Cool"));
//    assignedTags.add(new Tag("CS"));
//    Student fakeStudent = new Student(new Name("Tommy"), new ID("9999999999"), assignedCourses, assignedTags);
//    fakeStudent.processAssignedCourses((FilteredList<Course>) logic.getFilteredCourseList());
//    studentDetailsMap.put("details", fakeStudent);
//    ObservableList<Course> filteredCourses = logic.getFilteredCourseList();
//    ObservableList<HashMap> courseMap = FXCollections.observableArrayList();
//    for (Course course : filteredCourses) {
//      HashMap<String, Object> m = new HashMap<>();
//      ObservableList<Progress> progressList = FXCollections.observableArrayList();
//      m.put("info", course);
//      m.put("progress_list", progressList);
//      m.put("number_of_done_progress", 3);
//      courseMap.add(m);
//    }
//    studentDetailsMap.put("courses", courseMap);
//    studentDetailedPanel = new StudentDetailedPanel(studentDetailsMap, commandBox);

        dataListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());
        extraListPanelPlaceholder.getChildren().add(studentDetailedPanel.getRoot());
        //summaryPanelPlaceholder.getChildren().add(summaryPanel.getRoot());
        summaryPanel.updateTotalStudents(logic.getFilteredStudentList().size());
        summaryPanel.updateTotalStaffs(logic.getFilteredStaffList().size());
        summaryPanel.updateTotalCourses(logic.getFilteredCourseList().size());
        summaryPanel.updateTotalFinances(logic.getFilteredFinanceList().size());
        summaryPanel.updateTotalAssignments(logic.getFilteredAssignmentList().size());

        logic.setSummaryPanel(summaryPanel);

        ftListPanelLeft = getFadeTransition(Duration.millis(150), dataListPanelPlaceholder);
        ftListPanelRight = getFadeTransition(Duration.millis(150), extraListPanelPlaceholder);

        originalExtraPanelWidth = extraListPanelPlaceholder.getMaxWidth();
        originalExtraPanelHeight = extraListPanelPlaceholder.getMaxHeight();
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


    public void switchToView(String view) {
        switch (view) {
            case "SUMMARY":
                callSwitchToSummary();
                break;
            case "STUDENT":
                callSwitchToStudent();
                break;
            case "STAFF":
                callSwitchToStaff();
                break;
            case "COURSE":
                callSwitchToCourse();
                break;
            case "FINANCE":
                callSwitchToFinance();
                break;
            case "ASSIGNMENT":
                callSwitchToAssignment();
                break;
        }
    }

    /**
     * Called from switch student command
     */
    public void callSwitchToStudent() {
        handleSwitchToStudent();
    }

    /**
     * Called from switch staff command
     */
    public void callSwitchToStaff() {
        handleSwitchToStaff();
    }

    /**
     * Called from switch course command
     */
    public void callSwitchToCourse() {
        handleSwitchToCourse();
    }

    /**
     * Called from switch finance command
     */
    public void callSwitchToFinance() {
        handleSwitchToFinance();
    }

    /**
     * Called from switch assignment command
     */
    public void callSwitchToAssignment() {
        handleSwitchToAssignment();
    }

    /**
     * Called from switch summary command
     */
    public void callSwitchToSummary() {
        handleSwitchToSummary();
    }

    /**
     * Switch to the Person view.
     */
    @FXML
    private void handleSwitchToStudent() {
        //Enable extra List
        extraListPanelPlaceholder.setMaxSize(originalExtraPanelWidth, originalExtraPanelHeight);
        extraListPanelPlaceholder.setVisible(true);
        currentView = "STUDENT";
        switchList(studentListPanel.getRoot(), studentDetailedPanel.getRoot());
    }

    /**
     * Switch to the Staff view.
     */
    @FXML
    private void handleSwitchToStaff() {
        //Enable extra List
        extraListPanelPlaceholder.setMaxSize(originalExtraPanelWidth, originalExtraPanelHeight);
        extraListPanelPlaceholder.setVisible(true);
        currentView = "STAFF";
        switchList(staffListPanel.getRoot(), staffDetailedPanel.getRoot());
    }

    /**
     * Switch to the Course view.
     */
    @FXML
    private void handleSwitchToCourse() {
        //Enable extra List
        extraListPanelPlaceholder.setMaxSize(originalExtraPanelWidth, originalExtraPanelHeight);
        extraListPanelPlaceholder.setVisible(true);
        currentView = "COURSE";
        switchList(courseListPanel.getRoot(), courseDetailedPanel.getRoot());
    }

    /**
     * Switch to the Finance view.
     */
    @FXML
    private void handleSwitchToFinance() {
        //Disable extra List
        extraListPanelPlaceholder.setMaxSize(originalExtraPanelWidth, originalExtraPanelHeight);
        extraListPanelPlaceholder.setVisible(true);
        currentView = "FINANCE";
        switchList(financeListPanel.getRoot(), financeDetailedPanel.getRoot());
    }

    /**
     * Switch to the Assignment view.
     */
    @FXML
    private void handleSwitchToAssignment() {
        //Disable extra List
        extraListPanelPlaceholder.setMaxSize(originalExtraPanelWidth, originalExtraPanelHeight);
        extraListPanelPlaceholder.setVisible(true);
        currentView = "ASSIGNMENT";
        switchList(assignmentListPanel.getRoot(), assignmentDetailedPanel.getRoot());
    }

    /**
     * Switch to the Summary view.
     */
    @FXML
    private void handleSwitchToSummary() {
        //Disable extra List
        extraListPanelPlaceholder.setMaxSize(0.0, 0.0);
        extraListPanelPlaceholder.setVisible(false);
        currentView = "SUMMARY";
        switchList(summaryPanel.getRoot(), courseListPanel.getRoot());
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
    private void switchList(Region leftRegion, Region rightRegion) {
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(leftRegion);

        extraListPanelPlaceholder.getChildren().clear();
        extraListPanelPlaceholder.getChildren().add(rightRegion);
        ftListPanelLeft.play();
        ftListPanelRight.play();
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
