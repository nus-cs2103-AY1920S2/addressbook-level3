package team.easytravel.model.trip;

import static team.easytravel.commons.util.AppUtil.checkArgument;
import static team.easytravel.commons.util.CollectionUtil.requireAllNonNull;

import team.easytravel.commons.core.time.Date;
import team.easytravel.model.util.attributes.Title;

/**
 * Represents a Trip in the EasyTravel
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Trip {

    public static final String MESSAGE_TRIP_CONSTRAINTS = "Start date must not be after end date and they should only "
            + "be a maximum of 30 days apart.";

    // Trip data
    private Title title;
    private final Date startDate;
    private final Date endDate;
    private final int numDays;
    private Budget budget;
    private final ExchangeRate exchangeRate;

    /**
     * Creates an instance of a Trip.
     * Every field must be present and not null.
     */
    public Trip(Title title, Date startDate, Date endDate, Budget budget, ExchangeRate exchangeRate) {
        requireAllNonNull(title, startDate, endDate, budget);
        checkArgument(isValidTrip(startDate, endDate), MESSAGE_TRIP_CONSTRAINTS);
        this.title = title;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
        numDays = startDate.daysUntilInclusive(endDate);
        this.exchangeRate = exchangeRate;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title newTitle) {
        this.title = newTitle;
    }

    public int getNumDays() {
        return numDays;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget newBudget) {
        this.budget = newBudget;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public static boolean isValidTrip(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0 && startDate.daysUntilInclusive(endDate) <= 30;
    }

    @Override
    public String toString() {
        return "Trip - Title: " + title
                + " Start date: " + startDate
                + " End date: " + endDate
                + " Duration: " + numDays + " days"
                + " Budget: " + budget
                + " Exchange Rate: " + exchangeRate;
    }

}
