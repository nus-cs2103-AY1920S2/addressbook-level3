package team.easytravel.storage.trip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.core.time.Date;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.ExchangeRate;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.util.attributes.Title;

/**
 * Jackson-friendly version of {@link Trip}.
 */
class JsonAdaptedTrip {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String title;
    private final String startDate;
    private final String endDate;
    private final Integer budget;
    private final Double exchangeRate;

    /**
     * Constructs a {@code JsonAdaptedTrip} with the given trip details.
     */
    @JsonCreator
    public JsonAdaptedTrip(@JsonProperty("title") String title, @JsonProperty("startDate") String startDate,
                           @JsonProperty("endDate") String endDate, @JsonProperty("budget") Integer budget,
                           @JsonProperty("exchangeRate") Double exchangeRate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.exchangeRate = exchangeRate;
    }

    /**
     * Converts a given {@code Trip} into this class for Jackson use.
     */
    public JsonAdaptedTrip(Trip source) {
        title = source.getTitle().value;
        startDate = source.getStartDate().getStorageFormat();
        endDate = source.getEndDate().getStorageFormat();
        budget = source.getBudget().value;
        exchangeRate = source.getExchangeRate().value;
    }

    /**
     * Converts this Jackson-friendly adapted trip object into the model's {@code Trip} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted trip.
     */
    public Trip toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(startDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelStartDate = Date.fromString(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelEndDate = Date.fromString(endDate);

        if (!Trip.isValidTrip(modelStartDate, modelEndDate)) {
            throw new IllegalValueException(Trip.MESSAGE_TRIP_CONSTRAINTS);
        }

        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }
        if (!Budget.isValidBudget(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        final Budget modelBudget = new Budget(budget);

        if (exchangeRate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExchangeRate.class.getSimpleName()));
        }
        final ExchangeRate modelExchangeRate = new ExchangeRate(exchangeRate);

        return new Trip(modelTitle, modelStartDate, modelEndDate, modelBudget, modelExchangeRate);
    }

}
