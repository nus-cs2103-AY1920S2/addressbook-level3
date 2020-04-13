package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.group.Group;

/**
 * A UI component that displaces information of a {@code Group}
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label timeSpent;
    @FXML
    private Label memberLabel;
    @FXML
    private FlowPane members;
    @FXML
    private Label eventLabel;
    @FXML
    private FlowPane events;

    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        name.setText(group.getName().fullName);
        String text = group.getTimeSpent().toString();
        timeSpent.setText("Total Time Spent: " + text);
        memberLabel.setText("Members: ");
        group
                .getMembers()
                .stream()
                .forEach(member -> members.getChildren().add(new Label(member.toString())));
        eventLabel.setText("Events: ");
        group
                .getEvents()
                .stream()
                .forEach(event -> events.getChildren().add(new Label(event.toString())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCard)) {
            return false;
        }

        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText()) && group.equals(card.group);

    }
}
