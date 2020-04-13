package nasa.model.module;

import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.CS2106;
import static nasa.testutil.TypicalModules.GEH1001;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();
    private final UniqueModuleList newUniqueModuleList = new UniqueModuleList();

    @Test
    void contains() {
        uniqueModuleList.add(CS2103T);
        assertTrue(uniqueModuleList.contains(CS2103T.getModuleCode()));
    }

    @Test
    void setModule() {
    }

    @Test
    void remove() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.remove(CS2103T.getModuleCode());
        assertFalse(uniqueModuleList.contains(CS2103T.getModuleCode()));
    }

    @Test
    void setModules() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T.getModuleCode(), CS2106);
        assertFalse(uniqueModuleList.contains(CS2103T.getModuleCode()));
        assertTrue(uniqueModuleList.contains(CS2106.getModuleCode()));
    }

    @Test
    void testSetModules() {
        newUniqueModuleList.add(GEH1001);
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModules(newUniqueModuleList);
        assertTrue(uniqueModuleList.contains(GEH1001.getModuleCode()));
        assertFalse(uniqueModuleList.contains(CS2103T.getModuleCode()));
    }

    @Test
    void getModule() {
        uniqueModuleList.add(CS2103T);
        assertTrue(CS2103T.equals(uniqueModuleList.getModule(CS2103T.getModuleCode())));
        assertFalse(CS2106.equals(uniqueModuleList.getModule(CS2103T.getModuleCode())));
    }
}
