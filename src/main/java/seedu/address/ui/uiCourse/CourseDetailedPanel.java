package seedu.address.ui.uiCourse;

import java.util.HashMap;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;
import seedu.address.ui.uiStudent.StudentVeryDetailedCard;
import seedu.address.ui.uiCourse.CourseDetailedCard;
import seedu.address.ui.uiStudent.StudentCard;

/**
 * Panel containing the list of courses.
 */
public class CourseDetailedPanel extends UiPart<Region> {

  private static final String FXML = "CourseDetailedPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(CourseDetailedPanel.class);
  private CommandBox commandBox;

  @FXML
  private ListView<Course> courseDetailedView;

  @FXML
  private ListView<HashMap> studentListView;

  //    {
//      "details": Course,
//      "students": [
//          {
//            "info": Student,
//            "progress_list": [Progress],
//            "number_of_done_progress": Integer,
//          }
//      ]
//    }

  public CourseDetailedPanel(HashMap<String, Object> courseMap, CommandBox commandBox) {
    super(FXML);
    this.commandBox = commandBox;
    Course course = (Course) courseMap.get("details");
    ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
    filteredCourses.add(course);
    courseDetailedView.setItems(filteredCourses);
    courseDetailedView.setCellFactory(listView -> new CourseListViewCell());

    studentListView.setItems((ObservableList<HashMap>) courseMap.get("students"));
    studentListView.setCellFactory(listView -> new StudentListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
   * StudentCard}.
   */
  class CourseListViewCell extends ListCell<Course> {

    @Override
    protected void updateItem(Course course, boolean empty) {
      super.updateItem(course, empty);

      if (empty || course == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new CourseDetailedCard(course, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code
   * StudentCard}.
   */
  class StudentListViewCell extends ListCell<HashMap> {

    @Override
    protected void updateItem(HashMap studentMap, boolean empty) {
      super.updateItem(studentMap, empty);

      if (empty || studentMap == null) {
        setGraphic(null);
        setText(null);
      } else {
        Student thisStudent = (Student) studentMap.get("info");
        ObservableList<Progress> progressList = (ObservableList<Progress>) studentMap.get("progress_list");
        int noOfDoneProgress = (int) studentMap.get("number_of_done_progress");
        setGraphic(new StudentVeryDetailedCard(thisStudent, progressList, noOfDoneProgress, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

}
