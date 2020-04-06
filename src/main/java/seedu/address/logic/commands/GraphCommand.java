package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AXIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.graph.Graph;

/**
 * Displays a graph of exercises done by a client in FitBiz.
 */
public class GraphCommand extends Command {

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

    public static final String MESSAGE_SUCCESS = "Graph displayed. Currently displaying graph for exercise:\n%1$s";

    public static final String MESSAGE_CLIENT_NOT_IN_VIEW = "You currently do not have a client in view, "
        + "use the view-c command to view a client first";

    public static final String MESSAGE_EXERCISE_NOT_IN_PERSONAL_BEST =
        "This exercise does not have a personal best recorded";

    private final Graph graph;

    public GraphCommand(Graph graph) {
        requireNonNull(graph);
        this.graph = graph;
    }

    public static boolean isValidTimeFrame(String startDate, String endDate) {
        return (LocalDate.parse(endDate).compareTo(LocalDate.parse(startDate)) >= 0);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasClientInView()) {
            throw new CommandException(MESSAGE_CLIENT_NOT_IN_VIEW);
        }

        Client clientInView = model.getClientInView();

        List<Exercise> graphList = graph.generateGraphList(clientInView);
        // TODO: send graphList to gui :D

        return new CommandResult(graphList.toString());
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
}
