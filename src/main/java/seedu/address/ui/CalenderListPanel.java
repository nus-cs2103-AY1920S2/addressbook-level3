package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.calender.Task;
import seedu.address.model.nusmodule.ModuleTask;

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
            for (CalenderDate calenderDate : CalenderPanel.getCalenderDatesArrayList()) {
                calenderDate.setCircleNotVisible();
            }
            for (CalenderDate calenderDate : CalenderPanel.getCalenderDatesArrayList()) {
                if (Task.isTaskPresent(calenderDate.getDate())) {
                    calenderDate.setCircleVisible();
                    ArrayList<Task> allTask = Task.getDeadlineTaskHashMap().get(calenderDate.getDate());
                    for (Task tasks : allTask) {
                        if (tasks instanceof ModuleTask) {
                            calenderDate.setPriorityColour(((ModuleTask) tasks).getPriority());
                        }
                    }

                }
            }
            super.updateItem(deadline, empty);
            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(new CalenderDeadline(deadline, getIndex() + 1).getRoot());


            }
        }

    }

}
