package team.easytravel.model.util.sampledata;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.Description;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpenseCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;

/**
 * Contains utility methods for populating Easy Travel with sample data.
 */
public class SampleDataUtil {
    /**
     * Returns a tag set containing the list of strings given.
     *
     * @param strings the strings
     * @return the tag set
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Get sample transport bookings transport booking [ ].
     *
     * @return the transport booking [ ]
     */
    public static TransportBooking[] getSampleTransportBookings() {
        return new TransportBooking[]{
            new TransportBooking(new Mode("plane"), new Location("Singapore"), new Location("Japan"),
                    DateTime.fromString("28-09-2020 10:15"), DateTime.fromString("28-09-2020 17:00"))
        };
    }

    /**
     * Gets sample transport booking manager.
     *
     * @return the sample transport booking manager
     */
    public static ReadOnlyTransportBookingManager getSampleTransportBookingManager() {
        TransportBookingManager sampleTransportBookingManager = new TransportBookingManager();
        for (TransportBooking sampleTransportBooking : getSampleTransportBookings()) {
            sampleTransportBookingManager.addTransportBooking(sampleTransportBooking);
        }
        return sampleTransportBookingManager;
    }

    /**
     * Get sample fixed expenses fixed expense [ ].
     *
     * @return the fixed expense [ ]
     */
    public static FixedExpense[] getSampleFixedExpenses() {
        return new FixedExpense[]{
            new FixedExpense(new Amount("1000"), new Description("TestDescription"),
                    new FixedExpenseCategory("accommodations")),
            new FixedExpense(new Amount("2000"), new Description("TestDescription"),
                    new FixedExpenseCategory("transport"))
        };
    }

    /**
     * Gets sample fixed expense manager.
     *
     * @return the sample fixed expense manager
     */
    public static ReadOnlyFixedExpenseManager getSampleFixedExpenseManager() {
        FixedExpenseManager sampleFixedExpenseManager = new FixedExpenseManager();
        for (FixedExpense sampleFixedExpense : getSampleFixedExpenses()) {
            sampleFixedExpenseManager.addFixedExpense(sampleFixedExpense);
        }
        return sampleFixedExpenseManager;
    }

    /**
     * Get sample activity activity [ ].
     *
     * @return the activity [ ]
     */
    public static Activity[] getSampleActivity() {
        return new Activity[]{
            new Activity(new Title("Hot Spring"), new Duration(3), new Location("Hokkaido"),
                    getTagSet("relaxation"), Optional.empty())
        };
    }

    /**
     * Gets sample activity manager.
     *
     * @return the sample activity manager
     */
    public static ReadOnlyActivityManager getSampleActivityManager() {
        ActivityManager sampleActivityManager = new ActivityManager();
        for (Activity sampleActivity : getSampleActivity()) {
            sampleActivityManager.addActivity(sampleActivity);
        }
        return sampleActivityManager;
    }

    /**
     * Get sample accommodation bookings accommodation booking [ ].
     *
     * @return the accommodation booking [ ]
     */
    public static AccommodationBooking[] getSampleAccommodationBookings() {
        return new AccommodationBooking[]{
            new AccommodationBooking(new AccommodationName("Hyatt Regency"), new Location("Kyoto"), new Day(1),
                    new Day(3), new Remark("Checkin at 3pm"))
        };
    }

    /**
     * Gets sample accommodation booking manager.
     *
     * @return the sample accommodation booking manager
     */
    public static ReadOnlyAccommodationBookingManager getSampleAccommodationBookingManager() {
        AccommodationBookingManager sampleAccommodationBookingManager = new AccommodationBookingManager();
        for (AccommodationBooking sampleAccommodationBooking : getSampleAccommodationBookings()) {
            sampleAccommodationBookingManager.addAccommodationBooking(sampleAccommodationBooking);
        }
        return sampleAccommodationBookingManager;
    }

    /**
     * Get sample packing list items [ ].
     *
     * @return the packing list items [ ]
     */
    public static PackingListItem[] getSamplePackingListItems() {
        return new PackingListItem[]{
            new PackingListItem(new ItemName("Tshirt"), new Quantity(7), new ItemCategory("basics"), false),
            new PackingListItem(new ItemName("Jeans"), new Quantity(5), new ItemCategory("basics"), false),
            new PackingListItem(new ItemName("Underwear"), new Quantity(7), new ItemCategory("essentials"), false),
            new PackingListItem(new ItemName("Shampoo"), new Quantity(1), new ItemCategory("toiletries"), false),
            new PackingListItem(new ItemName("Conditioner"), new Quantity(1), new ItemCategory("toiletries"), false)
        };
    }

    /**
     * Gets sample packing list manager.
     *
     * @return the sample packing list manager
     */
    public static ReadOnlyPackingListManager getSamplePackingListManager() {
        PackingListManager samplePackingListManager = new PackingListManager();
        for (PackingListItem samplePackingListItem : getSamplePackingListItems()) {
            samplePackingListManager.addPackingListItem(samplePackingListItem);
        }
        return samplePackingListManager;
    }
}

