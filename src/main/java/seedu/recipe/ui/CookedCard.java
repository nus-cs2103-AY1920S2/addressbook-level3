package seedu.recipe.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.recipe.model.cooked.Record;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class CookedCard extends UiPart<Region> {

    private static final String FXML = "CookedListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Record record;
    private final String styleIngredientsAndSteps = "-fx-font-size: 11pt;\n"
            + "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public CookedCard(Record record, int displayedIndex) throws IOException {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        name.setText(record.getName().fullName);
        name.setWrapText(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CookedCard)) {
            return false;
        }

        // state check
        CookedCard card = (CookedCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
