package team.easytravel.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.easytravel.commons.core.GuiSettings;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.logic.parser.EasyTravelParser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.Trip;
import team.easytravel.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";

    private static final String HELP_MESSAGE = "Refer to the user guide: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EasyTravelParser easyTravelParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        easyTravelParser = new EasyTravelParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = easyTravelParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTripManager(model.getTripManager());
            storage.saveAccommodationBookings(model.getAccommodationBookingManager());
            storage.saveFixedExpenses(model.getFixedExpenseManager());
            storage.saveTransportBookings(model.getTransportBookingManager());
            storage.saveActivityManager(model.getActivityManager());
            storage.savePackingList(model.getPackingListManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        return model.getFilteredTransportBookingList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        return model.getFilteredFixedExpenseList();
    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return model.getFilteredPackingList();
    }

    @Override
    public ObservableList<AccommodationBooking> getFilteredAccommodationBookingList() {
        return model.getFilteredAccommodationBookingList();
    }

    @Override
    public List<ObservableList<DayScheduleEntry>> getScheduleList() {
        return model.getScheduleList();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public boolean hasTrip() {
        return model.hasTrip();
    }

    @Override
    public Trip getTrip() {
        return model.getTripManager().getTrip();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<String> getHelpQuestions() {
        ArrayList<String> questions = new ArrayList<>();
        questions.add(HELP_MESSAGE);
        questions.add("Set Trip : settrip title/TITLE start/START_DATE end/END_DATE location/LOCATION\n\n"
                + "Example: settrip title/Graduation Trip start/28-09-2020 end/28-10-2020 location/Japan");
        questions.add("Edit Trip : edittrip [title/TITLE] [start/START_DATE] [end/END_DATE][country/COUNTRY]\n\n"
                + "Example: edittrip title/not a graduation trip country/Singapore\n");
        questions.add("Delete Trip : deletetrip\n\n"
                + "Example: deletetrip\n");
        questions.add("Clear Trip : cleartrip\n\n"
                + "Example: cleartrip\n");
        questions.add("View Itinerary : view\n\n"
                + "Example: view\n");
        questions.add("Check Preparation : status\n\n"
                + "Example: status\n");


        questions.add("Add Activity : addactivity title/TITLE duration/DURATION location/LOCATION [t/tag]...\n\n"
                + "Example: addactivity title/Going to the beach duration/4 location/Hakone t/sea t/ocean\n");
        questions.add("Edit Activity : editactivity index [title/NAME] [duration/DURATION] [location/LOCATION] "
                + "[t/tag]\n\n"
                + "Example: editactivity 1 name/Go to an aquarium\n"
                + " \n");
        questions.add("Delete Activity : deleteactivity index\n\n"
                + "Example: deleteactivity 1\n");
        questions.add("Clear Activity : clearactivity\n\n"
                + "Example: clearactivity\n");


        questions.add("Add Transport Booking : addtransport mode/MODE startloc/START_LOCATION endloc/END_LOCATION \n"
                + "starttime/DATE_TIME_OF_DEPARTURE endtime/DATE_TIME_OF_ARRIVAL\n\n"
                + "Example: addtransport mode/plane startloc/Singapore endloc/Japan starttime/22-04-2021 09:00 \n"
                + "endtime/22-04-2021 16:00\n");
        questions.add("Edit Transportation Booking : edittransport INDEX [mode/MODE] [startloc/START_LOCATION]\n"
                + "[endloc/END_LOCATION] [starttime/DATE_TIME_OF_DEPARTURE] [endtime/DATE_TIME_OF_ARRIVAL]\n\n"
                + "Example: edittransport 2 starttime/22-04-2021 10:00\n");
        questions.add("Delete transportation Booking : deletetransport INDEX\n\n"
                + "Example: deletetransport 1\n");
        questions.add("Clear Transportation Bookings : cleartransport\n\n"
                + "Example: cleartransport\n");
        questions.add("Add Accommodation Booking :  addacc name/NAME loc/LOCATION startday/START_DAY\n"
                + "endday/END_DAY [remark/REMARK]\n\n"
                + "Example: addacc name/JW Marriott Hotel loc/KL startday/2 endday/4\n");
        questions.add("Edit Accommodation Booking : editacc INDEX [name/NAME] [loc/LOCATION] [startday/START_DAY]\n"
                + "[endday/END_DAY] [remark/REMARK]\n\n"
                + "Example: editacc 2 startday/4 endday/6\n");


        questions.add("Delete Transportation Bookings : deletetransport INDEX\n\n"
                + "Example: deletetransport 1");
        questions.add("Clear Transportation Bookings : cleartransport\n\n"
                + "Example: cleartransport");
        questions.add("Add Accommodation Booking : addacc name/NAME loc/LOCATION startday/START_DAY\n"
                + "endday/END_DAY [remark/REMARK]\n\n"
                + "Example: addacc name/JW Marriott Hotel loc/KL startday/2 endday/4");
        questions.add("Edit Accommodation Booking : editacc INDEX [name/NAME] [loc/LOCATION] [startday/START_DAY]\n "
                + "[endday/END_DAY] [remark/REMARK]\n\n"
                + "Example: editacc 2 startday/4 endday/6");
        questions.add("Delete Accommodation Booking : deleteacc INDEX\n\n"
                + "Example: deleteacc 2");
        questions.add("Clear Accommodation Bookings : clearacc\n\n"
                + "Example: clearacc");


        questions.add("Add Preset Category : addpreset preset/PRESET_NAME\n\n"
                + "Example: addpreset beach");
        questions.add("List all Presets in Packing List : listpresets\n\n"
                + "Example: listpresets");
        questions.add("Add Item in Packing List : additem item/ITEM quantity/QUANTITY\n\n"
                + "Example: additem item/underwear quantity/5");
        questions.add("Edit Item in Packing List : edititem INDEX [i/item] [q/quantity]\n\n"
                + "edititem 1 item/shirt quantity/5");
        questions.add("Delete item in Packing List : deleteitem INDEX\n\n"
                + "Example: deleteitem 1");
        questions.add("Mark Item as packed in Packing List :  checkitem INDEX\n\n"
                + "Example: checkitem 1");
        questions.add("Mark Item as unpacked in Packing List : uncheckitem INDEX\n\n"
                + "uncheckitem 1");
        questions.add("Sort Items in Packing List : sortitem order criteria\n\n"
                + "Example: sortitem asc alphabet");
        questions.add("Find items in Packing List: finditem\n\n"
                + "Example: finditem car shampoo");
        questions.add("Find items in Packing List under category: finditemcategory\n\n"
                + "Example: finditemcategory swimming clothes\n");


        questions.add("Add Fixed Expense :  addexpense amount/AMOUNT currency/CURRENCY\n"
                + "description/DESCRIPTION category/CATEGORY \n\n"
                + "Example: addexpense amount/1100 currency/SGD description/SQ Tickets category/Flights");
        questions.add("Edit Fixed Expense : editexpense INDEX amount/AMOUNT currency/CURRENCY\n "
                + "[description/DESCRIPTION] [category/CATEGORY]\n\n"
                + "Example: editexpense 1 amount/3000 currency/other description/Cathay Pacific Flight");
        questions.add("Delete Fixed Expense : deleteexpense INDEX\n\n"
                + "Example: deleteexpense 1");
        questions.add("Clear Fixed Expenses : clearexpense\n\n"
                + "Example: clearexpense");
        questions.add("Set Conversion Rate :  conversion rate/RATE\n\n"
                + "Example: conversion rate/1.32");
        questions.add("Sort Fixed Expenses : sortexpense SORTIDENTIFIER [AMOUNT] [DESCRIPTION] [CATEGORY]\n\n"
                + "Example: sortexpense 1 amount");


        return FXCollections.observableArrayList(questions);
    }
}
