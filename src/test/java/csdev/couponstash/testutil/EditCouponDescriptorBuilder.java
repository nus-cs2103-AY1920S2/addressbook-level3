package csdev.couponstash.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.commands.EditCommand.EditCouponDescriptor;
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
 * A utility class to help with building EditCouponDescriptor objects.
 */
public class EditCouponDescriptorBuilder {

    private EditCommand.EditCouponDescriptor descriptor;

    public EditCouponDescriptorBuilder() {
        descriptor = new EditCouponDescriptor();
    }

    public EditCouponDescriptorBuilder(EditCouponDescriptor descriptor) {
        this.descriptor = new EditCouponDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCouponDescriptor} with fields containing {@code coupon}'s details
     */
    public EditCouponDescriptorBuilder(Coupon coupon) {
        descriptor = new EditCouponDescriptor();
        descriptor.setName(coupon.getName());
        descriptor.setPromoCode(coupon.getPromoCode());
        descriptor.setSavings(coupon.getSavingsForEachUse());
        descriptor.setExpiryDate(coupon.getExpiryDate());
        descriptor.setStartDate(coupon.getStartDate());
        descriptor.setLimit(coupon.getLimit());
        descriptor.setTags(coupon.getTags());
        descriptor.setRemindDate(coupon.getRemindDate());
        descriptor.setCondition(coupon.getCondition());
    }

    /**
     * Sets the {@code Name} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code PromoCode} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withPromoCode(String promoCode) {
        descriptor.setPromoCode(new PromoCode(promoCode));
        return this;
    }

    /**
     * Sets the {@code Savings} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withSavings(Savings sv) {
        descriptor.setSavings(sv);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withExpiryDate(String expiryDate) {
        descriptor.setExpiryDate(new ExpiryDate(expiryDate));
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new StartDate(startDate));
        return this;
    }

    /**
     * Sets the {@code Usage} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withUsage(int usage) {
        descriptor.setUsage(new Usage(usage));
        return this;
    }

    /**
     * Sets the {@code Limit} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withLimit(int limit) {
        descriptor.setLimit(new Limit(limit));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCouponDescriptor}
     * that we are building.
     */
    public EditCouponDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withRemindDate(String remindDate) {
        descriptor.setRemindDate(new RemindDate(remindDate));
        return this;
    }

    /**
     * Sets the {@code Condition} of the {@code EditCouponDescriptor} that we are building.
     */
    public EditCouponDescriptorBuilder withCondition(String condition) {
        descriptor.setCondition(new Condition(condition));
        return this;
    }

    public EditCouponDescriptor build() {
        return descriptor;
    }
}
