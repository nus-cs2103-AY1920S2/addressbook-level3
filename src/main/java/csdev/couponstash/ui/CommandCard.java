package csdev.couponstash.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Command}.
 */
public class CommandCard extends UiPart<Region> {

    private static final String FXML = "CommandCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label usage;

    public CommandCard(String commandWord, String messageUsage) {
        super(FXML);
        name.setText(commandWord);
        usage.setText(messageUsage.split(commandWord + ": ", 2)[1]);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandCard)) {
            return false;
        }

        // state check
        CommandCard command = (CommandCard) other;
        return name.getText().equals(command.name.getText())
                && usage.getText().equals(command.usage.getText());
    }
}
