package fithelper.ui.calendar;
import java.time.LocalDate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.util.EntriesUtil;
import fithelper.model.entry.Entry;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * Create an anchor pane that can store additional data.
 */
public class DayNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private final Logger logger = LogsCenter.getLogger(DayNode.class);
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportsList;
    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public DayNode(ObservableList<Entry> sportsList, ObservableList<Entry> foodList, Node... children) {
        super(children);
        this.foodList = foodList;
        this.sportsList = sportsList;
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> show());
    }

    /**
     * Displays entries from a particular date when a date is clicked
     */
    public void show() {
        logger.info("Showing day page about this date.");
        ObservableList<Entry> entries = EntriesUtil.getEntries(foodList, sportsList, date);
        DayCardWithStage temp = new DayCardWithStage(entries, date);
        temp.getRoot().show();
        temp.getRoot().centerOnScreen();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
