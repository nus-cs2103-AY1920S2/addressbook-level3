package seedu.recipe.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
    private Label date;
    @FXML
    private Label id;
    @FXML
    private FlowPane goals;

    public CookedCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        name.setText(record.getName().fullName);
        name.setWrapText(true);
        date.setText("Cooked on " + record.getDate().toString());
        date.setWrapText(true);

        record.getGoals().stream()
                .sorted(Comparator.comparing(goal -> goal.goalName))
                .forEach(goal -> goals.getChildren().add(new Label(goal.goalName)));
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
