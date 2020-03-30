package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;


/**
 * CalenderPanel that holds the month
 */
public class CalenderPanel extends UiPart<Region> {
    private static final String FXML = "Calender.fxml";
    private final Logger logger = LogsCenter.getLogger(CalenderPanel.class);
    private final String[] monthsArray = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};

    @FXML
    private Text month;

    @FXML
    private Text todayDate;

    @FXML
    private GridPane calenderGrid;

    @FXML
    private Label mon1;


    public CalenderPanel() {

        super(FXML);

        this.setMonth();
        this.setDates();
    }

    public void setMonth() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String[] currentDateArray = dtf.format(now).split("/");
        String currentMonth = currentDateArray[1];
        String currentYear = currentDateArray[0];
        String date = currentDateArray[2];

        int currentMonthInt = Integer.parseInt(currentMonth);
        String currentMonthAndYear = monthsArray[currentMonthInt - 1] + " " + currentYear;
        month.setText(currentMonthAndYear);
        todayDate.setText("Today: " + date + " " + currentMonthAndYear);
    }

    public void setDates() {

        mon1.setText("1");
        // to do finish integration of the calender


    }

}
