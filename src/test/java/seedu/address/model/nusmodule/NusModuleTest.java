package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class NusModuleTest {

    @Test
    void getGradePoint() {
        NusModule module1 = new NusModule(new ModuleCode("CS2030"), 4,
                Optional.of(Grade.A), new ArrayList<>());
        assertEquals(5.0, module1.getGradePoint());
        NusModule module2 = new NusModule(new ModuleCode("CS2030"), 4,
                Optional.of(Grade.C), new ArrayList<>());
        assertEquals(2.0, module2.getGradePoint());
    }
}
