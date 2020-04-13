package seedu.address.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;


/**
 * CalenderPanel that holds the month
 */
public class CalenderPanel extends UiPart<Region> {
    private static int year;
    private static int monthNow;
    private static ArrayList<CalenderDate> calenderDatesArrayList = new ArrayList<>();
    private static final String FXML = "Calender.fxml";
    private final Logger logger = LogsCenter.getLogger(CalenderPanel.class);
    private final String[] monthsArray = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};
    private HashMap<String, Integer> datesArray = new HashMap<>();
    private String todayMonth;
    private String todayYear;



    @FXML
    private Text month;

    @FXML
    private Text todayDate;

    @FXML
    private GridPane calenderGrid;

    @FXML
    private Label mon1;


    public CalenderPanel() {

        super(FXML);

        this.setMonth();
        this.setDatesArray();
        this.setDates();
        calenderGrid.setAlignment(Pos.CENTER);
    }

    public void setMonth() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String[] currentDateArray = dtf.format(now).split("/");
        String currentMonth = currentDateArray[1];
        String currentYear = currentDateArray[0];
        String date = currentDateArray[2];
        todayMonth = currentMonth;
        todayYear = currentYear;

        int currentMonthInt = Integer.parseInt(currentMonth);
        monthNow = currentMonthInt;
        year = Integer.parseInt(todayYear);
        String currentMonthAndYear = monthsArray[currentMonthInt - 1] + " " + currentYear;
        month.setText(currentMonthAndYear);
        todayDate.setText("Today: " + date + " " + currentMonthAndYear);
    }

    public void setDates() {
        try {
            String firstDay = getCalenderDates();
            int firstDayInt = datesArray.get(firstDay);
            String dateSkeleton = makeDate();
            for (int i = 1; i < 32; i++) {
                String temp = "";
                if (i < 10) {
                    temp = "0" + i;
                } else {
                    temp = "" + i;
                }
                calenderDatesArrayList.add(new CalenderDate(temp + dateSkeleton, i));
            }

            int x = 0;
            for (int col = firstDayInt; col < 7; col++) {
                System.out.println(col);
                calenderGrid.add(calenderDatesArrayList.get(x).getRoot(), col, 1, 1, 1);
                x++;
            }

            for (int row = 2; row < 7; row++) {
                for (int col = 0; col < 7; col++) {
                    if (x >= 31) {
                        break;
                    }
                    calenderGrid.add(calenderDatesArrayList.get(x).getRoot(), col, row, 1, 1);
                    x++;
                }
            }
        } catch (ParseException ex) {
            logger.info(ex.getMessage());
        }

    }

    /**
     * Create the date format.
     * @return a new date format
     */
    private String makeDate() {
        String dateSkeleton = "";
        if (monthNow < 10) {
            dateSkeleton = "-" + 0 + monthNow + "-" + year;
        } else {
            dateSkeleton = "-" + monthNow + "-" + year;
        }
        return dateSkeleton;
    }

    private String getCalenderDates() throws ParseException {
        String inputDate = String.format("01/%s/%s", todayMonth, todayYear);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = format1.parse(inputDate);
        DateFormat format2 = new SimpleDateFormat("EEEE");
        return format2.format(dt1);
    }

    public static ArrayList<CalenderDate> getCalenderDatesArrayList() {
        return calenderDatesArrayList;
    }

    public static int getYear() {
        return year;
    }

    public static int getCurrentMonth() {
        return monthNow;
    }

    private void setDatesArray() {
        datesArray.put("Monday", 0);
        datesArray.put("Tuesday", 1);
        datesArray.put("Wednesday", 2);
        datesArray.put("Thursday", 3);
        datesArray.put("Friday", 4);
        datesArray.put("Saturday", 5);
        datesArray.put("Sunday", 6);
    }

}
