package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays the information of a person whose birthday is upcoming.
 */
public class PersonCardBday extends UiPart<Region> {

    private static final String FXML = "PersonListBdayCard.fxml";

    public final Person person;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label birthday;

    public PersonCardBday(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        birthday.setText(person.getBirthday().toString());
        String remarkValue = "";
        int i = 0;
        while (i < person.getRemark().size()) {
            remarkValue += person.getRemark().get(i).value;
            if (i != person.getRemark().size() - 1) {
                remarkValue += ", ";
            }
            i++;
        }
        remark.setText(remarkValue);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
        PersonCardBday card = (PersonCardBday) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }

}
