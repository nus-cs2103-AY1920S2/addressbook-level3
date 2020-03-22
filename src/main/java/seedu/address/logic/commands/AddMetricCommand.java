package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.MetricList;

public class AddMetricCommand extends AddCommand {
    public static final String COMMAND_WORD = "metric";
    public static final String MESSAGE_SUCCESS = "New metric added: %1$s";
    public static final String MESSAGE_USAGE = "new " + COMMAND_WORD + ": Adds an metric to the Metric list.\n"
            + "Parameters: "
            + "NAME -a attributePrefix1 -w weight1 -a attributePrefix2 -w weight2\n"
            + "Example: new " + COMMAND_WORD + " extremeLeader -a lea -w 0.7 -a te -w 0.6";


    private final String toAdd;
    private final ArrayList<String> attributePrefixes;
    private final ArrayList<Double> addedWeights;

    /**
     * Creates an AddMetricCommand to add the specified {@code Metric}
     */

    public AddMetricCommand(String toAdd, ArrayList<String> attributePrefixes, ArrayList<Double> addedWeights) {
        this.toAdd = toAdd;
        this.attributePrefixes = attributePrefixes;
        this.addedWeights = addedWeights;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MetricList metrics = model.getMetricList();
        AttributeList attributes = model.getAttributeList();

        try {
            metrics.add(toAdd, attributes, attributePrefixes, addedWeights);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
