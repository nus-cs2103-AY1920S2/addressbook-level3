package csdev.couponstash.ui;

import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.Savings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SavingsPane extends UiPart<Region> {
    private static final String FXML = "SavingsPane.fxml";
    private static final String HIDDEN = "visibility: hidden;";
    private static final String SAVEABLE_CLASS = "sv-label";

    @FXML
    private VBox savingsPane;
    @FXML
    private Label numericalAmount;
    @FXML
    private FlowPane saveables;

    public SavingsPane() {
        super(SavingsPane.FXML);
    }

    /**
     * Sets the Savings to be displayed in this SavingsPane.
     *
     * @param s The Savings to be displayed.
     * @param moneySymbol Money symbol for the display.
     */
    public void setSavings(Savings s, String moneySymbol) {
        String savingsNumber = getSavingsString(s, moneySymbol);
        if (savingsNumber.isBlank()) {
            this.numericalAmount.setStyle(SavingsPane.HIDDEN);
        } else {
            this.numericalAmount.setText(savingsNumber);
            this.numericalAmount.setStyle("-fx-font-size: "
                    + (125 / savingsNumber.length())
                    + ";");
        }
        s.getSaveables().ifPresentOrElse(saveablesList -> saveablesList.stream()
            .forEach(sva -> {
                Label label = new Label(sva.getValue());
                // ensure that label has the correct CSS style
                label.getStyleClass().add(SavingsPane.SAVEABLE_CLASS);
                saveables.getChildren().add(label);
            }), () -> this.saveables.setStyle(SavingsPane.HIDDEN));
    }

    private static String getSavingsString(Savings s, String moneySymbol) {
        // assumes that Savings only has either PercentageAmount
        // or MonetaryAmount, but never both
        StringBuilder sb = new StringBuilder();
        s.getPercentageAmount().ifPresent(pc ->
            sb.append(pc.getValue()).append(PercentageAmount.PERCENT_SUFFIX));
        s.getMonetaryAmount().ifPresent(ma ->
            sb.append(ma.getStringWithMoneySymbol(moneySymbol)));
        return sb.toString();
    }
}
