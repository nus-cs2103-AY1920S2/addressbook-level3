package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * An UI component that displays information of a {@code Profile}.
 */
public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deadline deadline;

    @FXML
    private HBox cardPane;
    @FXML
    private Label module;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label time;


    public DeadlineCard(Deadline deadline) {
        super(FXML);
        this.deadline = deadline;
        module.setText(deadline.getModuleCode());
        description.setText("Task: " + deadline.getDescription());
        if (deadline.getDate() == null) {
            date.setText("Date: -");
            time.setText("Time: -");
        } else {
            date.setText("Date: " + deadline.getStringDate());
            time.setText("Time: " + deadline.getStringTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCard)) {
            System.out.print("");
            return false;
        }

        // state check
        DeadlineCard card = (DeadlineCard) other;
        return deadline.equals(card.deadline);
    }
}
