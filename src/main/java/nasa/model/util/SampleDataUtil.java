package nasa.model.util;

import java.util.ArrayList;
import java.util.List;

import nasa.model.HistoryBook;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.UniqueModuleList;

/**
 * Contains utility methods for populating {@code NasaBook} with sample data.
 */
public class SampleDataUtil {

    private static Module geh1001 = new Module(new ModuleCode("GEH1001"), new ModuleName("Globalisation"));
    private static Module cs2106 = new Module(new ModuleCode("CS2106"), new ModuleName("Operating System"));

    public static Module[] getSampleModules() {

        Deadline project = new Deadline(new Name("Weekly Quiz"), new Date("20-06-2020 00:00"));
        project.setNote(new Note("To read readings"));
        project.setSchedule(1);

        Deadline tutorial = new Deadline(new Name("Tutorial"), new Date("20-06-2020 00:00"));
        tutorial.setSchedule(1); //repeat weekly.

        geh1001.addDeadline(project);
        geh1001.addDeadline(tutorial);

        //TODO add activities
        return new Module[] {
            new Module(new ModuleCode("CS2101"), new ModuleName("Effective Communication for Computing "
                    + "Professionals")),
            new Module(new ModuleCode("CS2105"), new ModuleName("Introduction to Computer Network")),
            cs2106,
            geh1001
        };
    }

    public static UniqueModuleList[] getSampleModuleList() {
        UniqueModuleList list1 = new UniqueModuleList();
        UniqueModuleList list2 = new UniqueModuleList();
        UniqueModuleList list3 = new UniqueModuleList();


        list1.add(new Module(new ModuleCode("CS2101"), new ModuleName("Effective Communication for Computing "
                + "Professionals")));
        list1.add(new Module(new ModuleCode("CS2105"), new ModuleName("Introduction to Computer Network")));

        list2.setModules(list1.getDeepCopyList());
        list2.add(cs2106);
        list2.add(geh1001);
        return new UniqueModuleList[] {
            list3, list1, list2
        };
    }

    public static List<String> getSampleUiList() {
        List<String> sample = new ArrayList<>();
        sample.add("null");
        sample.add("find activity Tutorial");
        sample.add("find activity Project");

        return sample;
    }

    public static ReadOnlyNasaBook getSampleNasaBook() {
        NasaBook sampleNb = new NasaBook();
        for (Module sampleModule : getSampleModules()) {
            sampleNb.addModule(sampleModule);
        }
        return sampleNb;
    }

    public static ReadOnlyHistory<UniqueModuleList> getSampleHistoryBook() {
        HistoryBook<UniqueModuleList> sampleNb = new HistoryBook<>();
        for (UniqueModuleList sampleModuleList : getSampleModuleList()) {
            sampleNb.add(sampleModuleList);
        }
        return sampleNb;
    }

    public static ReadOnlyHistory<String> getSampleUiHistoryBook() {
        HistoryBook<String> sampleNb = new HistoryBook<>();
        for (String sampleUiList : getSampleUiList()) {
            sampleNb.add(sampleUiList);
        }
        return sampleNb;
    }
}

