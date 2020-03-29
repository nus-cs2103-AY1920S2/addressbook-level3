package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.Locale;

/**
 * Compares birthdays and sorts them in chronological order.
 */
public class BirthdayComparator implements Comparator<Person> {
    private static final DateTimeFormatter inputFormat = new DateTimeFormatterBuilder().appendPattern("MM-dd")
        .parseDefaulting(ChronoField.YEAR, 2020).toFormatter(Locale.ENGLISH);

    @Override
    public int compare(Person p1, Person p2) {
        return LocalDate.parse(p1.getBirthday().birthday, inputFormat)
            .compareTo(LocalDate.parse(p2.getBirthday().birthday, inputFormat));
    }
}
