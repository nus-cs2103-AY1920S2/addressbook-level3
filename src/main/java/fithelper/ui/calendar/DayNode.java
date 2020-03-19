package fithelper.ui.calendar;
import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


/**
 * Create an anchor pane that can store additional data.
 */
public class DayNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public DayNode(javafx.scene.Node... children) {
        super(children);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
