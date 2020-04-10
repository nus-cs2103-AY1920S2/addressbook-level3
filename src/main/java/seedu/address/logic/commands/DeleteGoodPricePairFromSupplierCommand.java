package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;

/**
 * delete entries of good price pair from supplier's good list given the good name which is identified by keywords
 */
public class DeleteGoodPricePairFromSupplierCommand extends Command {

    public static final String COMMAND_WORD = "delete-gp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entry / entries of good price pair(s) in the supplier's good list given a good's or goods' "
            + "name(s) which is / are identified by keyword(s) \n"
            + "Parameters: INDEX (must be a positive integer),"
            + " a good's or goods' name(s) (g/good name)\n"
            + "Example: " + COMMAND_WORD + " 1" + " g/apple" + " g/orange (to delete two different goods)";

    public static final String MESSAGE_DELETE_GOOD = "Successfuly deleted goods from supplier's list: ";
    public static final String MESSAGE_COULD_NOT_FIND_GOOD = "COULD NOT FIND GOODS: ";
    public static final String MESSAGE_MUST_INCLUDE_GOODNAME = "MUST INCLUDE AT LEAST ONE GOOD NAME";

    private static String errorMessage = null;
    private static String successMessage = null;

    private final Index index;
    private final DeleteSupplierGoodName deleteSupplierGoodName;

    public DeleteGoodPricePairFromSupplierCommand(Index index, DeleteSupplierGoodName deleteSupplierGoodName) {
        requireNonNull(index);

        this.index = index;
        this.deleteSupplierGoodName =
                new DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName(deleteSupplierGoodName);
    }

    public DeleteGoodPricePairFromSupplierCommand(Index indexFirstSupplier) {
        this.index = indexFirstSupplier;
        deleteSupplierGoodName = new DeleteSupplierGoodName();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierWhoHasGoodToDelete = lastShownList.get(index.getZeroBased());
        Supplier editedSupplier = createEditedSupplier(supplierWhoHasGoodToDelete,
                deleteSupplierGoodName.goodNames.iterator().next());

        //if (errorMessage != null) {
           // return new CommandResult(String.format(errorMessage + "/n" + successMessage, supplierWhoHasGoodToDelete));
        //}

        model.setSupplier(supplierWhoHasGoodToDelete, editedSupplier);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        model.commit();

        return new CommandResult(String.format(successMessage, supplierWhoHasGoodToDelete));
    }

    /**
     * Creates and returns a {@code Supplier} with the details of {@code supplierWhoHasGoodToDelete}
     * edited with {@code goodName}.
     */
    private static Supplier createEditedSupplier(Supplier supplierWhoHasGoodToDelete, GoodName goodName) {
        assert supplierWhoHasGoodToDelete != null;

        Name updatedName = supplierWhoHasGoodToDelete.getName();
        Phone updatedPhone = supplierWhoHasGoodToDelete.getPhone();
        Email updatedEmail = supplierWhoHasGoodToDelete.getEmail();
        Address updatedAddress = supplierWhoHasGoodToDelete.getAddress();

        Set<Offer> supplierToEditOffer = new HashSet<>(supplierWhoHasGoodToDelete.getOffers());

        /*for (Offer tempOffer : supplierToEditOffer) {
            if (tempOffer.getGoodName().equals(goodName)) {
                supplierToEditOffer.remove(tempOffer);
                successMessage = MESSAGE_DELETE_GOOD + tempOffer.getGoodName();
            }
        }

        if (successMessage == null) {
            errorMessage = MESSAGE_COULD_NOT_FIND_GOOD + goodName;
        }*/
        supplierToEditOffer.removeIf(tempOffer -> tempOffer.getGoodName().equals(goodName));

        return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress, supplierToEditOffer);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoodPricePairFromSupplierCommand // instanceof handles nulls
                && index.equals(((DeleteGoodPricePairFromSupplierCommand) other).index)); // state check
    }

    /**
     * Stores the details to edit the supplier with. Each non-empty field value will replace the
     * corresponding field value of the supplier.
     */
    public static class DeleteSupplierGoodName {
        private Set<GoodName> goodNames;

        public DeleteSupplierGoodName() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code goods} is used internally.
         */
        public DeleteSupplierGoodName(DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName toCopy) {
            setGoodNames(toCopy.goodNames);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(goodNames);
        }

        /**
         * Sets {@code goods} to this object's {@code goods}.
         * A defensive copy of {@code goods} is used internally.
         */
        public void setGoodNames(Set<GoodName> goodNames) {
            this.goodNames = (goodNames != null) ? new HashSet<>(goodNames) : null;
        }

        /**
         * Returns an unmodifiable good set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code goods} is null.
         */
        public Optional<Set<GoodName>> getGoodNames() {
            return (goodNames != null) ? Optional.of(Collections.unmodifiableSet(goodNames)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName)) {
                return false;
            }

            // state check
            DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName e =
                    (DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName) other;

            return getGoodNames().equals(e.getGoodNames());
        }
    }
}
