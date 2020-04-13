package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Pie Chart for time
 */
public class TimePieChart extends UiPart<Region> {

    private static final String FXML = "TimePieChartPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TimePieChart.class);

    @FXML
    private StackPane timePieChartPane;

    public TimePieChart(ObservableList<Time> timeList, ObservableList<Group> groupList,
                        ObservableList<Person> personList) {
        super(FXML);

        Time personTime = new Time(0, 0);
        Time groupTime = new Time(0, 0);
        for (Person onePerson : personList) {
            int personHour = onePerson.getTime().getHours();
            int personMin = onePerson.getTime().getMinutes();
            personTime.addTime(personMin, personHour);
        }

        for (Group oneGroup : groupList) {
            int groupHour = oneGroup.getTimeSpent().getHours();
            int groupMin = oneGroup.getTimeSpent().getMinutes();
            groupTime.addTime(groupMin, groupHour);
        }
        timeList.add(personTime);
        timeList.add(groupTime);


        double individualTotalTime = 0.0;
        double groupTotalTime = 0.0;

        individualTotalTime = timeList.get(0).getHours() + (double) (timeList.get(0).getMinutes() / 100);
        groupTotalTime = timeList.get(1).getHours() + (double) (timeList.get(1).getMinutes() / 100);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Individual Time", individualTotalTime),
                        new PieChart.Data("Group Time", groupTotalTime));
        final PieChart timePieChart = new PieChart(pieChartData);
        timePieChart.setTitle("Time Spent");
        timePieChartPane.getChildren().add(timePieChart);

    }
}
