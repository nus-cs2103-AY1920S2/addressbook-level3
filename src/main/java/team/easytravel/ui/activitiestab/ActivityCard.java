package team.easytravel.ui.activitiestab;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code Activity}.
 */
public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "activitiestab/ActivityCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Activity activity;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML

    private Label duration;
    @FXML
    private Label title;
    @FXML
    private Label scheduledTime;
    @FXML
    private Label activityLocation;
    @FXML
    private FlowPane tags;

    public ActivityCard(Activity activity, int displayedIndex) {
        super(FXML);
        this.activity = activity;
        id.setText(displayedIndex + ". ");
        title.setText(activity.getTitle().toString());
        if (activity.getScheduledDateTime().isPresent()) {
            scheduledTime.setText("Scheduled timing: " + activity.getScheduledDateTime().get());
        } else {
            scheduledTime.setText("Not scheduled");
        }
        duration.setText("Duration: " + activity.getDuration().toString() + " hours");
        activityLocation.setText("Location: " + activity.getLocation().toString());
        activity.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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

        // state check of activity
        ActivityCard card = (ActivityCard) other;
        return id.getText().equals(card.id.getText())
                && activity.equals(card.activity);
    }
}
