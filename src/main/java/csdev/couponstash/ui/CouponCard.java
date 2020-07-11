package csdev.couponstash.ui;

import java.util.Comparator;
import java.util.Set;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.tag.Tag;
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
        promoCode.setText("Promo Code: " // If promo code is empty, use a -
                + (coupon.getPromoCode().toString().strip().length() == 0 ? "-" : coupon.getPromoCode()));
        expiryDate.setText("Expiry Date: " + coupon.getExpiryDate().value);
        startDate.setText("Start Date: " + coupon.getStartDate().value);
        usage.setText(String.format("Usage: %s/%s", coupon.getUsage().value, coupon.getLimit().toUiText()));
        setTags(coupon, tags);
        setTags(coupon, tagsDup); // duplicate is needed for UI purposes
        remindDate.setText("Remind Date: " + coupon.getRemindDate().toString());
        condition.setText("T&C: " + coupon.getCondition().value);
        // set savings pane
        SavingsBox savingsBox = new SavingsBox();
        savingsBox.setSavings(coupon.getSavingsForEachUse(), moneySymbol);
        savings.getChildren().add(savingsBox.getRoot());
        archived.setVisible(coupon.getArchived().state);
    }

    /**
     * A maximum of 5 tags will be displayed, while the total length
     * of all on screen tags will be at most 44 characters.
     * These numbers are chosen to achieve the best fit for the UI.
     * An 'and more...' tag will be created and displayed when either
     * of the two aforementioned numbers are exceeded.
     */
    public void setTags(Coupon coupon, FlowPane tagFlowPane) {
        Set<Tag> couponTags = coupon.getTags();
        final int maxTags = 5;

        Object[] tagsArr = couponTags.stream()
                .sorted(Comparator.comparing(tag -> tag.tagName.length()))
                .limit(maxTags)
                .toArray();

        int maxTotalLength = 44;
        boolean isSkipped = false;

        for (Object tag : tagsArr) {
            Tag currentTag = ((Tag) tag);
            int currentTagNameLength = currentTag.tagName.length();
            if (currentTagNameLength < maxTotalLength) {
                tagFlowPane.getChildren().add(new Label(currentTag.tagName));
                maxTotalLength -= currentTagNameLength;
            } else {
                isSkipped = true;
            }
        }

        // add ellipses to indicate existence of more off screen tags
        int initialNumberOfTags = couponTags.size();
        boolean isNumberOfTagsAboveLimit = initialNumberOfTags > maxTags;

        if (isSkipped || isNumberOfTagsAboveLimit) {
            tagFlowPane.getChildren().add(new Label("and more..."));
        }
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
