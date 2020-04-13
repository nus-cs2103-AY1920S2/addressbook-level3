package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.ModuleBook;
import seedu.address.model.studentprofile.Profile;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ModulesYetTaken extends UiPart<Region> {

    private static final String FXML = "ModulesYetTaken.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Profile student;

    @FXML
    private Label modulesEligible;

    @FXML
    private Label modulesUneligible;

    public ModulesYetTaken(String asd) {
        super(FXML);
        this.student = new Profile(new ModuleBook());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModulesYetTaken)) {
            return false;
        }
        return false;
    }
}
