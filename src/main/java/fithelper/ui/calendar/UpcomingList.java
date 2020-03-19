package fithelper.ui.calendar;

import java.time.LocalDateTime;

import fithelper.model.entry.Entry;
import fithelper.ui.FoodCard;
import fithelper.ui.SportCard;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * A section which displays upcoming tasks.
 */
public class UpcomingList extends UiPart<AnchorPane> {
    private static final String FXML = "upcomingList.fxml";
    private LocalDateTime time;
    private int index = 0;

    @FXML
    private VBox upcomingList;

    @FXML
    private Label upcomingTitle;

    public UpcomingList(ObservableList<Entry> foodList, ObservableList<Entry> sportsList) {
        super(FXML);
        time = LocalDateTime.now();
        upcomingTitle.setText("Upcoming in " + time.getMonth());
        getList(foodList, sportsList);
    }

    /**
     * Updates the upcoming tasks.
     */
    public void getList(ObservableList<Entry> foodList, ObservableList<Entry> sportsList) {
        upcomingList.getChildren().clear();
        findList(foodList);
        findList(sportsList);
    }

    /**
     * Finds the upcoming tasks.
     */
    public void findList(ObservableList<Entry> entries) {
        for (Entry entry: entries) {
            if (LocalDateTime.now().isBefore(entry.getDateTime())) {
                if (entry.getType().toString().equals("food")) {
                    index++;
                    FoodCard foodCard = new FoodCard(entry, index);
                    upcomingList.getChildren().add(foodCard.getRoot());
                } else {
                    index++;
                    SportCard sportCard = new SportCard(entry, index);
                    upcomingList.getChildren().add(sportCard.getRoot());
                }
            }
        }
    }
}
