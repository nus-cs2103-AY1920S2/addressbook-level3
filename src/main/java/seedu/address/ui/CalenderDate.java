package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;


/**
 * CalenderPanel that holds the month
 */
public class CalenderDate extends UiPart<Region> {
    private static final String FXML = "CalenderDate.fxml";
    private final Logger logger = LogsCenter.getLogger(CalenderDate.class);

    private String date;
    private String day;

    @FXML
    private Label calenderDate;



    public CalenderDate(String date, String day) {

        super(FXML);
        this.date = date;
        this.day = day;
        calenderDate.setText(date);
    }



}
