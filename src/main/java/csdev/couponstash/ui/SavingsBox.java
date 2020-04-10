package csdev.couponstash.ui;

import java.util.List;
import java.util.function.Consumer;

import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The UI element that holds the Savings, this is
 * displayed as a blue box beside the Coupon.
 */
public class SavingsBox extends UiPart<Region> {
    private static final String FXML = "SavingsBox.fxml";
    // to set certain elements to be invisible
    private static final String HIDDEN = "visibility: hidden;";
    // allow CSS styles for each label in the FlowPane
    private static final String SAVEABLE_CLASS = "sv-label";
    // make it more obvious that savings can exist without
    // numerical but with saveable free items
    private static final String INFO_TEXT_STYLE = "-fx-font-size: 12;"
            + "-fx-font-weight: normal; -fx-font-style: italic; -fx-text-fill: #6c96be;";
    // controls font size of number amount
    private static final int BASE_FONT_SIZE = 105;
    // if no saveables, translate numerical amount
    private static final int NUMERICAL_AMOUNT_TRANSLATE_AMOUNT = 12;
    // maximum number of saveables to be displayed
    private static final int MAXIMUM_NUMBER_OF_SHOWN_SAVEABLES = 2;

    @FXML
    private VBox savingsPane;
    @FXML
    private Label numericalAmount;
    @FXML
    private FlowPane saveables;

    public SavingsBox() {
        super(SavingsBox.FXML);
    }

    /**
     * Sets the Savings to be displayed in this SavingsBox.
     *
     * @param s The Savings to be displayed.
     * @param moneySymbol Money symbol for the display.
     */
    public void setSavings(Savings s, String moneySymbol) {

        // logic for adding labels to the savings box for saveables
        Consumer<List<Saveable>> labelAdderForSaveablesList = saveablesList -> {
            int numberOfSaveables = saveablesList.size();

            // loop through all the items, or to maximum number to show (ignore last item)
            int loopLimit = Math.min(numberOfSaveables, SavingsBox.MAXIMUM_NUMBER_OF_SHOWN_SAVEABLES - 1);
            for (int i = 0; i < loopLimit; i++) {
                addLabelToSavingsBox(saveablesList.get(i));
            }

            // special cases for last item
            if (numberOfSaveables > SavingsBox.MAXIMUM_NUMBER_OF_SHOWN_SAVEABLES) {
                // show a label that denotes presence of more saveables
                // but these saveables are not shown in savings box
                Label label = new Label("(and more...)");
                label.setStyle(SavingsBox.INFO_TEXT_STYLE);
                saveables.getChildren().add(label);
            } else if (numberOfSaveables == SavingsBox.MAXIMUM_NUMBER_OF_SHOWN_SAVEABLES) {
                // if there is only one more item, we have enough space to show it
                addLabelToSavingsBox(saveablesList.get(numberOfSaveables - 1));
            }
        };

        // handle saveables
        s.getSaveables().ifPresentOrElse(labelAdderForSaveablesList, () -> {
            this.saveables.setStyle(SavingsBox.HIDDEN);
            this.numericalAmount.setTranslateY(SavingsBox.NUMERICAL_AMOUNT_TRANSLATE_AMOUNT);
        });

        // handle numerical value
        String savingsNumber = getSavingsString(s, moneySymbol);
        if (savingsNumber.isBlank()) {
            this.numericalAmount.setText("(no amount)");
            this.numericalAmount.setStyle(SavingsBox.INFO_TEXT_STYLE);
        } else {
            this.numericalAmount.setText(savingsNumber);
            // resize numerical amount dynamically
            this.numericalAmount.setStyle("-fx-font-size: "
                    + (SavingsBox.BASE_FONT_SIZE / savingsNumber.length())
                    + ";");
        }
    }

    /**
     * Given a Savings object and the money symbol, return
     * a String containing a formatted numerical value for
     * use in SavingsBox, or an empty String if the
     * Savings does not have any MonetaryAmount
     * or PercentageAmount (only Saveable).
     *
     * @param s The Savings object to access.
     * @param moneySymbol The money symbol set in UserPrefs.
     * @return Nicely formatted String of the numerical savings.
     */
    protected static String getSavingsString(Savings s, String moneySymbol) {
        // assumes that Savings only has either PercentageAmount
        // or MonetaryAmount, but never both
        StringBuilder sb = new StringBuilder();
        s.getPercentageAmount().ifPresent(pc ->
            sb.append(pc.toString()));
        s.getMonetaryAmount().ifPresent(ma ->
            sb.append(ma.getStringWithMoneySymbol(moneySymbol)));
        return sb.toString();
    }

    /**
     * Given a Saveable, adds a label to the SavingsBox
     * that displays the name of the Saveable.
     *
     * @param sva The Saveable to be displayed in
     *            SavingsBox as a Label.
     */
    private void addLabelToSavingsBox(Saveable sva) {
        Label label = new Label(sva.getValue());
        // ensure that label has the correct CSS style
        label.getStyleClass().add(SavingsBox.SAVEABLE_CLASS);
        saveables.getChildren().add(label);
    }
}
