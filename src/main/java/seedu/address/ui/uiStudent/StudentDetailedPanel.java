package seedu.address.ui.uiStudent;

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
import seedu.address.ui.uiCourse.CourseVeryDetailedCard;

/**
 * Panel containing the list of students.
 */
public class StudentDetailedPanel extends UiPart<Region> {

  private static final String FXML = "StudentDetailedPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(StudentDetailedPanel.class);
  private CommandBox commandBox;

  @FXML
  private ListView<Student> studentDetailedView;

  @FXML
  private ListView<HashMap> courseListView;


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

  public StudentDetailedPanel(HashMap<String, Object> studentMap, CommandBox commandBox) {
    super(FXML);
    this.commandBox = commandBox;
    Student student = (Student) studentMap.get("details");
    ObservableList<Student> filteredStudents = FXCollections.observableArrayList();
    filteredStudents.add(student);
    studentDetailedView.setItems(filteredStudents);
    studentDetailedView.setCellFactory(listView -> new StudentListViewCell());

    courseListView.setItems((ObservableList<HashMap>) studentMap.get("courses"));
    courseListView.setCellFactory(listView -> new CourseListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code
   * CourseCard}.
   */
  class StudentListViewCell extends ListCell<Student> {

    @Override
    protected void updateItem(Student student, boolean empty) {
      super.updateItem(student, empty);

      if (empty || student == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new StudentDetailedCard(student, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
   * CourseCard}.
   */
  class CourseListViewCell extends ListCell<HashMap> {

    @Override
    protected void updateItem(HashMap courseMap, boolean empty) {
      super.updateItem(courseMap, empty);

      if (empty || courseMap == null) {
        setGraphic(null);
        setText(null);
      } else {
        Course thisCourse = (Course) courseMap.get("info");
        ObservableList<Progress> progressList = (ObservableList<Progress>) courseMap.get("progress_list");
        int noOfDoneProgress = (int) courseMap.get("number_of_done_progress");
        setGraphic(new CourseVeryDetailedCard(thisCourse, progressList, noOfDoneProgress, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

}
