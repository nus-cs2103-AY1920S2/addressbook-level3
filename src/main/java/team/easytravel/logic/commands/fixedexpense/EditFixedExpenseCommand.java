package team.easytravel.logic.commands.fixedexpense;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_FIXED_EXPENSE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.Description;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpenseCategory;
import team.easytravel.model.trip.TripManager;

/**
 * Edits the details of an existing fixed expense.
 */
public class EditFixedExpenseCommand extends Command {

    public static final String COMMAND_WORD = "editexpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the fixed expense identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CURRENCY + "CURRENCY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "3000 "
            + PREFIX_CURRENCY + "sgd "
            + PREFIX_DESCRIPTION + "SQ Flight "
            + PREFIX_CATEGORY + "transport";

    public static final String MESSAGE_EDIT_FIXEDEXPENSE_SUCCESS = "Edited Fixed Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FIXED_EXPENSE = "This Fixed Expense already exists in the "
            + "Fixed Expense List.";
    public static final String MESSAGE_CURRENCY_NOT_PRESENT = "A currency field must be included when editing amount!";
    public static final String MESSAGE_INVALID_CURRENCY = "Currency must be either sgd or other";

    private final Index index;
    private final EditFixedExpenseDescriptor editFixedExpenseDescriptor;
    private final boolean isOverseasAmount;

    /**
     * @param index                      of the person in the filtered person list to edit
     * @param editFixedExpenseDescriptor details to edit the fixed expense with
     */
    public EditFixedExpenseCommand(Index index, EditFixedExpenseDescriptor editFixedExpenseDescriptor,
                                   boolean exchangeRate) {
        requireNonNull(index);
        requireNonNull(editFixedExpenseDescriptor);

        this.index = index;
        this.editFixedExpenseDescriptor = new EditFixedExpenseDescriptor(editFixedExpenseDescriptor);
        this.isOverseasAmount = exchangeRate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<FixedExpense> lastShownList = model.getFilteredFixedExpenseList();
        Double exchangeRate = model.getExchangeRate();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "fixed expense"));
        }

        FixedExpense fixedExpenseToEdit = lastShownList.get(index.getZeroBased());
        FixedExpense editedExpense = createEditedFixedExpense(fixedExpenseToEdit, editFixedExpenseDescriptor,
                isOverseasAmount, exchangeRate);

        if (!fixedExpenseToEdit.isSame(editedExpense) && model.hasFixedExpense(editedExpense)) {
            throw new CommandException(MESSAGE_DUPLICATE_FIXED_EXPENSE);
        }

        model.setFixedExpense(fixedExpenseToEdit, editedExpense);
        model.updateFilteredFixedExpenseList(Model.PREDICATE_SHOW_ALL_FIXED_EXPENSES);
        int currentBudget = model.getBudget();
        int newBudget = (int) (currentBudget - Double.parseDouble(editedExpense.getAmount().value));
        return new CommandResult(String.format(MESSAGE_EDIT_FIXEDEXPENSE_SUCCESS, editedExpense + "\n"
                + "Your current budget left is " + newBudget), SWITCH_TAB_FIXED_EXPENSE);
    }

    /**
     * Creates and returns a {@code FixedExpense} with the details of {@code fixedExpenseToEdit}
     * edited with {@code editFixedExpenseDescriptor}
     */
    private static FixedExpense createEditedFixedExpense(FixedExpense fixedExpenseToEdit,
                                                         EditFixedExpenseDescriptor editFixedExpenseDescriptor,
                                                         boolean isOverseasAmount,
                                                         Double exchangeRate) {
        assert fixedExpenseToEdit != null;

        Amount updatedAmount;

        if (isOverseasAmount) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            updatedAmount =
                    new Amount(decimalFormat.format(Double.parseDouble(editFixedExpenseDescriptor.getAmount()
                            .orElse(fixedExpenseToEdit.getAmount()).value) * exchangeRate));
        } else {
            updatedAmount = editFixedExpenseDescriptor.getAmount().orElse(fixedExpenseToEdit.getAmount());
        }

        Description updatedDescription = editFixedExpenseDescriptor
                .getDescription().orElse(fixedExpenseToEdit.getDescription());
        FixedExpenseCategory updatedFixedExpenseCategory = editFixedExpenseDescriptor.getFixedExpenseCategory()
                .orElse(fixedExpenseToEdit.getFixedExpenseCategory());

        return new FixedExpense(updatedAmount, updatedDescription, updatedFixedExpenseCategory);
    }

    /**
     * Stores the details to edit the fixed expense with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditFixedExpenseDescriptor {
        private Amount amount;
        private Description description;
        private FixedExpenseCategory fixedExpenseCategory;

        public EditFixedExpenseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFixedExpenseDescriptor(EditFixedExpenseDescriptor toCopy) {
            setAmount(toCopy.amount);
            setDescription(toCopy.description);
            setFixedExpenseCategory(toCopy.fixedExpenseCategory);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, description, fixedExpenseCategory);
        }

        public void setAmount(Amount amount, boolean isOverseasCurrency) {
            this.amount = amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setFixedExpenseCategory(FixedExpenseCategory fixedExpenseCategory) {
            this.fixedExpenseCategory = fixedExpenseCategory;
        }


        public Optional<FixedExpenseCategory> getFixedExpenseCategory() {
            return Optional.ofNullable(fixedExpenseCategory);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFixedExpenseDescriptor)) {
                return false;
            }

            // state check
            EditFixedExpenseDescriptor e = (EditFixedExpenseDescriptor) other;

            return getAmount().equals(e.getAmount())
                    && getDescription().equals(e.getDescription())
                    && getFixedExpenseCategory().equals(e.getFixedExpenseCategory());
        }
    }
}
