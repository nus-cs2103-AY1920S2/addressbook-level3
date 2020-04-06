package seedu.foodiebot.commons.core;

/** Container for user visible messages. */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_DATA_COMMAND = "Data is invalid";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ITEM_DISPLAYED_INDEX =
        "The item index provided is invalid";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_REPORT_EXPENSE = "Here are your expenses from %s to %s:\n";
    public static final String MESSAGE_REPORT_TRANSACTIONS = "Here are your transactions from %s to %s:\n";
    public static final String MESSAGE_FOOD_REVIEW = "Leave a review for the food item";
    public static final String MESSAGE_BUDGET_SET = "Your %s budget has been set to $ %.2f.\n"
        + "You have $%.2f available to spend today.\n";
    public static final String MESSAGE_BUDGET_VIEW = "Your %s budget is $%.2f."
            + " Remaining budget: $%.2f.\nYou have $%.2f left to spend today.\n"
            + "Here are your %s spendings:\n";
    public static final String MESSAGE_NOTAVAILABLE = "No information available";
    public static final String MESSAGE_INVALID_DATE = "Invalid date format!";
    public static final String MESSAGE_INVALID_PREFIX = "Invalid prefix!";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Invalid date range!";
    public static final String MESSAGE_INVALID_FILEREADER = "Problem loading file!";
    public static final String MESSAGE_INVALID_CANTEEN_NAME = "Invalid canteen name!";
    public static final String MESSAGE_INVALID_FAVORITE_NAME_SPECIFIED = "Please specify the number in the menu "
        + "of the food stall";
    public static final String MESSAGE_NO_FOOD_WITH_TAG = "There is no food with the tag : ";
    public static final String MESSAGE_INVALID_STALL_INDEX = "Invalid stall index!";
    public static final String MESSAGE_INVALID_CANTEEN_INDEX = "Invalid canteen index!";
    public static final String MESSAGE_CANTEEN_ALREADY_SELECTED = "You have already selected a canteen!";
}
