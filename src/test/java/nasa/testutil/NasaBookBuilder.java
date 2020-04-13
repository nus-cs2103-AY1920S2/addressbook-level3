package nasa.testutil;

import static nasa.testutil.TypicalModules.CS2106;
import static nasa.testutil.TypicalModules.GEH1001;

import nasa.model.NasaBook;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.UniqueModuleList;

/**
 * Class to build example NasaBook.
 */
public class NasaBookBuilder {

    public static final Module DEFAULT_MODULE_1 = CS2106;
    public static final Module DEFAULT_MODULE_2 = GEH1001;
    public static final Module DEFAULT_MODULE_3 = new ModuleBuilder().build();

    private UniqueModuleList moduleList;

    public NasaBookBuilder() {
        moduleList = new UniqueModuleList();
        moduleList.add(DEFAULT_MODULE_1); // first module in moduleList
        moduleList.add(DEFAULT_MODULE_2);
        moduleList.add(DEFAULT_MODULE_3);
    }

    /**
     * Reset nasabook builder.
     * @return NasaBookBuilder
     */
    public NasaBookBuilder reset() {
        moduleList = new UniqueModuleList();
        return this;
    }

    /**
     * Add module to Nasabook.
     * @param module module
     * @return NasaBookBuilder
     */
    public NasaBookBuilder addModule(Module module) {
        moduleList.add(module);
        return this;
    }

    /**
     * Delete the module from Nasabook.
     * @param moduleCode module
     * @return NasaBookBuilder
     */
    public NasaBookBuilder deleteModule(ModuleCode moduleCode) {
        moduleList.remove(moduleCode);
        return this;
    }

    /**
     * Build Nasabook.
     * @return NasaBook
     */
    public NasaBook build() {
        NasaBook nasaBook = new NasaBook();
        UniqueModuleList newModuleList = new UniqueModuleList();
        newModuleList.setModules(moduleList.getDeepCopyList());
        nasaBook.setModuleList(newModuleList);
        return nasaBook;
    }

}
