package seedu.jelphabot.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class to generate updated dates for SampleDataUtil.
 *
 * @@author yaojiethng
 */
public class SampleDateUtil {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM-d-yyyy HH mm");
    public static final String LAST_NIGHT = LocalDate.now().atStartOfDay().minusMinutes(1).format(dateTimeFormatter);
    public static final String LAST_WEEK = LocalDate.now().atStartOfDay().minusDays(7).format(dateTimeFormatter);
    public static final String TODAY_MORNING = LocalDate.now().atStartOfDay().plusHours(10).format(dateTimeFormatter);
    public static final String TONIGHT =
        LocalDate.now().atStartOfDay().plusHours(23).plusMinutes(59).format(dateTimeFormatter);
    public static final String FIVE_DAYS_LATER =
        LocalDate.now().atStartOfDay().plusDays(5).plusHours(14).plusMinutes(30).format(dateTimeFormatter);
    public static final String NEXT_WEEK =
        LocalDate.now().atStartOfDay().plusDays(7).plusHours(9).format(dateTimeFormatter);
    public static final String NEXT_MONTH =
        LocalDate.now().atStartOfDay().plusMonths(1).minusMinutes(1).format(dateTimeFormatter);
}
