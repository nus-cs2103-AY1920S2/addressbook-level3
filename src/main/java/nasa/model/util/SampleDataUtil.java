package nasa.model.util;

import nasa.model.NasaBook;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * Contains utility methods for populating {@code NasaBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS2101"), new ModuleName("Effective Communication for Computing "
                    + "Professionals")),
            new Module(new ModuleCode("CS2105"), new ModuleName("Introduction to Computer Network"))
        };
    }

    public static ReadOnlyNasaBook getSampleNasaBook() {
        NasaBook sampleNb = new NasaBook();
        for (Module sampleModule : getSampleModules()) {
            sampleNb.addModule(sampleModule);
        }
        return sampleNb;
    }
}

