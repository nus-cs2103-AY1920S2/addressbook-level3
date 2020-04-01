package nasa.ui;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;


/**
 * An UI component that displays information of a {@code Module}.
 */
public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "ActivityListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;
    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label note;
    @FXML
    private Label status;
    @FXML
    private Label priority;
    @FXML
    private Group type;
    @FXML
    private Label labelForCircle;
    @FXML
    private Circle circle;



    public ActivityCard(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        name.setText(activity.getName().toString());
        date.setText(activity.getDate().toString());
        note.setText(activity.getNote().toString());
        status.setText(activity.getStatus().toString());
        priority.setText(activity.getPriority().toString());
        if (activity instanceof Deadline) {
            labelForCircle.setText("D");
            int urgent = ((Deadline) activity).percentage();
            if (urgent < 50) {
                circle.setFill(Color.GREEN);
            } else if (urgent < 80) {
                circle.setFill(Color.YELLOW);
            } else {
                circle.setFill(Color.RED);
            }
        } else if (activity instanceof Event) {
            labelForCircle.setText("E");
        } else {
            labelForCircle.setText("L");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCard)) {
            return false;
        }

        // state check
        ActivityCard card = (ActivityCard) other;
        return name.getText().equals(card.name.getText());
    }
}
