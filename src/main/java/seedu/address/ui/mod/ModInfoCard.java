package seedu.address.ui.mod;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.mod.Mod;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Mod}.
 */
public class ModInfoCard extends UiPart<Region> {

    private static final String FXML = "ModInfoCard.fxml";

    public final Mod mod;
    private ModLinkPanel modLinkPanel;

    @FXML
    private HBox cardPane;

    @FXML
    private Label modNoteLabel;

    @FXML
    private Label modCodeLabel;

    @FXML
    private StackPane modLinkPanelPlaceholder;


    public ModInfoCard(Mod viewedMod) {
        super(FXML);
        this.mod = viewedMod;
        modCodeLabel.setText(viewedMod.getModCode().toString() + " " + viewedMod.getModName());
        modNoteLabel.setText(viewedMod.getNote());

        if (viewedMod.getLinks().size() > 0) {
            modLinkPanel = new ModLinkPanel(viewedMod.getLinks());
            modLinkPanelPlaceholder.getChildren().add(modLinkPanel.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModCard)) {
            return false;
        }

        // state check
        ModInfoCard card = (ModInfoCard) other;
        return modCodeLabel.getText().equals(card.modCodeLabel.getText())
            && modNoteLabel.getText().equals(card.modNoteLabel.getText())
            && mod.equals(card.mod);
    }
}
