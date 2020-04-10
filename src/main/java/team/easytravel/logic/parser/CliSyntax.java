package team.easytravel.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_AMOUNT = new Prefix("amount/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("description/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("category/");
    public static final Prefix PREFIX_CURRENCY = new Prefix("currency/");

    public static final Prefix PREFIX_ITEM_NAME = new Prefix("name/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("quantity/");
    public static final Prefix PREFIX_ITEM_CATEGORY = new Prefix("category/");
    public static final Prefix PREFIX_ITEM_CRITERIA = new Prefix("criteria/");

    public static final Prefix PREFIX_MODE = new Prefix("mode/");
    public static final Prefix PREFIX_START_LOCATION = new Prefix("startloc/");
    public static final Prefix PREFIX_END_LOCATION = new Prefix("endloc/");
    public static final Prefix PREFIX_START_DATE_TIME = new Prefix("starttime/");
    public static final Prefix PREFIX_END_DATE_TIME = new Prefix("endtime/");

    public static final Prefix PREFIX_ACTIVITY_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_ACTIVITY_LOCATION = new Prefix("location/");
    public static final Prefix PREFIX_ACTIVITY_DURATION = new Prefix("duration/");
    public static final Prefix PREFIX_ACTIVITY_TAG = new Prefix("tag/");

    public static final Prefix PREFIX_ACCOMMODATION_NAME = new Prefix("name/");
    public static final Prefix PREFIX_ACCOMMODATION_LOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_ACCOMMODATION_START_DAY = new Prefix("startday/");
    public static final Prefix PREFIX_ACCOMMODATION_END_DAY = new Prefix("endday/");
    public static final Prefix PREFIX_ACCOMMODATION_REMARK = new Prefix("remark/");

    public static final Prefix PREFIX_TRIP_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_TRIP_START_DATE = new Prefix("startdate/");
    public static final Prefix PREFIX_TRIP_END_DATE = new Prefix("enddate/");
    public static final Prefix PREFIX_TRIP_BUDGET = new Prefix("budget/");
    public static final Prefix PREFIX_TRIP_EXCHANGE_RATE = new Prefix("exchangerate/");

    public static final Prefix PREFIX_SCHEDULE_DAY = new Prefix("day/");
    public static final Prefix PREFIX_SCHEDULE_TIME = new Prefix("time/");
}
