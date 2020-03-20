package fithelper.model.diary;

import fithelper.logic.parser.exceptions.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class is for testing and debugging.
 */
public class DiaryMain {

    /**
     * Main testing method.
     * @param args user input
     */
    public static void main(String[] args) throws ParseException {
        String str = "2015-03-15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        Diary myDiary = new Diary(new DiaryDate("2015-03-16"), new Content("I am enjoying my life."));
        System.out.println(myDiary.toString());
        myDiary.addContent("And I love myself.");
        System.out.println(myDiary.toString());
    }
}
