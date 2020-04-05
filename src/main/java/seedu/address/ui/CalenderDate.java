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
    public void setPriorityColour(Priority priority ) {

        String priorityLevel = priority.toString();

        if (priorityLevel.equals("Very high")) {
            circle.setStyle("-fx-fill: red");
        } else if (priorityLevel.equals("High")) {
            circle.setStyle("-fx-fill: orange");
        } else if (priorityLevel.equals("Medium")) {
            circle.setStyle("-fx-fill: yellow");
        } else if (priorityLevel.equals("Low")) {
            circle.setStyle("-fx-fill: green");
        } else if (priorityLevel.equals("Very Low")) {
            circle.setStyle("-fx-fill: white");
        }
    }

    public String getDate() {
        return this.date;
    }


    public void setCircleVisible() {
        circle.setVisible(true);
    }



}
