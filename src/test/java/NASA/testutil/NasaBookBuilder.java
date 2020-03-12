package NASA.testutil;

import static NASA.testutil.TypicalModules.CS2103T;
import static NASA.testutil.TypicalModules.CS2106;
import static NASA.testutil.TypicalModules.GEH1001;

import NASA.commons.core.index.Index;
import NASA.model.NasaBook;
import NASA.model.module.Module;
import NASA.model.module.UniqueModuleList;

public class NasaBookBuilder {

    public static final Module DEFAULT_MODULE_1 = CS2103T;
    public static final Module DEFAULT_MODULE_2 = CS2106;
    public static final Module DEFAULT_MODULE_3 = GEH1001;

    private UniqueModuleList moduleList;

    public NasaBookBuilder() {
        moduleList = new UniqueModuleList();
        moduleList.add(DEFAULT_MODULE_1);
        moduleList.add(DEFAULT_MODULE_2);
        moduleList.add(DEFAULT_MODULE_3);
    }

    public NasaBookBuilder reset() {
        moduleList = new UniqueModuleList();
        return this;
    }

    public NasaBookBuilder addModule(Module module) {
        moduleList.add(module);
        return this;
    }

    public NasaBookBuilder deleteModule(Module module) {
        moduleList.remove(module);
        return this;
    }

    public NasaBookBuilder deleteModule(Index index) {
        moduleList.removeByIndex(index);
        return this;
    }

    public NasaBook build() {
        NasaBook nasaBook = new NasaBook();
        nasaBook.setModuleList(moduleList);
        return nasaBook;
    }

}
