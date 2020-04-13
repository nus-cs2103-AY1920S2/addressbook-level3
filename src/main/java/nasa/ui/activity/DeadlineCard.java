package nasa.ui.activity;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import nasa.model.activity.Deadline;
import nasa.ui.UiPart;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deadline deadline;
    @FXML
    private HBox deadlinePane;
    @FXML
    private CheckBox isDone;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label note;
    @FXML
    private Label priority;
    @FXML
    private Label dateToRepeat;

    public DeadlineCard(Deadline deadline, int displayedIndex) {
        super(FXML);

        this.deadline = deadline;
        name.setText(displayedIndex + ". " + deadline.getName().toString());
        date.setText("Due by: " + deadline.getDueDate().toString());
        note.setText(deadline.getNote().toString());
        dateToRepeat.setText("");
        if (deadline.getSchedule().getType() != 0) {
            dateToRepeat.setText("Repeat: " + deadline.getSchedule().typeInString());
        }
        isDone.setSelected(deadline.isDone());
        isDone.setMouseTransparent(true);
        isDone.setOnAction(e -> {
            deadline.setDone(!deadline.isDone());
            //mainWindow.handleDeadlineClick(displayedIndex);
            if (!deadline.isOverdue()) {
                deadlinePane.getParent().setStyle("-fx-background-color: #C7CEEA;");
            } else {
                deadlinePane.getParent().setStyle("-fx-background-color: darkred;");
            }
            isDone.setSelected(deadline.isDone());
        });
        setPriority();
    }

    /**
     * Returns a Ui representation of the priority.
     */
    public void setPriority() {
        switch (deadline.getPriority().getPriorityLevel()) {
        case 1:
            priority.setText("!");
            priority.setStyle("-fx-text-fill:#00bc6b;");
            break;
        case 2:
            priority.setText("!!");
            priority.setStyle("-fx-text-fill:#85ba00;");
            break;
        case 3:
            priority.setText("!!!");
            priority.setStyle("-fx-text-fill:#d0d000;");
            break;
        case 4:
            priority.setText("!!!!");
            priority.setStyle("-fx-text-fill:#e1b400;");
            break;
        case 5:
            priority.setText("!!!!!");
            priority.setStyle("-fx-text-fill:#e80303;");
            break;
        default:
            priority.setStyle("");
            break;
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeadlineCard)) {
            return false;
        }

        // state check
        DeadlineCard card = (DeadlineCard) other;
        return name.getText().equals(card.name.getText());
    }
}
