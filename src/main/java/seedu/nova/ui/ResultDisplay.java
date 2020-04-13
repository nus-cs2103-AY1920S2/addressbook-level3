package seedu.nova.ui;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private ReadOnlyDoubleProperty width;
    @FXML
    private StackPane placeHolder;

    public ResultDisplay(ReadOnlyDoubleProperty width) {
        super(FXML);
        this.width = width;
        VBox.setVgrow(placeHolder, Priority.NEVER);

    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        Text txt = new Text();
        txt.setText(feedbackToUser);
        txt.setStyle("-fx-font: 18 arial;");
        width.addListener((
                observable, oldValue, newValue) ->
                txt.setWrappingWidth(newValue.doubleValue() - 30.0));
        placeHolder.getChildren().add(txt);
        VBox.setVgrow(txt, Priority.NEVER);
    }

}
