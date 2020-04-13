package seedu.address.ui.mod;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.mod.ModLinkPair;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Pair<String, ModLink>}.
 */
public class ModLinkCard extends UiPart<Region> {

    private static final String FXML = "ModLinkCard.fxml";

    public final ModLinkPair modLinkPair;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label modLinkName;
    @FXML
    private Label modLink;

    public ModLinkCard(ModLinkPair modLinkPair, int displayedIndex) {
        super(FXML);
        this.modLinkPair = modLinkPair;
        id.setText(displayedIndex + ". ");
        modLinkName.setText(modLinkPair.getKey());
        modLink.setText(modLinkPair.getValue().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModLinkCard)) {
            return false;
        }

        // state check
        ModLinkCard card = (ModLinkCard) other;
        return id.getText().equals(card.id.getText())
            && modLinkPair.equals(card.modLinkPair);
    }
}

