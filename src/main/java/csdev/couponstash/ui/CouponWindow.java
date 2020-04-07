package csdev.couponstash.ui;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.tag.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Coupon Window display panel.
 */
public class CouponWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(CouponWindow.class);
    private static final String FXML = "CouponWindow.fxml";

    public final Coupon coupon;
    private final Stage root;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label promoCode;
    @FXML
    private Label duration;
    @FXML
    private Label usage;
    @FXML
    private Label remindDate;
    @FXML
    private Label condition;
    @FXML
    private Label archived;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane tagsDup;
    @FXML
    private VBox savings;

    /**
     * Creates a new Coupon Window.
     */
    public CouponWindow(Stage root, Coupon coupon, String moneySymbol) {
        super(FXML, root);
        this.root = root;
        this.coupon = coupon;

        name.setText(coupon.getName().fullName);
        promoCode.setText(coupon.getPromoCode().toString());
        duration.setText(String.format("%s to %s", coupon.getStartDate().value, coupon.getExpiryDate().value));
        usage.setText(String.format("%s/%s", coupon.getUsage().value, coupon.getLimit().value));
        remindDate.setText(coupon.getRemindDate().toString());
        condition.setText(coupon.getCondition().value);

        //setTags(coupon, tags);
        //setTags(coupon, tagsDup); // duplicate is needed for UI purposes

        // set savings pane
        //SavingsBox savingsBox = new SavingsBox();
        //savingsBox.setSavings(coupon.getSavingsForEachUse(), moneySymbol);
        //savings.getChildren().add(savingsBox.getRoot());

        root.setTitle("Coupon Details of : " + name.getText());
        logger.info("CouponWindow created for " + name.getText() + ".");
    }

    public CouponWindow(Coupon coupon, String moneySymbol) {
        this(new Stage(), coupon, moneySymbol);
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
        CouponWindow card = (CouponWindow) other;
        return coupon.equals(card.coupon);
    }
}
