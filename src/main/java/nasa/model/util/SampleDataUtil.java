package nasa.model.util;

import nasa.model.HistoryBook;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.UniqueModuleList;

/**
 * Contains utility methods for populating {@code NasaBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {

        //TODO add activities
        return new Module[] {
            new Module(new ModuleCode("CS2101"), new ModuleName("Effective Communication for Computing "
                    + "Professionals")),
            new Module(new ModuleCode("CS2105"), new ModuleName("Introduction to Computer Network"))
        };
    }

    public static UniqueModuleList[] getSampleModuleList() {
        UniqueModuleList list1 = new UniqueModuleList();
        UniqueModuleList list2 = new UniqueModuleList();
        list1.add(new Module(new ModuleCode("CS2103T"), new ModuleName("Software engineering")));
        list2.add(new Module(new ModuleCode("CS2106"), new ModuleName("Operating System")));
        return new UniqueModuleList[] {
            list1,
            list2
        };
    }

    public static ReadOnlyNasaBook getSampleNasaBook() {
        NasaBook sampleNb = new NasaBook();
        for (Module sampleModule : getSampleModules()) {
            sampleNb.addModule(sampleModule);
        }
        return sampleNb;
    }

    public static ReadOnlyHistory getSampleHistoryBook() {
        HistoryBook sampleNb = new HistoryBook();
        for (UniqueModuleList sampleModuleList : getSampleModuleList()) {
            sampleNb.add(sampleModuleList);
        }
        return sampleNb;
    }
}

