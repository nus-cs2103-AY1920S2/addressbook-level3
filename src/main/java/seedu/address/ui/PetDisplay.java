package seedu.address.ui;

import java.nio.file.Path;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** An UI component that displays Pet {@code Pet}. */
public class PetDisplay extends UiPart<Region> {

    private static final String FXML = "PetDisplay.fxml";

    public Path petFilepath; // mutable
    public Path accessoryFilepath; // mutable

    @FXML private VBox petPane;
    @FXML private ImageView petPic;
    @FXML private ImageView accessoryPic;

    public PetDisplay(Path petFilepath, Path accessoryFilepath) {
        super(FXML);
        this.petFilepath = petFilepath;
        this.accessoryFilepath = null;

        if (accessoryFilepath != null) {
            this.accessoryFilepath = accessoryFilepath;
            Image image = new Image(String.valueOf(accessoryFilepath));
            accessoryPic.setImage(image);
        }

        // set up pet image
        Image image = new Image(String.valueOf(petFilepath));
        petPic.setImage(image);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetDisplay)) {
            return false;
        }

        // state check
        PetDisplay card = (PetDisplay) other;
        return petPic.getImage().equals(card.petPic.getImage());
    }
}
