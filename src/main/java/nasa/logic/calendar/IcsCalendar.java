package nasa.logic.calendar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.ModuleCode;

/**
 * Calendar ics class, writes all the events
 * and deadlines into .ics folder.
 */
public class IcsCalendar {

    /**
     * Method to write all events and deadlines to ics file.
     * @param filepath path for ics file to be written to
     * @param deadlines all the deadline in the nasabook
     * @param events all the event in the nasabook
     * @throws IOException
     */
    public static void writeToIcsFile(Path filepath, HashMap<ModuleCode, ArrayList<Deadline>> deadlines,
                                      HashMap<ModuleCode, ArrayList<Event>> events) throws IOException {
        File file = new File(String.valueOf(filepath));
        if (!file.exists()) {
            file.mkdir(); // make sure can write to something
        }

        FileWriter fileWriter = new FileWriter(String.valueOf(filepath.resolve("nasa.ics")));
        fileWriter.write("BEGIN:VCALENDAR" + System.lineSeparator());
        fileWriter.write("VERSION:2.0" + System.lineSeparator());
        fileWriter.write("PRODID:-//NASA//CS2103T//EN" + System.lineSeparator());

        // write deadlines in
        for (ModuleCode moduleCode : deadlines.keySet()) {
            ArrayList<Deadline> moduleDeadlines = deadlines.get(moduleCode);
            for (Deadline deadline : moduleDeadlines) {
                fileWriter.write(new IcsDeadline(deadline, moduleCode).getIcsFormat());
            }
        }

        // followed by event
        for (ModuleCode moduleCode : events.keySet()) {
            ArrayList<Event> moduleEvents = events.get(moduleCode);
            for (Event event : moduleEvents) {
                fileWriter.write(new IcsEvent(event, moduleCode).getIcsFormat());
            }
        }

        // end the calendar ics file
        fileWriter.write("END:VCALENDAR");
        fileWriter.close();
    }
}
