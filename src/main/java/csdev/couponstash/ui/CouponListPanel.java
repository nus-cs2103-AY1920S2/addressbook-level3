package csdev.couponstash.ui;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;
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

    @FXML
    private ListView<Coupon> couponListView;

    /**
     * Constructor for a CouponListPanel.
     *
     * @param couponList  The ObservableList of Coupons to be shown.
     * @param moneySymbol The money symbol for the Savings of the Coupons.
     */
    public CouponListPanel(ObservableList<Coupon> couponList, MoneySymbol moneySymbol) {
        super(FXML);
        couponListView.setItems(couponList);
        couponListView.setCellFactory(listView -> new CouponListViewCell(moneySymbol));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Coupon} using a {@code CouponCard}.
     */
    class CouponListViewCell extends ListCell<Coupon> {
        private final MoneySymbol moneySymbol;

        /**
         * Constructor for a new CouponListViewCell.
         *
         * @param moneySymbol The MoneySymbol representing the
         *                    money symbol to be displayed
         *                    on the Coupon's Savings.
         */
        public CouponListViewCell(MoneySymbol moneySymbol) {
            this.moneySymbol = moneySymbol;
        }

        @Override
        protected void updateItem(Coupon coupon, boolean empty) {
            super.updateItem(coupon, empty);

            if (empty || coupon == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CouponCard(coupon, getIndex() + 1, this.moneySymbol.getString()).getRoot());
            }
        }
    }
}
