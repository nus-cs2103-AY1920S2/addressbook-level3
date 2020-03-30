package seedu.address.ui;

import seedu.address.model.ReadOnlyPomodoro;
import seedu.address.logic.PetManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

public class SettingsDisplay extends UiPart<Region> {
    private static final String FXML = "SettingsDisplay.fxml";

    private ReadOnlyPomodoro pomodoro;
    private PetManager petManager;

    @FXML
    private VBox settingsPane;
    @FXML
    private Label petNameLabel;
    @FXML
    private TextField petNameTextField;
    @FXML
    private Label pomodoroLabel;
    @FXML
    private TextField pomodoroTextField;
    @FXML
    private Label challengeLabel;
    @FXML
    private TextField challengeTextField;

    public SettingsDisplay(PetManager petManager, ReadOnlyPomodoro pomodoro) {
        super(FXML);
        this.pomodoro = pomodoro;
        this.petManager = petManager;
        
        String name = petManager.getPetName();
        petNameTextField.setText(name);

        String duration = pomodoro.getDefaultTime();
        pomodoroTextField.setText(duration);
    }

    
}
