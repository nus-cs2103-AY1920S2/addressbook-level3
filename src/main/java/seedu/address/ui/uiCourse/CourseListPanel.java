package seedu.address.ui.uiCourse;

import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelCourse.Course;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of courses.
 */
public class CourseListPanel extends UiPart<Region> {

  private static final String FXML = "CourseListPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(CourseListPanel.class);

  @FXML
  private ListView<Course> courseListView;

  public CourseListPanel(ObservableList<Course> courseList) {
    super(FXML);
    courseListView.setItems(courseList);
    courseListView.setCellFactory(listView -> new CourseListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
   * CourseCard}.
   */
  class CourseListViewCell extends ListCell<Course> {

    @Override
    protected void updateItem(Course course, boolean empty) {
      super.updateItem(course, empty);

      if (empty || course == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new CourseCard(course, getIndex() + 1).getRoot());
      }
    }
  }

}
