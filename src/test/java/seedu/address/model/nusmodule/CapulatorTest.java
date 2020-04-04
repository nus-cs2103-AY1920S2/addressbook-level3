package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class CapulatorTest {

    @Test
    void calculateCap() {
        NusModule module1 = new NusModule(new ModuleCode("CS2030"), 4, false,
                Optional.of(Grade.BPLUS), new ArrayList<>(), null);
        NusModule module2 = new NusModule(new ModuleCode("CS2040"), 4, false,
                Optional.of(Grade.A), new ArrayList<>(), null);
        NusModule module3 = new NusModule(new ModuleCode("CS2100"), 4, false,
                Optional.of(Grade.AMINUS), new ArrayList<>(), null);
        NusModule module4 = new NusModule(new ModuleCode("GES1012"), 4, false,
                Optional.of(Grade.CPLUS), new ArrayList<>(), null);
        NusModule module5 = new NusModule(new ModuleCode("GEQ1000"), 4, false,
                Optional.of(Grade.S), new ArrayList<>(), null);
        NusModule module6 = new NusModule(new ModuleCode("CS2103T"), 4, true,
                Optional.empty(), new ArrayList<>(), null);
        List<NusModule> modules = new ArrayList<>() {
            {
                add(module1);
                add(module2);
                add(module3);
                add(module4);
                add(module5);
                add(module6);
            }
        };
        double result = new Capulator(modules).calculateCap();
        assertEquals(4.0, result);
    }
}
