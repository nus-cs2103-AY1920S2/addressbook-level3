package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String NEUTRAL_STYLE_CLASS = "neutral";
    public static final String PASS_STYLE_CLASS = "pass";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Sets the text of the ResultDisplay to the feedback produced by executing the command entered.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Sets the style of the ResultDisplay to the pass style.
     */
    public void setStyleToIndicatePass() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(PASS_STYLE_CLASS)) {
            return;
        }
        styleClass.add(PASS_STYLE_CLASS);
    }

    /**
     * Sets the style of the ResultDisplay to the failure style.
     */
    public void setStyleToIndicateFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the style of the ResultDisplay to the neutral style.
     */
    public void setStyleToIndicateNeutral() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(NEUTRAL_STYLE_CLASS)) {
            return;
        }
        styleClass.add(NEUTRAL_STYLE_CLASS);
    }

    /**
     * Resets the styles of the ResultDisplay.
     */
    public void resetStyles() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        styleClass.remove(NEUTRAL_STYLE_CLASS);
        styleClass.remove(ERROR_STYLE_CLASS);
        styleClass.remove(PASS_STYLE_CLASS);
    }
}
