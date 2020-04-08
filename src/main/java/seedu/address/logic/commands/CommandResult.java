package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.scene.chart.XYChart;
import seedu.address.model.product.Product;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final XYChart.Series dataSeries;

    private final Product product;

    /** Title of the plot. */
    private final String title;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Notification window should be displayed to the user. */
    private final boolean showNotification;

    /** Plot should be displayed to the user */
    private final boolean showPlot;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, XYChart.Series dataSeries, Product product,
                         String title, boolean showHelp, boolean showNotification, boolean showPlot, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.dataSeries = dataSeries;
        this.product = product;
        this.title = title;
        this.showHelp = showHelp;
        this.showNotification = showNotification;
        this.showPlot = showPlot;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, null, "", false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public XYChart.Series getDataSeries() {
        return dataSeries;
    }

    public String getTitle() {
        return title;
    }

    public Product getNotificationData() {
        return product;
    }

    public boolean isShowPlot() {
        return showPlot;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }
}
