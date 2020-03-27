package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * An UI component that displays information of a {@code Profile}.
 */
public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deadline deadline;

    @FXML
    private HBox cardPane;
    @FXML
    private Text module;
    @FXML
    private Label description;
    @FXML
    private Text date;
    @FXML
    private Label time;


    public DeadlineCard(Deadline deadline) {
        super(FXML);
        this.deadline = deadline;
        for (Module mod : Profile.getCurModules().getModuleList()) {
            if (mod.getModuleCode().moduleCode.equals(deadline.getModuleCode())) {
                int tag = mod.getTag();
                if (tag == 0) { //up to 8 modules
                    module.setFill(Color.valueOf("84a9ac"));
                } else if (tag == 1) {
                    module.setFill(Color.valueOf("d45d79"));
                } else if (tag == 2) {
                    module.setFill(Color.valueOf("ff9933"));
                } else if (tag == 3) {
                    module.setFill(Color.valueOf("f6d186"));
                } else if (tag == 4) {
                    module.setFill(Color.valueOf("b590ca"));
                } else if (tag == 5) {
                    module.setFill(Color.valueOf("ea9085"));
                } else if (tag == 6) {
                    module.setFill(Color.valueOf("cae8d5"));
                } else {

                }
            }
        }
        //module name
        module.setText(deadline.getModuleCode());

        description.setText("Task: " + deadline.getDescription());
        if (deadline.getDate() == null) {
            date.setText("Date: -");
            date.setFill(Color.WHITE);
            time.setText("Time: -");
        } else {
            date.setText("Date: " + deadline.getStringDate());
            if (deadline.getTag().equals("RED")) {
                date.setFill(Color.valueOf("F05B5B"));
            } else if (deadline.getTag().equals("YELLOW")) {
                date.setFill(Color.YELLOW);
            } else {
                date.setFill(Color.LAWNGREEN);
            }

            time.setText("Time: " + deadline.getStringTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCard)) {
            return false;
        }

        // state check
        DeadlineCard card = (DeadlineCard) other;
        return deadline.equals(card.deadline);
    }
}
