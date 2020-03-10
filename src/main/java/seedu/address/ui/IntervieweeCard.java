package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.hirelah.Interviewee;

/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class IntervieweeCard extends UiPart<Region> {

    private static final String FXML = "IntervieweeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interviewee interviewee;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label alias;
    @FXML
    private Label done;

    public IntervieweeCard(Interviewee interviewee) {
        super(FXML);
        this.interviewee = interviewee;
        name.setText(interviewee.getFullName());
        id.setText(String.valueOf(interviewee.getId()));
        alias.setText(interviewee.getAlias().orElse("No alias has been set."));
        // done should be a image? or just tick/cross? status check for interviewee
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IntervieweeCard)) {
            return false;
        }

        // state check
        IntervieweeCard card = (IntervieweeCard) other;
        return id.getText().equals(card.id.getText())
                && interviewee.equals(card.interviewee);
    }
}
