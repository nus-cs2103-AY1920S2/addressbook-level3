package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.CourseRequirement;

/**
 * Panel containing information related to Course.
 */
public class CoursePanel extends UiPart<Region> {
    private static final String FXML = "CoursePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CoursePanel.class);

    @FXML
    private Label courseName;
    @FXML
    private ListView<CourseRequirement> requirements;
    @FXML
    private ListView<CourseFocusArea> focusAreas;

    public CoursePanel(Course course) throws ParseException {
        super(FXML);

        courseName.setText(course.getCourseName().courseName);

        requirements.setItems(FXCollections.observableArrayList(course.getCourseRequirement()));
        focusAreas.setItems(FXCollections.observableArrayList(course.getCourseFocusArea()));
    }
}
