package csdev.couponstash.ui;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.Logic;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.tag.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * Coupon details display panel.
 */
public class CouponDetails extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(CouponDetails.class);
    private static final String FXML = "CouponDetails.fxml";

    public final Coupon coupon;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
//    @FXML
//    private Label id;
//    @FXML
//    private Label idDup;
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
     * Creates a new Coupon Details.
     */
    public CouponDetails(Logic logic, Coupon coupon) {
        super(FXML);
        this.coupon = coupon;
        logger.info("CouponDetails created.");

//        setId(id, displayedIndex);
//        setId(idDup, displayedIndex); // duplicate is needed for UI purposes
        name.setText(coupon.getName().fullName);
        promoCode.setText("Promo Code: " + coupon.getPromoCode());
        expiryDate.setText("Expiry Date: " + coupon.getExpiryDate().value);
        startDate.setText("Start Date: " + coupon.getStartDate().value);
        usage.setText(String.format("Usage: %s/%s", coupon.getUsage().value, coupon.getLimit().value));
        setTags(coupon, tags);
        setTags(coupon, tagsDup); // duplicate is needed for UI purposes
        remindDate.setText("Remind Date: " + coupon.getRemindDate().toString());
        condition.setText("T&C: " + coupon.getCondition().value);

        // set savings pane
        SavingsBox savingsBox = new SavingsBox();
        savingsBox.setSavings(coupon.getSavingsForEachUse(), logic.getStashSettings().getMoneySymbol().toString());
        savings.getChildren().add(savingsBox.getRoot());
        archived.setVisible(coupon.getArchived().state);
    }

    public void setTags(Coupon coupon, FlowPane tagFlowPane) {
        Set<Tag> couponTags = coupon.getTags();
        final int maxTags = 5;

        Object[] tagsArr = couponTags.stream()
                .sorted(Comparator.comparing(tag -> tag.tagName.length()))
                .limit(maxTags)
                .toArray();

        int maxTotalLength = 44;
        boolean isSkiped = false;

        for (Object tag : tagsArr) {
            Tag currentTag = ((Tag) tag);
            int currentTagNameLength = currentTag.tagName.length();
            if (currentTagNameLength < maxTotalLength) {
                tagFlowPane.getChildren().add(new Label(currentTag.tagName));
                maxTotalLength -= currentTagNameLength;
            } else {
                isSkiped = true;
            }
        }

        // add ellipses to indicate existence of more off screen tags
        int initialNumberOfTags = couponTags.size();
        boolean isNumberOfTagsAboveLimit = initialNumberOfTags > maxTags;

        if (isSkiped || isNumberOfTagsAboveLimit) {
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
        CouponDetails card = (CouponDetails) other;
        return id.getText().equals(card.id.getText())
                && coupon.equals(card.coupon);
    }
}
