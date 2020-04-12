package seedu.address.model.bluetooth.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDateFormatter implements DateFormatterInterface {
    @Override
    public String format(long epochTs) {
        Date date = new Date(epochTs);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);  // IDK what I'm doing here. All I know is that I am converting the epoch timestamp to some timezone.
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }
}
