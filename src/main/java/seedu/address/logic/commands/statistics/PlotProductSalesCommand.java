package seedu.address.logic.commands.statistics;

import static seedu.address.model.transaction.DateTime.populateDates;

import javafx.scene.chart.XYChart;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.DateTimeInRangePredicate;
import seedu.address.model.transaction.JointTransactionPredicate;
import seedu.address.model.transaction.ProductIdEqualsPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Quantity;

import java.util.ArrayList;
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
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    public PlotProductSalesCommand(Index targetIndex, DateTime startDateTime, DateTime endDateTime) {
        this.targetIndex = targetIndex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Predicate<Transaction>> predicates = new ArrayList<>();
        List<Product> lastShownList = model.getFilteredProductList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToPlot = lastShownList.get(targetIndex.getZeroBased());

        predicates.add(new ProductIdEqualsPredicate(productToPlot.getId()));
        predicates.add(new DateTimeInRangePredicate(startDateTime, endDateTime));
        Predicate<Transaction> jointPredicate = new JointTransactionPredicate(predicates);
        List<Transaction> transactions =  model.filterTransaction(jointPredicate);

        XYChart.Series dataSeries = generateDataSeries(transactions);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, productToPlot.getDescription()),
                dataSeries, false, true, false);
    }

    private XYChart.Series generateDataSeries(List<Transaction> transactions) {
        XYChart.Series dataSeries = new XYChart.Series();

        List<DateTime> dateTimes = populateDates(startDateTime, endDateTime);
        dateTimes.forEach(date -> {
            Quantity sales = new Quantity(0);
            for (Transaction t: transactions) {
                if (t.getDateTime().isOnSameDay(date)) {
                    sales = sales.plus(t.getQuantity());
                }
            }

            dataSeries.getData().add(new XYChart.Data(date.toDateString(), sales.value));
        });

        return dataSeries;
    }

}
