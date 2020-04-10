package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.exercise.Exercise;
import seedu.address.model.graph.AxisType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static final List<Exercise> NO_GRAPH_LIST = new ArrayList<Exercise>();
    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    private final boolean openGraph;
    private final List<Exercise> exerciseGraphList;
    private final AxisType axisType;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;

        this.openGraph = false;
        this.exerciseGraphList = NO_GRAPH_LIST;
        this.axisType = AxisType.NA;

        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, AxisType axisType, List<Exercise> exerciseGraphList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;

        this.openGraph = true;
        this.exerciseGraphList = exerciseGraphList;
        this.axisType = axisType;

        this.exit = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isOpenGraph() {
        return openGraph;
    }

    public List<Exercise> getGraphList() {
        return exerciseGraphList;
    }

    public AxisType getAxisType() {
        return axisType;
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
