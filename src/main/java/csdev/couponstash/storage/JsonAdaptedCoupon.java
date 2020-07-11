package csdev.couponstash.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.commons.util.DateUtil;
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
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;
/**
 * Jackson-friendly version of {@link Coupon}.
 */
class JsonAdaptedCoupon {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Coupon's %s field is missing!";

    private final String name;
    private final String promoCode;
    private final JsonAdaptedSavings savingsPerUse;
    private final String expiryDate;
    private final String startDate;
    private final String usage;
    private final String limit;
    private final String remindDate;
    private final String archived;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final JsonAdaptedDssm totalSaved;
    private final String condition;

    /**
     * Constructs a {@code JsonAdaptedCoupon} with the given coupon details.
     */
    @JsonCreator
    public JsonAdaptedCoupon(@JsonProperty("name") String name,
                             @JsonProperty("promoCode") String promoCode,
                             @JsonProperty("savingsPerUse") JsonAdaptedSavings savingsPerUse,
                             @JsonProperty("expiry date") String expiryDate,
                             @JsonProperty("start date") String startDate,
                             @JsonProperty("usage") String usage,
                             @JsonProperty("limit") String limit,
                             @JsonProperty("totalSaved") JsonAdaptedDssm totalSaved,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("remind date") String remindDate,
                             @JsonProperty("condition") String condition,
                             @JsonProperty("archived") String archived
    ) {
        this.name = name;
        this.promoCode = promoCode;
        this.savingsPerUse = savingsPerUse;
        this.expiryDate = expiryDate;
        this.startDate = startDate;
        this.usage = usage;
        this.limit = limit;
        this.totalSaved = totalSaved;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.remindDate = remindDate;
        this.condition = condition;
        this.archived = archived;
    }

    /**
     * Converts a given {@code Coupon} into this class for Jackson use.
     */
    public JsonAdaptedCoupon(Coupon source) {
        name = source.getName().fullName;
        promoCode = source.getPromoCode().value;
        savingsPerUse = new JsonAdaptedSavings(source.getSavingsForEachUse());
        expiryDate = source.getExpiryDate().value;
        startDate = source.getStartDate().value;
        usage = source.getUsage().toString();
        limit = source.getLimit().toString();
        totalSaved = new JsonAdaptedDssm(source.getSavingsMap());
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        remindDate = source.getRemindDate().toString();
        condition = source.getCondition().value;
        archived = source.getArchived().toString();
    }

    /**
     * Converts this Jackson-friendly adapted coupon object into the model's {@code Coupon} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted coupon.
     */
    public Coupon toModelType() throws IllegalValueException {
        final List<Tag> couponTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            couponTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (promoCode == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PromoCode.class.getSimpleName()));
        }
        final PromoCode modelPromoCode = new PromoCode(promoCode);

        if (savingsPerUse == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName()));
        }
        final Savings modelSavings = savingsPerUse.toModelType();

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpiryDate.class.getSimpleName()));
        }
        if (!DateUtil.isValidDate(expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }
        if (!DateUtil.isValidDate(startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }
        final StartDate modelStartDate = new StartDate(startDate);

        if (usage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Usage.class.getSimpleName()));
        }
        if (!Usage.isValidUsage(usage)) {
            throw new IllegalValueException(Usage.MESSAGE_CONSTRAINTS);
        }
        final Usage modelUsage = new Usage(usage);

        if (limit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Limit.class.getSimpleName()));
        }
        if (!Limit.isValidLimit(limit)) {
            throw new IllegalValueException(Limit.MESSAGE_CONSTRAINTS);
        }
        final Limit modelLimit = new Limit(limit);

        if (condition == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Condition.class.getSimpleName()));
        }
        final Condition modelCondition = new Condition(condition);

        if (!Archived.isValidState(archived)) {
            throw new IllegalValueException(Archived.MESSAGE_CONSTRAINTS);
        }
        final Archived modelArchived = new Archived(Boolean.parseBoolean(archived));

        final DateSavingsSumMap modelTotalSaved;
        if (totalSaved == null) {
            // could not find totalSaved data
            // just set total savings to nothing
            modelTotalSaved = new DateSavingsSumMap();
        } else {
            modelTotalSaved = totalSaved.toModelType();
        }

        if (!DateUtil.isValidDate(remindDate)) {
            throw new IllegalValueException(RemindDate.MESSAGE_CONSTRAINTS);
        }
        final RemindDate modelRemindDate = new RemindDate(remindDate);

        final Set<Tag> modelTags = new HashSet<>(couponTags);

        return new Coupon(modelName, modelPromoCode, modelSavings,
                modelExpiryDate, modelStartDate, modelUsage, modelLimit,
                modelTags, modelTotalSaved, modelRemindDate, modelCondition,
                modelArchived);
    }
}
