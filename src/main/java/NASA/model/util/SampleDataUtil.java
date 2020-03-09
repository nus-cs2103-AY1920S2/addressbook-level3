package NASA.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import NASA.model.NasaBook;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.activity.Activity;
import NASA.model.activity.Name;
import NASA.model.module.Module;
import NASA.model.module.ModuleCode;
import NASA.model.module.ModuleName;

/**
 * Contains utility methods for populating {@code NasaBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
                new Module(new ModuleCode("CS2101"), new ModuleName("Effective Communication for Computing " +
                        "Professionals")),
                new Module(new ModuleCode("CS2103T"), new ModuleName("Software Engineering"))
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

