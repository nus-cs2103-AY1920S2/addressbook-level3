package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.util.CollectionUtil;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.CliSyntax;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Archived;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.RemindDate;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Edits the details of an existing coupon in the CouponStash.
 */
public class EditCommand extends IndexedCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the coupon identified "
            + "by the index number used in the displayed coupon list. "
            + "Existing values will be overwritten by the input values.\n\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_PROMO_CODE + "PROMO_CODE] "
            + "[" + CliSyntax.PREFIX_SAVINGS + "SAVINGS] "
            + "[" + CliSyntax.PREFIX_EXPIRY_DATE + "EXPIRY_DATE] "
            + "[" + CliSyntax.PREFIX_START_DATE + "START_DATE] "
            + "[" + CliSyntax.PREFIX_LIMIT + "LIMIT "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_PROMO_CODE + "ILOVESTASH";

    public static final String MESSAGE_EDIT_COUPON_SUCCESS = "Edited Coupon: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_COUPON = "This coupon already exists in the CouponStash.";
    public static final String MESSAGE_CANNOT_EDIT_USAGE = "The usage of the coupon cannot be edited, "
            + "due to changes in the concrete savings.";
    public static final String MESSAGE_LIMIT_LESS_THAN_USAGE = "The new limit of the coupon cannot be less than "
            + "the current usage (%s) of the coupon.";
    private final EditCouponDescriptor editCouponDescriptor;

    /**
     * @param index of the coupon in the filtered coupon list to edit
     * @param editCouponDescriptor details to edit the coupon with
     */
    public EditCommand(Index index, EditCouponDescriptor editCouponDescriptor) {
        super(index);

        requireNonNull(editCouponDescriptor);

        this.editCouponDescriptor = new EditCouponDescriptor(editCouponDescriptor);
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToEdit = lastShownList.get(targetIndex.getZeroBased());
        Coupon editedCoupon = createEditedCoupon(couponToEdit, editCouponDescriptor);

        if (!couponToEdit.isSameCoupon(editedCoupon) && model.hasCoupon(editedCoupon)) {
            throw new CommandException(MESSAGE_DUPLICATE_COUPON);
        }

        if (!couponToEdit.getExpiryDate().isAfterOrEqual(editedCoupon.getStartDate())) {
            throw new CommandException(StartDate.MESSAGE_CONSTRAINTS);
        }

        if (!editedCoupon.getExpiryDate().isAfterOrEqual(couponToEdit.getStartDate())) {
            throw new CommandException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }

        Usage currentUsage = couponToEdit.getUsage();
        if (Usage.isUsageGreaterThanLimit(currentUsage, editedCoupon.getLimit())) {
            throw new CommandException(String.format(MESSAGE_LIMIT_LESS_THAN_USAGE, currentUsage));
        }

        model.setCoupon(couponToEdit, editedCoupon, commandText);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);
        return new CommandResult(String.format(MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName()));
    }

    /**
     * Creates and returns a {@code Coupon} with the details of {@code couponToEdit}
     * edited with {@code editCouponDescriptor}.
     */
    private static Coupon createEditedCoupon(Coupon couponToEdit, EditCouponDescriptor editCouponDescriptor) {
        assert couponToEdit != null;

        Name updatedName = editCouponDescriptor.getName().orElse(couponToEdit.getName());
        PromoCode updatedPromoCode = editCouponDescriptor.getPromoCode().orElse(couponToEdit.getPromoCode());
        Savings updatedSavings = editCouponDescriptor.getSavings().orElse(couponToEdit.getSavingsForEachUse());
        ExpiryDate updatedExpiryDate = editCouponDescriptor.getExpiryDate().orElse(couponToEdit.getExpiryDate());
        StartDate updatedStartDate = editCouponDescriptor.getStartDate().orElse(couponToEdit.getStartDate());
        Limit updatedLimit = editCouponDescriptor.getLimit().orElse(couponToEdit.getLimit());
        Set<Tag> updatedTags = editCouponDescriptor.getTags().orElse(couponToEdit.getTags());
        Condition updatedCondition = editCouponDescriptor.getCondition().orElse(couponToEdit.getCondition());
        Archived archived = editCouponDescriptor.getUsage().isPresent() || editCouponDescriptor.getLimit().isPresent()
                ? new Archived(String.valueOf(Usage.isUsageAtLimit(couponToEdit.getUsage(), updatedLimit)))
                : couponToEdit.getArchived();
        RemindDate remindDate = editCouponDescriptor.getExpiryDate().isPresent()
                ? new RemindDate(updatedExpiryDate)
                : editCouponDescriptor.getRemindDate().orElse(couponToEdit.getRemindDate());

        return new Coupon(updatedName, updatedPromoCode, updatedSavings, updatedExpiryDate, updatedStartDate,
                // avoid changing the usage
                couponToEdit.getUsage(),
                updatedLimit, updatedTags,
                // avoid changing the total savings and dates mappings
                couponToEdit.getSavingsMap(),
                // avoid changing the reminder
                remindDate,
                updatedCondition,
                // avoid changing the archival state
                archived);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editCouponDescriptor.equals(e.editCouponDescriptor);
    }

    /**
     * Stores the details to edit the coupon with. Each non-empty field value will replace the
     * corresponding field value of the coupon.
     */
    public static class EditCouponDescriptor {
        private Name name;
        private PromoCode promoCode;
        private Savings savings;
        private ExpiryDate expiryDate;
        private StartDate startDate;
        private Usage usage;
        private Limit limit;
        private Set<Tag> tags;
        private RemindDate remindDate;
        private Condition condition;
        private Archived archived;

        public EditCouponDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCouponDescriptor(EditCouponDescriptor toCopy) {
            setName(toCopy.name);
            setPromoCode(toCopy.promoCode);
            setSavings(toCopy.savings);
            setExpiryDate(toCopy.expiryDate);
            setStartDate(toCopy.startDate);
            setUsage(toCopy.usage);
            setLimit(toCopy.limit);
            setTags(toCopy.tags);
            setRemindDate(toCopy.remindDate);
            setCondition(toCopy.condition);
            setArchived(toCopy.archived);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, promoCode, savings, expiryDate, startDate, usage, limit, tags,
                    remindDate, condition, archived);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPromoCode(PromoCode promoCode) {
            this.promoCode = promoCode;
        }

        public Optional<PromoCode> getPromoCode() {
            return Optional.ofNullable(promoCode);
        }

        /**
         * Sets the Savings field in this EditCouponDescriptor.
         * @param sv Savings to be set in EditCouponDescriptor.
         */
        public void setSavings(Savings sv) {
            this.savings = sv;
        }

        /**
         * Gets the Savings that have been set in this
         * EditCouponDescriptor in an Optional (if it
         * was never set, an Optional.empty() is returned).
         * @return Optional with Savings representing the
         *     Savings value stored in this
         *     EditCouponDescriptor, if any.
         */
        public Optional<Savings> getSavings() {
            return Optional.ofNullable(this.savings);
        }

        public void setExpiryDate(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        public void setStartDate(StartDate startDate) {
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setUsage(Usage usage) {
            this.usage = usage;
        }

        public Optional<Usage> getUsage() {
            return Optional.ofNullable(usage);
        }

        public void setLimit(Limit limit) {
            this.limit = limit;
        }

        public Optional<Limit> getLimit() {
            return Optional.ofNullable(limit);
        }

        public Optional<RemindDate> getRemindDate() {
            return Optional.ofNullable(this.remindDate);
        }

        public void setRemindDate(RemindDate remindDate) {
            this.remindDate = remindDate;
        }


        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public Optional<Condition> getCondition() {
            return Optional.ofNullable(condition);
        }

        public Optional<Archived> getArchived() {
            return Optional.ofNullable(this.archived);
        }

        public void setArchived(Archived archived) {
            this.archived = archived;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCouponDescriptor)) {
                return false;
            }

            // state check
            EditCouponDescriptor e = (EditCouponDescriptor) other;

            return getName().equals(e.getName())
                    && getPromoCode().equals(e.getPromoCode())
                    && getSavings().equals(e.getSavings())
                    && getExpiryDate().equals(e.getExpiryDate())
                    && getStartDate().equals(e.getStartDate())
                    && getUsage().equals(e.getUsage())
                    && getLimit().equals(e.getLimit())
                    && getTags().equals(e.getTags())
                    && getRemindDate().equals(e.getRemindDate())
                    && getCondition().equals(e.getCondition())
                    && getArchived().equals(e.getArchived());
        }
    }
}
