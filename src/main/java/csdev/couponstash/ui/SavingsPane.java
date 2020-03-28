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
            this.numericalAmount.setStyle("visibility: hidden;");
        } else {
            this.numericalAmount.setText(savingsNumber);
        }
        s.getSaveables().ifPresent(saveablesList -> saveablesList.stream()
            .forEach(sva -> saveables.getChildren().add(new Label(sva.getValue()))));
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
