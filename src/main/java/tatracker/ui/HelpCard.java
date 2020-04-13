//@@author potatocombat

package tatracker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import tatracker.logic.commands.CommandDetails;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class HelpCard extends UiPart<Region> {

    private static final String FXML = "HelpListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final CommandDetails details;

    @FXML
    private HBox cardPane;
    @FXML
    private Label commandWord;
    @FXML
    private Label summary;

    public HelpCard(CommandDetails details) {
        super(FXML);
        this.details = details;
        this.commandWord.setText(details.getFullCommandWord());
        this.summary.setText(details.getInfo());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCard)) {
            return false;
        }

        // state check
        HelpCard card = (HelpCard) other;
        return details.equals(card.details);
    }
}
