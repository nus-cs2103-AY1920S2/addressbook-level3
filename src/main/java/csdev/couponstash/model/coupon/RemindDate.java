package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Coupon's remind date in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemindDate(String, String)}
 */
public class RemindDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Remind Dates should be a date in the D-M-YYYY format and not after the expiry date";
    public static final String VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");
    private LocalDate date;
    private boolean remindFlag;
    private String value;

    /**
     * Constructs a {@code RemindDate}.
     *
     * @param remindDate A valid remind date.
     */
    public RemindDate(String remindDate, String expiryDate) {
        if (remindDate.equals("")) {
            date = null;
            remindFlag = false;
            value = "";
        }
        requireNonNull(remindDate, expiryDate);
        checkArgument(isValidRemindDate(remindDate, expiryDate), MESSAGE_CONSTRAINTS);
        value = remindDate;
        this.date = getDate();
        remindFlag = true;
    }

    public RemindDate() {
        value = "";
        date = null;
        remindFlag = false;
    }

    public RemindDate(ExpiryDate expiryDate) {
        value = expiryDate.value;
        date = getDate().minusDays(3);
        setRemindDate(date);
    }
    /**
     * Returns true if a given string is a valid remind date.
     */
    public static boolean isValidRemindDate(String remindTest, String expiryTest) {
        LocalDate remindDate = LocalDate.parse(remindTest, DATE_FORMATTER);
        LocalDate expiryDate = LocalDate.parse(expiryTest, DATE_FORMATTER);
        LocalDate testYesterday = LocalDate.now();
        testYesterday.minusDays(1);

        return remindTest.matches(VALIDATION_REGEX)
                && !(remindDate.isAfter(expiryDate))
                && remindDate.isAfter(testYesterday);
    }

    /**
     * Returns remindFlag if coupon has reminder set.
     */
    public boolean hasReminder() {
        return this.remindFlag;
    }

    /**
     * Set remindDate to a coupon
     */
    public void setRemindDate(LocalDate date) {
        this.date = date;
        remindFlag = true;
        value = date.format(DATE_FORMATTER);
    }

    /**
     * Returns remind date
     */
    public LocalDate getDate() {
        return LocalDate.parse(value, DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindDate// instanceof handles nulls
                && value.equals(((RemindDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
