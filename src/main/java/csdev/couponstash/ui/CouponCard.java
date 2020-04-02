package csdev.couponstash.ui;

import java.util.Comparator;

import csdev.couponstash.model.coupon.Coupon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Coupon}.
 */
public class CouponCard extends UiPart<Region> {

    private static final String FXML = "CouponCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CouponStash level 4</a>
     */

    public final Coupon coupon;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label idDup;
    @FXML
    private Label promoCode;
    @FXML
    private VBox savings;
    @FXML
    private Label expiryDate;
    @FXML
    private Label startDate;
    @FXML
    private Label usage;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane tagsDup;
    @FXML
    private Label remindDate;
    @FXML
    private Label condition;
    @FXML
    private Label archived;

    /**
     * Constructor for a new CouponCard to be shown
     * in the JavaFX GUI.
     *
     * @param coupon         The Coupon that is shown in
     *                       this CouponCard.
     * @param displayedIndex The index of this Coupon
     *                       in the displayed list.
     * @param moneySymbol    String representing the money
     *                       symbol to be used for savings
     *                       (saved in UserPrefs).
     */
    public CouponCard(Coupon coupon, int displayedIndex, String moneySymbol) {
        super(FXML);
        this.coupon = coupon;
        setId(id, displayedIndex);
        setId(idDup, displayedIndex); // duplicate is needed for UI purposes
        name.setText(coupon.getName().fullName);
        promoCode.setText("Promo Code: " + coupon.getPromoCode());
        expiryDate.setText("Expiry Date: " + coupon.getExpiryDate().value);
        startDate.setText("Start Date: " + coupon.getStartDate().value);
        usage.setText(String.format("Usage: %s/%s", coupon.getUsage().value, coupon.getLimit().value));
        setTags(coupon, tags);
        setTags(coupon, tagsDup);
        remindDate.setText("Remind Date: " + coupon.getRemindDate().toString());
        condition.setText("T&C: " + coupon.getCondition().value);
        // set savings pane
        SavingsPane savingsPane = new SavingsPane();
        savingsPane.setSavings(coupon.getSavingsForEachUse(), moneySymbol);
        savings.getChildren().add(savingsPane.getRoot());
        archived.setVisible(Boolean.parseBoolean(coupon.getArchived().value));
    }

    public void setTags(Coupon coupon, FlowPane tagFlowPane) {
        coupon.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .limit(5)
                .forEach(tag -> tagFlowPane.getChildren().add(new Label(tag.tagName)));
    }

    public void setId(Label idLabel, int index) {
        idLabel.setText(index + "");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CouponCard)) {
            return false;
        }

        // state check
        CouponCard card = (CouponCard) other;
        return id.getText().equals(card.id.getText())
                && coupon.equals(card.coupon);
    }
}
