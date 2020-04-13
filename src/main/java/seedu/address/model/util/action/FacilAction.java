package seedu.address.model.util.action;

import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;

/**
 * Represents a DoableAction involving a {@code Facilitator}.
 */
public class FacilAction implements DoableAction<Facilitator> {
    private final Facilitator target;
    private final Facilitator replacement;
    private final DoableActionType type;

    public FacilAction(Facilitator target, DoableActionType type) {
        assert type != DoableActionType.EDIT;

        this.target = target;
        this.replacement = null;
        this.type = type;
    }

    public FacilAction(Facilitator target, Facilitator replacement, DoableActionType type) {
        assert type == DoableActionType.EDIT;

        this.target = target;
        this.replacement = replacement;
        this.type = type;
    }

    @Override
    public void undo(Model model) {
        switch (type) {
        case ADD:
            model.deleteFacilitator(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setFacilitator(replacement, target);
            break;
        case DELETE:
            model.addFacilitator(target);
            break;
        default:
            break;
        }
    }

    @Override
    public void redo(Model model) {
        switch (type) {
        case ADD:
            model.addFacilitator(target);
            break;
        case EDIT:
            assert replacement != null;
            model.setFacilitator(target, replacement);
            break;
        case DELETE:
            model.deleteFacilitator(target);
            break;
        default:
            break;
        }
    }

    @Override
    public Facilitator getTarget() {
        return this.target;
    }

    @Override
    public Facilitator getReplacement() {
        return replacement;
    }

    @Override
    public DoableActionType getType() {
        return this.type;
    }
}
