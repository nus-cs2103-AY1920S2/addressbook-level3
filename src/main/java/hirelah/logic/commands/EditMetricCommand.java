package hirelah.logic.commands;

import static hirelah.commons.util.ModelUtil.validateFinalisation;
import static hirelah.logic.util.CommandUtil.saveMetrics;
import static java.util.Objects.requireNonNull;

import java.util.List;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.MetricList;
import hirelah.storage.Storage;

/**
 * EditMetricCommand describes the behavior of HireLah!
 * when a user wants to edit a metric.
 */
public class EditMetricCommand extends Command {
    public static final String COMMAND_WORD = "metric";
    public static final boolean DESIRED_MODEL_FINALIZED_STATE = true;
    public static final String MESSAGE_FORMAT = "edit " + COMMAND_WORD + "<metric> [-n <metric name>] [-a <attribute>"
            + " -w <score>]...";
    public static final String MESSAGE_FUNCTION = ": Edits the metric.\n";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + MESSAGE_FUNCTION
            + "Example: edit " + COMMAND_WORD + " extremeLeadership -n extremeDictatorship -a leadership -w 100";

    public static final String MESSAGE_EDIT_METRIC_SUCCESS = "Edited metric: %s";

    private final String toEdit;
    private final String updatedName;
    private final List<String> attributePrefixes;
    private final List<Double> weightages;

    public EditMetricCommand(String toEdit, String updatedName, List<String> attributePrefixes,
                             List<Double> weightages) {
        this.toEdit = toEdit;
        this.updatedName = updatedName;
        this.attributePrefixes = attributePrefixes;
        this.weightages = weightages;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        validateFinalisation(model, DESIRED_MODEL_FINALIZED_STATE);
        MetricList metrics = model.getMetricList();
        AttributeList attributes = model.getAttributeList();

        try {
            String name = metrics.edit(toEdit, updatedName, attributes, attributePrefixes, weightages);
            saveMetrics(model, storage);
            return new ToggleCommandResult(
                    String.format(MESSAGE_EDIT_METRIC_SUCCESS, name), ToggleView.METRIC);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditMetricCommand // instanceof handles nulls
                && toEdit.equals(((EditMetricCommand) other).toEdit)
                && updatedName.equals(((EditMetricCommand) other).updatedName)
                && attributePrefixes.equals(((EditMetricCommand) other).attributePrefixes)
                && weightages.equals(((EditMetricCommand) other).weightages));
    }
}
