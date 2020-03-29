package seedu.address.logic.commands.statistics;

import javafx.scene.chart.XYChart;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.ProductIdEqualsPredicate;
import seedu.address.model.transaction.Transaction;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class PlotProductSalesCommand extends Command {
    public static final String COMMAND_WORD = "plotsales";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Plot the sales of product to the screen"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Sales for product %1$s plotted.";

    private final Index targetIndex;

    public PlotProductSalesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToPlot = lastShownList.get(targetIndex.getZeroBased());

        Predicate<Transaction> predicate = new ProductIdEqualsPredicate(productToPlot.getId());

        List<Transaction> transactions =  model.filterTransaction(predicate);

        XYChart.Series dataSeries = new XYChart.Series();

        transactions.forEach(t -> dataSeries.getData().add(t.toData()));

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, productToPlot.getDescription()),
                dataSeries, false, true, false);
    }

}
