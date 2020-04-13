package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.nusmodule.Priority;


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

    @FXML
    private Circle circle;



    public CalenderDate(String date, int day) {

        super(FXML);
        this.date = date;
        this.day = "" + day;

        calenderDate.setText(this.day);
        setCircleNotVisible();
        setCssStyles();
    }

    public void setCssStyles() {
        calenderDate.setStyle("-fx-text-fill: #fb7b6b");

    }

    public void setCircleNotVisible() {
        circle.setVisible(false);

    }

    /**
     * Changes colour of the circle based on priority
     * @param priority
     */
    public void setPriorityColour(Priority priority) {

        String priorityLevel = priority.toString();

        if (priorityLevel.equals("Very high")) {
            circle.setStyle("-fx-fill: #dd2c00");
        } else if (priorityLevel.equals("High")) {
            circle.setStyle("-fx-fill: #ff5722");
        } else if (priorityLevel.equals("Medium")) {
            circle.setStyle("-fx-fill: #f2ed6f");
        } else if (priorityLevel.equals("Low")) {
            circle.setStyle("-fx-fill: #639a67");
        } else {
            circle.setStyle("-fx-fill: #2b580c");
        }
    }

    public String getDate() {
        return this.date;
    }


    public void setCircleVisible() {
        circle.setVisible(true);
    }



}
