package fithelper.ui.calendar;

import java.time.LocalDateTime;

import fithelper.model.calculator.CalorieCalculator;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class DailyStatus extends UiPart<AnchorPane> {

    private static final String FXML = "DailyStatusCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private CalorieCalculator stats;
    private ObservableList<Entry> entries;
    private LocalDateTime date;

    @FXML
    private AnchorPane innerPane;
    @FXML
    private Text dateTitle;
    @FXML
    private Text sportsCal;
    @FXML
    private Text foodCal;
    @FXML
    private Text totalCal;

    /**
     * Creates a card displaying the {@code sport entry}.
     */
    public DailyStatus(ObservableList<Entry> entriesToSet, LocalDateTime dateToSet) {
        super(FXML);
        this.entries = entriesToSet;
        this.date = dateToSet;
        this.stats = new CalorieCalculator(entries);
        fillInDetails();
    }

    /**
     * Fills in details in GUI, in list view.
     */
    private void fillInDetails() {
        getGenerator();
        dateTitle.setText(date.toLocalDate().toString());
        foodCal.setText("Food: " + String.valueOf(stats.getFoodCalorie()));
        sportsCal.setText("Sports: " + String.valueOf(stats.getSportsCalorie()));
        totalCal.setText("Total: " + String.valueOf(stats.getTotalCalorie()));
        foodCal.setFill(Color.RED);
        sportsCal.setFill(Color.GREEN);
        totalCal.setFill(Color.valueOf("#789cce"));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DailyStatus)) {
            return false;
        }

        // state check
        DailyStatus card = (DailyStatus) other;
        return entries.equals(card.entries);
    }

    public void getGenerator() {
        stats = new CalorieCalculator(entries);
    }
}
