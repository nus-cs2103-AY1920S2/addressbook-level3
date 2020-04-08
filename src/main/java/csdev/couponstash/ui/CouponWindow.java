package csdev.couponstash.ui;

import static csdev.couponstash.commons.util.DateUtil.DAY_SHORT_MONTH_YEAR_FORMATTER;

import java.util.Set;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Coupon Window display panel.
 */
public class CouponWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(CouponWindow.class);
    private static final String FXML = "CouponWindow.fxml";

    //For savings
    // to set certain elements to be invisible
    private static final String HIDDEN = "visibility: hidden;";
    // allow CSS styles for each label in the FlowPane
    private static final String SAVEABLE_CLASS = "sv-label";
    // make it more obvious that savings can exist without
    // numerical but with saveable free items
    private static final String NO_NUMERICAL_AMOUNT_STYLE = "-fx-font-size: 30px;"
            + "-fx-font-weight: normal; -fx-font-style: italic; -fx-text-fill: white;";
    // controls font size of number amount
    private static final int BASE_FONT_SIZE = 125;
    // if no saveables, translate numerical amount
    private static final int NUMERICAL_AMOUNT_TRANSLATE_AMOUNT = 12;

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
    private Label numericalAmount;
    @FXML
    private Label saveables;
    @FXML
    private Scene scene;

    /**
     * Creates a new Coupon Window.
     */
    public CouponWindow(Stage root, Coupon coupon, String moneySymbol) {
        super(FXML, root);
        this.root = root;
        this.coupon = coupon;

        name.setText(coupon.getName().fullName);
        promoCode.setText(coupon.getPromoCode().toString());
        duration.setText(String.format("%s to %s",
                coupon.getStartDate().date.format(DAY_SHORT_MONTH_YEAR_FORMATTER),
                coupon.getExpiryDate().date.format(DAY_SHORT_MONTH_YEAR_FORMATTER)));
        usage.setText(String.format("%s/%s", coupon.getUsage().value, coupon.getLimit().toUiText()));
        remindDate.setText(coupon.getRemindDate().toString());
        condition.setText(coupon.getCondition().value);

        setTags(coupon, tags);
        //setTags(coupon, tagsDup); // duplicate is needed for UI purposes

        // set savings pane
        //SavingsBox savingsBox = new SavingsBox();
        setSavings(coupon.getSavingsForEachUse(), moneySymbol);
        //savings.getChildren().add(savingsBox.getRoot());

        root.setTitle("Coupon Details of : " + name.getText());
        logger.info("CouponWindow created for " + name.getText() + ".");

        UiUtil.setExitAccelerator(root, scene, logger, "Coupon Window");
    }

    public CouponWindow(Coupon coupon, String moneySymbol) {
        this(new Stage(), coupon, moneySymbol);
    }

    public void setTags(Coupon coupon, FlowPane tagFlowPane) {
        Set<Tag> couponTags = coupon.getTags();
        couponTags.stream()
                .forEach(tag -> tagFlowPane.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Sets the Savings to be displayed.
     *
     * @param s           The Savings to be displayed.
     * @param moneySymbol Money symbol for the display.
     */
    public void setSavings(Savings s, String moneySymbol) {
        // handle numerical value
        String savingsNumber = getSavingsString(s, moneySymbol);
        if (savingsNumber.isBlank()) {
            this.numericalAmount.setText("(no amount)");
            this.numericalAmount.setStyle(NO_NUMERICAL_AMOUNT_STYLE);
        } else {
            this.numericalAmount.setText(savingsNumber);
        }
    }

    /**
     * Given a Savings object and the money symbol, return a String containing a formatted numerical value for use in
     * SavingsBox, or an empty String if Savings does not have any MonetaryAmount or PercentageAmount(only Saveable).
     *
     * @param s           The Savings object to access.
     * @param moneySymbol The money symbol set in UserPrefs.
     * @return Nicely formatted String of the numerical savings.
     */
    private static String getSavingsString(Savings s, String moneySymbol) {
        // assumes that Savings only has either PercentageAmount
        // or MonetaryAmount, but never both
        StringBuilder sb = new StringBuilder();
        s.getPercentageAmount().ifPresent(pc ->
                sb.append(pc.toString()));
        s.getMonetaryAmount().ifPresent(ma ->
                sb.append(ma.getStringWithMoneySymbol(moneySymbol)));
        return sb.toString();
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
