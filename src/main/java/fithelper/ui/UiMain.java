package fithelper.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UiMain {
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String todayDate = dtf.format(localDate);
        System.out.println(todayDate);

    }
}
