package nasa.model;

import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalActivities.DEADLINE;
import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.CS2106;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.model.module.UniqueModuleList;

class NasaBookTest {
    private final NasaBook nasaBook = new NasaBook();
    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), nasaBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nasaBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNasaBook_replacesData() {
        NasaBook newData = getTypicalNasaBook();
        nasaBook.resetData(newData);
        assertEquals(newData, nasaBook);
    }

    /*
    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two Modules with the same identity fields
        Module editedAlice = new ModuleBuilder(CS2103T.withNasa(VALID_Nasa_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Module> newModules = Arrays.asList(CS2103T, editedAlice);
        NasaBookStub newData = new NasaBookStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> nasaBook.resetData(newData));
    }

     */

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nasaBook.hasModule((nasa.model.module.Module) null));
    }

    @Test
    public void hasModule_moduleNotInNasaBook_returnsFalse() {
        assertFalse(nasaBook.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleInNasaBook_returnsTrue() {
        nasaBook.addModule(CS2103T);
        assertTrue(nasaBook.hasModule(CS2103T));
    }

    /*
    @Test
    public void hasModule_ModuleWithSameIdentityFieldsInNasaBook_returnsTrue() {
        nasaBook.addModule(ALICE);
        Module editedAlice = new ModuleBuilder(ALICE).withNasa(VALID_Nasa_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(nasaBook.hasModule(editedAlice));
    }

     */

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> nasaBook.getModuleList().remove(0));
    }

    @Test
    void addModule() {
        nasaBook.addModule(CS2103T);
        assertTrue(nasaBook.hasModule(CS2103T));
    }

    @Test
    void addActivity() {
        nasaBook.addModule(CS2103T);
        nasaBook.addActivity(CS2103T, DEADLINE);
        assertTrue(nasaBook.hasActivity(CS2103T, DEADLINE));
    }

    @Test
    void removeActivity() {
        nasaBook.addModule(CS2103T);
        nasaBook.addActivity(CS2103T, DEADLINE);
        nasaBook.removeActivity(CS2103T, DEADLINE);
        assertFalse(nasaBook.hasActivity(CS2103T, DEADLINE));
    }

    @Test
    void removeModuleByIndex() {
        nasaBook.addModule(CS2103T);
        nasaBook.addModule(CS2106);
        nasaBook.removeModuleByIndex(Index.fromZeroBased(1));
        assertTrue(nasaBook.hasModule(CS2103T));
        assertFalse(nasaBook.hasModule(CS2106));
    }

    /**
     * A stub ReadOnlyNasaBook whose Modules list can violate interface constraints.
     */
    /*
    private static class NasaBookStub implements ReadOnlyNasaBook {
        private final ObservableList<Module> Modules = FXCollections.observableArrayList();

        NasaBookStub(Collection<Module> Modules) {
            this.Modules.setAll(Modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return Modules;
        }


    }

     */

}
