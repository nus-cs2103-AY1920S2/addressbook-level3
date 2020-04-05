package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.calender.Task;
import seedu.address.model.nusmodule.ModuleTask;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CalenderDeadline extends UiPart<Region> {

    private static final String FXML = "CalenderDeadline.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task deadline;

    @FXML
    private HBox cardPane;
    @FXML
    private Label category;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private FlowPane modCode;


    public CalenderDeadline(Task deadline, int displayedIndex) {
        super(FXML);
        this.deadline = deadline;
        id.setText(displayedIndex + ". ");
        category.setText(deadline.getCategory());
        description.setText(deadline.getDescription());
        date.setText("Deadline: " + deadline.getDate());
        if (deadline instanceof ModuleTask) {
            setModuleTask(deadline);
        }
        setStatusColor();
//        category.setStyle("-fx-background-color: teal");

    }

    private void setModuleTask(Task deadline) {
        category.setText("School");
        modCode.getChildren().add(new Label(((ModuleTask) deadline).getModuleRelated().toString()));
        modCode.setStyle("-fx-background-color: teal");

    }

    private void setStatusColor() {

        if (deadline.getStatus()) {
            cardPane.setStyle("-fx-background-color: #323232");
        } else {
            cardPane.setStyle("-fx-background-color: #f78259");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalenderDeadline)) {
            return false;
        }
        return false;
    }
}
