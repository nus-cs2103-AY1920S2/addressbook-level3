package csdev.couponstash.ui;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.Logic;
import csdev.couponstash.model.coupon.Coupon;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * The ui for the Calendar and ResultDisplay that is displayed at the right of the application.
 */
public class CalendarResultDisplayPane extends UiPart<Region> {
    private static final String FXML = "CalendarResultDisplayPane.fxml";

    // Independent Ui parts residing in this Ui container
    private CalendarPane calendarPane;
    private ResultDisplay resultDisplay;
    private ObservableList<Coupon> coupons;
    private Logic logic;

    @FXML
    private StackPane calendarPanePlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    public CalendarResultDisplayPane(ObservableList<Coupon> coupons, Logic logic) {
        super(FXML);
        this.coupons = coupons;
        this.logic = logic;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        calendarPane = new CalendarPane(coupons, logic);
        calendarPanePlaceholder.getChildren().add(calendarPane.getRoot());
        calendarPanePlaceholder.setAlignment(Pos.TOP_RIGHT);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }

    /**
     * Sets the result display to display feedback to user.
     *
     * @param feedbackToUser Feedback to user of {@String}.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setFeedbackToUser(feedbackToUser);
    }
}
