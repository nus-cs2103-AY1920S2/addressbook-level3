package csdev.couponstash.ui;

import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Coupon> personListView;

    public PersonListPanel(ObservableList<Coupon> couponList) {
        super(FXML);
        personListView.setItems(couponList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Coupon} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Coupon> {
        @Override
        protected void updateItem(Coupon coupon, boolean empty) {
            super.updateItem(coupon, empty);

            if (empty || coupon == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(coupon, getIndex() + 1).getRoot());
            }
        }
    }

}
