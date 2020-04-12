package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelProgress.Progress;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class ProgressCard extends UiPart<Region> {

    private static final String FXML = "ProgressListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Progress progress;

    @FXML
    private HBox cardPane;
    @FXML
    private Label sid;
    @FXML
    private Label aid;
    @FXML
    private Label isDone;

    private CommandBox commandBox;

    public ProgressCard(Progress progress, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.progress = progress;
        this.commandBox = commandBox;
        sid.setText(progress.getId().getStudentID().value);
        aid.setText(progress.getId().getAssignmentID().value);
        if (progress.getIsDone()) {
            isDone.setText("Done");
        } else {
            isDone.setText("Not Done");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProgressCard)) {
            return false;
        }

        // state check
        ProgressCard card = (ProgressCard) other;
        return sid.getText().equals(card.sid.getText())
                && aid.getText().equals(card.aid.getText())
                && progress.equals(card.progress);
    }
}
