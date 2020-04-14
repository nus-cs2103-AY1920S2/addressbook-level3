package fithelper.model.diary;

import fithelper.logic.parser.exceptions.ParseException;

/**
 * This class is for testing and debugging.
 */
public class DiaryMain {

    /**
     * Main testing method.
     * @param args user input
     */
    public static void main(String[] args) throws ParseException {
        Diary myDiary = new Diary(new DiaryDate("2015-03-16"), new Content("I am enjoying my life."));
        System.out.println(myDiary.toString());
        //myDiary.addContent("And I love myself.");
        System.out.println(myDiary.getContent());
    }
}
