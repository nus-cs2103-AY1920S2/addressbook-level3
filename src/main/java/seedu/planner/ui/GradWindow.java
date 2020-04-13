package seedu.planner.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.planner.commons.GraduationRequirementUtil;
import seedu.planner.model.ReadOnlyPlanner;
import seedu.planner.model.student.Student;


/**
 * The UI component that is responsible for updating graduation requirements.
 */
public class GradWindow extends UiPart<Region> {

    private static final String FXML = "GradView.fxml";

    @FXML
    private HBox gradPane;

    @FXML
    private Label graduationStatus;

    @FXML
    private Label info;

    @FXML
    private ScrollPane scrollPaneGraduation;

    public GradWindow(ReadOnlyPlanner planner) {
        super(FXML);
        setGrad(planner);
    }

    public void setGrad(ReadOnlyPlanner planner) {
        if (!planner.hasActiveStudent()) {
            graduationStatus.setText("No Active Student Detected");
        } else {
            Student activeStudent = planner.getActiveStudent();
            graduationStatus.setStyle("-fx-font: 18 arial; -fx-text-fill: white;");
            graduationStatus.setText("Graduation Requirement for " + activeStudent.getName());
            info.setStyle("-fx-font: 12 arial; -fx-text-fill: white;");
            info.setText(GraduationRequirementUtil.getString(activeStudent));
        }
    }

    /**
     * Overrides Equal function to compare objects.
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradWindow)) {
            return false;
        }
        return false;
    }

    /**
     * Set the styles for light theme/dark theme.
     * @param type
     */
    public void changeColor(int type) {
        if (type == 0) {
            scrollPaneGraduation.setStyle("-fx-background: #383838");
        } else {
            scrollPaneGraduation.setStyle("-fx-background: #606060");
        }

    }
}
