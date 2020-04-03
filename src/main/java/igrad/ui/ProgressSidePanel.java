package igrad.ui;

import java.util.Optional;

import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;

/**
 * Panel containing the progress (MCs, modules completed, requirements completed, CAP info) of the user.
 */
public class ProgressSidePanel extends UiPart<Region> {

    public static final String FXML = "ProgressSidePanel.fxml";

    private int totalCreditsRequired;
    private int totalCreditsFulfilled;
    private double progressBarPercentage;

    @FXML
    private Label courseNameLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label creditsCount;

    public ProgressSidePanel(Model model) {
        super(FXML);

        updateProgress(model);

    }

    /**
     * Updates the progress panel
     */
    public void updateProgress(Model model) {

        CourseInfo courseInfo = model.getCourseInfo();

        Optional<Name> courseName = courseInfo.getName();
        Optional<Credits> credits = courseInfo.getCredits();

        courseName.ifPresentOrElse(
            name -> courseNameLabel.setText(name.value), () -> courseNameLabel
                .setText("Your Course."));

        String creditsCountString = "";

        if (credits.isPresent()) {
            progressBarPercentage = (double) credits.get().getCreditsFulfilled()
                / credits.get().getCreditsRequired();
            progressBar.setProgress(progressBarPercentage);

            creditsCountString = credits.get().getCreditsFulfilled()
                + " out of "
                + credits.get().getCreditsRequired() + " MCs completed";
        } else {
            creditsCountString = "- MCs";
        }

        System.out.println("CAPP!!");
        System.out.println(courseInfo.getCap());
        creditsCount.setText(creditsCountString);
    }

    /**
     * Returns the string to be displayed as progress.
     */
    public String countFormat(int completed, int total) {
        if (total == 0) {
            return "-/-";
        } else {
            return completed + "/" + total;
        }
    }

    /**
     * Returns the decimal formatted string required for progress display.
     */
    public String decimalFormat(double value) {
        return String.format("%.2f", value);
    }
}
