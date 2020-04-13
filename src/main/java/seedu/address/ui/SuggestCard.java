package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.event.Event;

/**
 * A UI component that displaces information of a {@code Group}
 */
public class SuggestCard extends UiPart<Region> {

    private static final String FXML = "SuggestListCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label suggest;
    @FXML
    private Label activity;
    @FXML
    private Label timeSpent;

    public SuggestCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        suggest.setText("Place : " + event.getPlace());
        activity.setText("Activity : " + event.getActivity());
        String text = event.getTime().toString();
        timeSpent.setText("Total Time Spent : " + text);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SuggestCard)) {
            return false;
        }

        SuggestCard card = (SuggestCard) other;
        return suggest.getText().equals(card.suggest.getText()) && event.equals(card.event);

    }
}
