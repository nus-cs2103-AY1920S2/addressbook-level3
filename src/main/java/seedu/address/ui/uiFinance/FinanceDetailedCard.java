package seedu.address.ui.uiFinance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelFinance.Finance;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Finance}.
 */
public class FinanceDetailedCard extends UiPart<Region> {

    private static final String FXML = "FinanceListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Finance finance;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label financeType;
    @FXML
    private Label date;
    @FXML
    private Label amount;
    @FXML
    private FlowPane tags;
    @FXML
    private Label financeID;

    private CommandBox commandBox;

    public FinanceDetailedCard(Finance finance, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.finance = finance;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        name.setText(finance.getName().fullName);
        financeType.setText(finance.getFinanceType().toString());
        date.setText(finance.getDate().value);
        amount.setText(finance.getAmount().value);
        financeID.setText(finance.getId().value);
        finance.getTags().stream()
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
        if (!(other instanceof FinanceDetailedCard)) {
            return false;
        }

        // state check
        FinanceDetailedCard card = (FinanceDetailedCard) other;
        return id.getText().equals(card.id.getText())
                && finance.equals(card.finance);
    }
}
