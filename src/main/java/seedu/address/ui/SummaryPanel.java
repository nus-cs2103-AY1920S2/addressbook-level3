package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class SummaryPanel extends UiPart<Region> {

    private static final String FXML = "SummaryPanel.fxml";

    @FXML
    private Label totalStudents;

    @FXML
    private Label totalStaffs;

    @FXML
    private Label totalCourses;

    @FXML
    private Label totalFinances;

    @FXML
    private Label totalAssignments;


    public SummaryPanel() {
        super(FXML);
        totalStudents.setText("Total Students: ");
        totalStaffs.setText("Total Staffs: ");
        totalCourses.setText("Total Courses: ");
        totalFinances.setText("Total Finances: ");
        totalAssignments.setText("Total Assignments: ");
    }

    public void updateTotalStudents(int num) {
        totalStudents.setText("Total Students: " + String.valueOf(num));
    }

    public void updateTotalStaffs(int num) {
        totalStaffs.setText("Total Staffs: " + String.valueOf(num));
    }

    public void updateTotalCourses(int num) {
        totalCourses.setText("Total Courses: " + String.valueOf(num));
    }

    public void updateTotalFinances(int num) {
        totalFinances.setText("Total Finances: " + String.valueOf(num));
    }

    public void updateTotalAssignments(int num) {
        totalAssignments.setText("Total Assignments: " + String.valueOf(num));
    }

}
