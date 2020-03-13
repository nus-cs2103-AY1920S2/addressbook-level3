package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RecipeBook level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label name1;
    @FXML
    private Label id;
    @FXML
    private Label id1;
    @FXML
    private Label phone;
    @FXML
    private Label phone1;
    @FXML
    private Label address;
    @FXML
    private Label address1;
    @FXML
    private Label email;
    @FXML
    private Label email1;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane tags1;

    public PersonCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;

        if (displayedIndex % 2 == 0) {
            id.setText(displayedIndex + ". ");
            name.setText(recipe.getName().name);
            phone.setText(recipe.getPhone().value);
            address.setText(recipe.getAddress().value);
            email.setText(recipe.getEmail().value);
            recipe.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
         
            id1.setText(displayedIndex + ". ");
            name1.setText(recipe.getName().name);
            phone1.setText(recipe.getPhone().value);
            address1.setText(recipe.getAddress().value);
            email1.setText(recipe.getEmail().value);
            recipe.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags1.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
