package csdev.couponstash.ui;

import static csdev.couponstash.commons.util.DateUtil.DAY_SHORT_MONTH_YEAR_FORMATTER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Coupon Window display panel.
 */
public class CouponWindow extends UiPart<Stage> {
    // to set certain elements to be invisible
    private static final String HIDDEN = "visibility: hidden;";
    private static final Logger logger = LogsCenter.getLogger(CouponWindow.class);
    private static final String FXML = "CouponWindow.fxml";
    // style for message displayed if no numerical savings for coupon
    private static final String NO_NUMERICAL_AMOUNT_STYLE = "-fx-font-size: 30px;"
            + "-fx-font-weight: normal; -fx-font-style: italic; -fx-text-fill: white;";
    // style for message displayed before all the saveables
    private static final String SAVEABLES_PRELUDE_STYLE = "-fx-font: 25px Arial;"
            + "-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: white;";
    // CSS styles for Saveables in CouponWindow
    private static final String SAVEABLE_LABEL_STYLE = "-fx-font: 12px Arial;"
            + "-fx-font-weight: bold; -fx-text-fill: white;";
    // style class for history date
    private static final String EXPANDED_COUPON_DETAILS_TITLE = "expanded-coupon-details-title";

    public final Coupon coupon;

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
    private Label numericalAmount;
    @FXML
    private VBox saveables;
    @FXML
    private VBox historyPane;
    @FXML
    private Scene scene;

    /**
     * Creates a new Coupon Window.
     */
    public CouponWindow(Stage root, Coupon coupon, String moneySymbol) {
        super(FXML, root);
        this.coupon = coupon;

        name.setText(coupon.getName().fullName);
        promoCode.setText(coupon.getPromoCode().toString());
        duration.setText(
                String.format("%s to %s",
                        coupon.getStartDate().date.format(DAY_SHORT_MONTH_YEAR_FORMATTER),
                        coupon.getExpiryDate().date.format(DAY_SHORT_MONTH_YEAR_FORMATTER)
                )
        );
        usage.setText(String.format("%s/%s", coupon.getUsage().value, coupon.getLimit().toUiText()));
        remindDate.setText(coupon.getRemindDate().toString());
        condition.setText(coupon.getCondition().value);
        coupon.getTags().stream().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // set savings pane
        setSavings(coupon.getSavingsForEachUse(), moneySymbol);
        // set history
        setHistory(coupon.getSavingsMap(), moneySymbol);

        root.setTitle("Coupon Details of : " + name.getText());
        logger.info("CouponWindow created for " + name.getText() + ".");

        UiUtil.setExitAccelerator(root, scene, logger, "Coupon Window");
    }

    public CouponWindow(Coupon coupon, String moneySymbol) {
        this(new Stage(), coupon, moneySymbol);
    }

    /**
     * Sets the Savings to be displayed.
     *
     * @param s           The Savings to be displayed.
     * @param moneySymbol Money symbol for the display.
     */
    public void setSavings(Savings s, String moneySymbol) {
        // handle numerical value
        String savingsNumber = SavingsBox.getSavingsString(s, moneySymbol);
        if (savingsNumber.isBlank()) {
            this.numericalAmount.setText("(no amount)");
            this.numericalAmount.setStyle(NO_NUMERICAL_AMOUNT_STYLE);
        } else {
            this.numericalAmount.setText(savingsNumber);
        }
        // handle saveables
        s.getSaveables().ifPresentOrElse(saveablesList -> {
            String saveablesPrelude = "Saveables:";
            Label preludeLabel = new Label(saveablesPrelude);
            preludeLabel.setStyle(CouponWindow.SAVEABLES_PRELUDE_STYLE);
            this.saveables.getChildren().add(preludeLabel);
            for (Saveable sva: saveablesList) {
                Label saveableLabel = new Label();
                if (sva.getCount() == 1) {
                    saveableLabel.setText(sva.getValue());
                } else {
                    saveableLabel.setText(sva.toString());
                }
                saveableLabel.setStyle(CouponWindow.SAVEABLE_LABEL_STYLE);
                this.saveables.getChildren().add(saveableLabel);
            }
            // put an invisible ending label to ensure that width of
            // ScrollPane with scroll bar does not truncate the
            // prelude text, which happens quite often
            Label invisibleEndingLabel = new Label(saveablesPrelude + "----");
            invisibleEndingLabel.setStyle(CouponWindow.SAVEABLES_PRELUDE_STYLE);
            invisibleEndingLabel.setMinHeight(0.0);
            invisibleEndingLabel.setMaxHeight(0.0);
            this.saveables.getChildren().add(invisibleEndingLabel);
        }, () -> {
                this.saveables.setStyle(CouponWindow.HIDDEN);
                this.numericalAmount.setTranslateX(20);
            });
    }

    /**
     * Sets the usage history in the CouponWindow.
     *
     * @param dssm The DateSavingsSumMap representing
     *             associations of date to savings
     *             earned on that date.
     * @param moneySymbol Money symbol for the display.
     */
    public void setHistory(DateSavingsSumMap dssm, String moneySymbol) {
        List<Map.Entry<LocalDate, PureMonetarySavings>> sortedEntries =
                new ArrayList<Map.Entry<LocalDate, PureMonetarySavings>>(dssm.entrySet());
        // sort by dates (descending, from present to past)
        sortedEntries.sort((e1, e2) -> e2.getKey().compareTo(e1.getKey()));
        sortedEntries.forEach(entry -> {
            Label dateLabel = new Label(entry.getKey().format(DateUtil.DATE_FORMATTER));
            dateLabel.getStyleClass().add(CouponWindow.EXPANDED_COUPON_DETAILS_TITLE);

            Text savingsLabel = new Text();
            savingsLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16));
            savingsLabel.setText(entry.getValue().getStringWithMoneySymbol(moneySymbol) + "\n");
            savingsLabel.setWrappingWidth(190);
            savingsLabel.setFill(Color.WHITE);

            this.historyPane.getChildren().add(dateLabel);
            this.historyPane.getChildren().add(savingsLabel);
        });
    }

    /**
     * Shows the coupon window.
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public void close() {
        getRoot().close();
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
