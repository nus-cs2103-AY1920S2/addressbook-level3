package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;


/**
 * CalenderPanel that holds the month
 */
public class CalenderDate extends UiPart<Region> {
    private static final String FXML = "CalenderDate.fxml";
    private final Logger logger = LogsCenter.getLogger(CalenderDate.class);
    private int count = 0;

    private String date;
    private String day;

    @FXML
    private Label calenderDate;

    @FXML
    private Circle circle;



    public CalenderDate(String date, String day) {

        super(FXML);
        this.date = date;
        this.day = day;
        calenderDate.setText(date);
        setCircleNotVisible();
        setCssStyles();
    }

    public void setCssStyles() {
        calenderDate.setStyle("-fx-text-fill: #fb7b6b");

    }

    public void setCircleNotVisible() {
        circle.setVisible(false);

    }

    public void increaseCount() {
        count++;
    }

    public void decreaseCount() {
        count--;
    }

    public int getCount() {
        return this.count;
    }

    public void setCircleVisible() {
        circle.setVisible(true);
    }



}
