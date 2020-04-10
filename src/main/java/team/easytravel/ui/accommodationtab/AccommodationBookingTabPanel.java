package team.easytravel.ui.accommodationtab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.ui.TabPanel;

/**
 * Panel containing the list of accommodations.
 */
public class AccommodationBookingTabPanel extends TabPanel {
    public static final String TAB_NAME = "Accommodation Tab";

    private static final String FXML = "accommodationtab/AccommodationBookingTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AccommodationBookingTabPanel.class);

    @FXML
    private ListView<AccommodationBooking> accommodationBookingListView;

    public AccommodationBookingTabPanel(ObservableList<AccommodationBooking> accommodationBookingList) {
        super(FXML);
        accommodationBookingListView.setItems(accommodationBookingList);
        accommodationBookingListView.setCellFactory(listView -> new AccommodationBookingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Accommodation} using a
     * {@code AccommodationListCard}.
     */
    class AccommodationBookingListViewCell extends ListCell<AccommodationBooking> {
        @Override
        protected void updateItem(AccommodationBooking accommodationBooking, boolean empty) {
            super.updateItem(accommodationBooking, empty);
            if (empty || accommodationBooking == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AccommodationBookingCard(accommodationBooking, getIndex() + 1).getRoot());
            }
        }
    }


}
