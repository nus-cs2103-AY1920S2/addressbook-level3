package seedu.address.model.graduation;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.programmes.specialisations.GenericSpecialisation;
import seedu.address.model.programmes.specialisations.cs.GenericCsSpecialisation;

public class FocusAreaGraduationRequirement extends GraduationRequirement {

    private GenericSpecialisation specialisation = null;

    public FocusAreaGraduationRequirement(Model model) {
        try {
            specialisation = model.getActiveStudent().getSpecialisation();
        } catch (Exception e) {
            return;
        }
    }

    public void setSpecialisation(GenericSpecialisation specialisation) {
        this.specialisation = specialisation;
    }

    public boolean isFulfilled(List<ModuleCode> moduleCodes) {
        if (specialisation == null) {
            return false;
        }
        return specialisation.isFulfilled(moduleCodes);
    }

    public GenericSpecialisation getSpecialisation() {
        return specialisation;
    }

    public String getString(List<ModuleCode> moduleCodes) {
        try {
            StringBuilder sb = new StringBuilder()
                .append("[")
                .append(getStatusIcon(specialisation.isFulfilled(moduleCodes)))
                .append("] " + "Focus Area: ")
                .append(specialisation.getName())
                .append("\n    [")
                .append(getStatusIcon(((GenericCsSpecialisation) specialisation).arePrimariesFulfilled(moduleCodes)))
                .append("] Primaries");
            for (ModuleCode primaries : ((GenericCsSpecialisation) getSpecialisation()).getPrimaries()) {
                sb.append("\n        ").append(new SingleGraduationRequirement(primaries).getString(moduleCodes));
            }
            sb.append("\n    [")
                .append(getStatusIcon(((GenericCsSpecialisation) specialisation).areElectivesFulfilled(moduleCodes)))
                .append("] Electives");
            for (ModuleCode electives : ((GenericCsSpecialisation) getSpecialisation()).getElectives()) {
                sb.append("\n        ").append(new SingleGraduationRequirement(electives).getString(moduleCodes));
            }
            return sb.toString();
        } catch (Exception e) {
            return "[X] Focus Area: Unknown (Please set your specialisation first!)";
        }
    }

    @Override
    public String toString() {
        return "[?] " + "Focus Area: " + specialisation.getName();
    }
}
