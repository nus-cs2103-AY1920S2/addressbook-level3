package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;

/**
 * A UI component that displays the information of a person whose birthday is upcoming.
 */
public class PersonCardBday extends UiPart<Region> {

    private static final String FXML = "PersonListBdayCard.fxml";
    private static final DateTimeFormatter inputFormat = new DateTimeFormatterBuilder().appendPattern("MM-dd")
        .parseDefaulting(ChronoField.YEAR, 2020).toFormatter(Locale.ENGLISH);

    public final Person person;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label remark;
    @FXML
    private Label birthday;

    public PersonCardBday(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        name.setWrapText(true);
        phone.setText("Phone: " + person.getPhone().value);

        birthday.setStyle("-fx-text-fill: #000");
        if (LocalDate.parse(person.getBirthday().birthday, inputFormat).compareTo(LocalDate.now()) == 0) {
            birthday.setText(" Today ");
            birthday.setBackground(new Background(new BackgroundFill(Color.rgb(255, 87, 51),
                CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (LocalDate.parse(person.getBirthday().birthday, inputFormat)
            .compareTo(LocalDate.now().plusDays(1)) == 0) {
            birthday.setText(" Tomorrow ");
            birthday.setBackground(new Background(new BackgroundFill(Color.rgb(255, 195, 0),
                CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            birthday.setText(" Upcoming: " + person.getBirthday().toString() + " ");
            birthday.setBackground(new Background(new BackgroundFill(Color.rgb(218, 247, 166),
                CornerRadii.EMPTY, Insets.EMPTY)));
        }

        String remarkValue = "";
        int i = 0;
        while (i < person.getRemark().size()) {
            remarkValue += person.getRemark().get(i).value;
            if (i != person.getRemark().size() - 1) {
                remarkValue += ", ";
            }
            i++;
        }
        remark.setText("Remarks: " + remarkValue);
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
        return person.equals(card.person);
    }
}
