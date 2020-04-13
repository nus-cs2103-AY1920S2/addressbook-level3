package nasa.logic.calendar;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import nasa.model.activity.Event;
import nasa.model.module.ModuleCode;

/**
 * Class to convert event to ics event.
 */
public class IcsEvent {

    private static final String ICS_TYPE = "VEVENT";
    private UUID uuid = UUID.randomUUID();
    private Event event;
    private ModuleCode moduleCode;
    private IcsSchedule icsSchedule;

    /**
     * Constructor.
     * @param event event to be formatted
     * @param moduleCode moduleCode the event belongs to
     */
    public IcsEvent(Event event, ModuleCode moduleCode) {
        requireAllNonNull(event, moduleCode);
        this.event = event;
        this.moduleCode = moduleCode;
        this.icsSchedule = new IcsSchedule(event.getSchedule());
    }

    /**
     * Get the ics format for event.
     * @return String format for event for ics
     */
    public String getIcsFormat() {
        String icsFormat = "BEGIN:" + ICS_TYPE + System.lineSeparator()
            + "UID:" + uuid.toString() + System.lineSeparator()
            + "DTSTAMP:" + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")))
            + System.lineSeparator()
            + "DTSTART;TZID=Asia/Singapore:"
            + event.getStartDate().getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))
            + System.lineSeparator()
            + "DTEND;TZID=Asia/Singapore:"
            + event.getEndDate().getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))
            + System.lineSeparator();

        if (icsSchedule.hasSchedule()) {
            icsFormat += icsSchedule.getIcsRule() + System.lineSeparator();
        }

        icsFormat += "SUMMARY:" + String.format("[%s] ", moduleCode.toString())
            + event.getName().toString() + System.lineSeparator()
            + "DESCRIPTION:" + event.getNote().toString() + System.lineSeparator()
            + "END:" + ICS_TYPE + System.lineSeparator();

        return icsFormat;
    }
}
