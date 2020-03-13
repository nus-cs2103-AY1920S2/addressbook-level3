package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
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
  private Label totalTeachers;

  @FXML
  private Label totalCourses;

  @FXML
  private Label totalFinances;

  public SummaryPanel() {
    super(FXML);
    totalStudents.setText("Total Students: ");
    totalTeachers.setText("Total Teachers: ");
    totalCourses.setText("Total Courses: ");
    totalFinances.setText("Total Finances: ");
  }

  public void updateTotalStudents(int num){
    totalStudents.setText("Total Students: " + String.valueOf(num));
  }

  public void updateTotalTeachers(int num){
    totalTeachers.setText("Total Teachers: " + String.valueOf(num));
  }

  public void updateTotalCourses(int num){
    totalCourses.setText("Total Courses: " + String.valueOf(num));
  }

  public void updateTotalFinances(int num){
    totalFinances.setText("Total Finances: " + String.valueOf(num));
  }

}
