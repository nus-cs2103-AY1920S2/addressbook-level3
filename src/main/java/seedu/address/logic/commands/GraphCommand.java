package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AXIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.UniqueExerciseList;
import seedu.address.model.graph.Graph;

/**
 * Displays a graph of exercises done by a client in FitBiz.
 */
public class GraphCommand extends Command {

    public static final List<Prefix> PREFIXES = List.of(PREFIX_EXERCISE_NAME, PREFIX_AXIS,
            PREFIX_STARTDATE, PREFIX_ENDDATE);

    public static final String COMMAND_WORD = "graph";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the graph of a specified exercise "
            + "of the client currently in view. "
            + "Start date cannot be after end date. \n"
            + "Parameters: "
            + PREFIX_EXERCISE_NAME + "EXERCISE_NAME "
            + PREFIX_AXIS + "Y_AXIS "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_ENDDATE + "END_DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXERCISE_NAME + "push up "
            + PREFIX_AXIS + "reps "
            + PREFIX_STARTDATE + "01-01-2020 "
            + PREFIX_ENDDATE + "01-04-2020";

    public static final String MESSAGE_SUCCESS = "Showing exercise graph for %1$s's %2$s from %3$s to %4$s. \n"
            + "Note that any new updates to the exercises (via add-e, edit-e, delete-e) will not be reflected "
            + "on the current graph.";

    public static final String MESSAGE_CLIENT_NOT_IN_VIEW = "You currently do not have a client in view, "
        + "use the view-c command to view a client first";

    public static final String MESSAGE_EXERCISE_NOT_IN_LIST = "Graph cannot be plotted as no such exercise is"
            + " recorded for this client from %1$s to %2$s.";

    public static final String MESSAGE_NO_GRAPH_FOR_AXIS = "There is no graph to plot for this axis specified. "
            + "Please choose a different axis or a different exercise.";


    private final Graph graph;

    public GraphCommand(Graph graph) {
        requireNonNull(graph);
        this.graph = graph;
    }

    public static boolean isValidTimeFrame(LocalDate startDate, LocalDate endDate) {
        return (endDate.compareTo(startDate) >= 0);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasClientInView()) {
            throw new CommandException(MESSAGE_CLIENT_NOT_IN_VIEW);
        }

        Client clientInView = model.getClientInView();
        UniqueExerciseList clientInViewExerciseList = clientInView.getExerciseList();

        if (!clientInViewExerciseList.containsNameWithinDate(graph.getExerciseName(),
                graph.getStartDate(), graph.getEndDate())) {
            throw new CommandException(String.format(MESSAGE_EXERCISE_NOT_IN_LIST,
                    graph.getStartDate(), graph.getEndDate()));
        }

        List<Exercise> graphList = graph.generateGraphList(clientInView);

        if (graphList.size() == 0) {
            throw new CommandException(MESSAGE_NO_GRAPH_FOR_AXIS);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, clientInView.getName().toString(),
            graph.getExerciseName().toString(), graph.getStartDate().toString(), graph.getEndDate().toString()),
            graph.getAxis().getAxisType(), graphList);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GraphCommand)) {
            return false;
        }

        // state check
        GraphCommand e = (GraphCommand) other;
        return graph.equals(e.graph);
    }

    @Override
    public String toString() {
        return graph.getExerciseName().value
            + " " + graph.getAxis().value
            + " " + graph.getStartDate().toString()
            + " " + graph.getEndDate().toString();
    }
}
