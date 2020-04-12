package nasa.logic.calendar;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import nasa.model.activity.Deadline;
import nasa.model.module.ModuleCode;

/**
 * For deadline, put it as an a VTODO.
 */
public class IcsDeadline {

    private static final String ICS_TYPE = "VTODO";
    private UUID uuid = UUID.randomUUID();
    private Deadline deadline;
    private String status;
    private ModuleCode moduleCode;
    private IcsSchedule icsSchedule;

    /**
     * Constructor.
     * @param deadline deadline to be converted to ics
     * @param moduleCode moduleCode the deadline belongs to
     */
    public IcsDeadline(Deadline deadline, ModuleCode moduleCode) {
        requireAllNonNull(deadline, moduleCode);
        this.deadline = deadline;
        this.moduleCode = moduleCode;
        if (deadline.isDone()) {
            status = "COMPLETED";
        } else if (deadline.isOverdue()) {
            status = "NEEDS-ACTION";
        } else {
            status = "IN-PROCESS";
        }
        icsSchedule = new IcsSchedule(deadline.getSchedule());
    }

    /**
     * Get the ics format representation of deadline.
     * @return string format for deadline for ics
     */
    public String getIcsFormat() {
        String icsFormat = "BEGIN:" + ICS_TYPE + System.lineSeparator()
            + "UID:" + uuid.toString() + System.lineSeparator()
            + "DUE;TZID=Asia/Singapore:"
            + deadline.getDueDate().getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))
            + System.lineSeparator();

        if (icsSchedule.hasSchedule()) {
            icsFormat += icsSchedule.getIcsRule() + System.lineSeparator();
        }

        icsFormat += "SUMMARY:" + String.format("[%s] ", moduleCode.toString())
            + deadline.getName().toString() + System.lineSeparator()
            + "DESCRIPTION:" + deadline.getNote().toString() + System.lineSeparator()
            + "STATUS:" + status + System.lineSeparator()
            + "END:" + ICS_TYPE + System.lineSeparator();

        return icsFormat;
    }
}
