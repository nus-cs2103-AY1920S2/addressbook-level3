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
 * Panel containing the list of coupons.
 */
public class CouponListPanel extends UiPart<Region> {
    private static final String FXML = "CouponListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CouponListPanel.class);

    @FXML
    private ListView<Coupon> couponListView;

    public CouponListPanel(ObservableList<Coupon> couponList) {
        super(FXML);
        couponListView.setItems(couponList);
        couponListView.setCellFactory(listView -> new CouponListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Coupon} using a {@code CouponCard}.
     */
    class CouponListViewCell extends ListCell<Coupon> {
        @Override
        protected void updateItem(Coupon coupon, boolean empty) {
            super.updateItem(coupon, empty);

            if (empty || coupon == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CouponCard(coupon, getIndex() + 1).getRoot());
            }
        }
    }

}
