package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

  private static final String FXML = "ResultDisplay.fxml";

  @FXML
  private TextArea resultDisplay;

  public ResultDisplay() {
    super(FXML);


    getRoot().setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        System.out.println(ke.getCode());
        if (ke.getCode() == KeyCode.CHANNEL_UP) {
          System.out.println("Key Pressed: " + ke.getCode());
        }
      }
    });
  }

  public void setFeedbackToUser(String feedbackToUser) {
    requireNonNull(feedbackToUser);
    resultDisplay.setText(feedbackToUser);
  }

}
