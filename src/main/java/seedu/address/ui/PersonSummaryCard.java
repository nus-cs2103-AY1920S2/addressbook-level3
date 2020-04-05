package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.bluetooth.Person;

public class PersonSummaryCard extends UiPart<Region>  {
    private static final String FXML="PersonSummaryCard.fxml";

    public final Person person;

    @FXML
    private Label name;

    @FXML
    private Label userid;

    @FXML
    private Label age;

    @FXML
    private Label address;

    @FXML
    private Label mobile;


    public PersonSummaryCard(Person person, int displayedIndex){
        super(FXML);
        this.person = person;

        name.setText(person.getName());
        userid.setText(Integer.toString(person.getUserID()));
        age.setText(Integer.toString(person.getAge()));
        address.setText(person.getAddress());
        mobile.setText(person.getMobile());
    }

    @Override
    public boolean equals(Object other){return true;}
}
