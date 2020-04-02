package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.todolist.Task;

/**
 * Panel containing the list of persons.
 */
public class CalenderListPanel extends UiPart<Region> {
    private static final String FXML = "CalenderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalenderListPanel.class);

    @FXML
    private ListView<Task> calenderDeadlineListView;

    public CalenderListPanel(ObservableList<Task> deadlineList) {
        super(FXML);
        calenderDeadlineListView.setItems(deadlineList);
        calenderDeadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class DeadlineListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task deadline, boolean empty) {
            super.updateItem(deadline, empty);
            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(new CalenderDeadline(deadline, getIndex() + 1).getRoot());
                System.out.println("Else");
                String[] date = deadline.getDate().split("-");
                int year = Integer.parseInt(date[2]);
                String day = date[0];
                int month = Integer.parseInt(date[1]);

                /** problem with setting circle to not visible
                 *  is that this else runs random number of time, so you dont know how many count to --
                 */
                System.out.println(CalenderPanel.getYear());
                System.out.println(CalenderPanel.getCurrentMonth());
                if (CalenderPanel.getYear() == year
                        && CalenderPanel.getCurrentMonth() == month) {
                    CalenderDate calenderDate = CalenderPanel.getCalenderDatesArrayList()
                            .get(Integer.parseInt(day) - 1);

                    calenderDate.setCircleVisible();
                    calenderDate.increaseCount();
                }
            }
        }

        private void setCircleNotVisible() {
            for (CalenderDate calenderDate : CalenderPanel.getCalenderDatesArrayList()) {
                if (calenderDate.getCount() == 0) {
                    calenderDate.setCircleNotVisible();
                }
            }
        }
    }

}
