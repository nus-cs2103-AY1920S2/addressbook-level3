package seedu.address.model.mod;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Represents a Module in TAble.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mod {

    // Identity fields
    private final ModCode modCode;
    private final String modName;

    // Data fields
    private String note;
    private List<ModLinkPair> links;

    /**
     * Every field must be present and not null.
     */
    public Mod(ModCode modCode, String name) {
        requireAllNonNull(modCode, name);
        this.modCode = modCode;
        this.modName = name;
        this.note = "";
        this.links = new ArrayList<>();
    }

    public Mod(Mod mod, String note) {
        requireAllNonNull(mod, note);
        this.modCode = mod.getModCode();
        this.modName = mod.getModName();
        this.note = note;
        this.links = new ArrayList<>(mod.getLinks());
    }

    public Mod(Mod mod, String linkName, ModLink link) {
        requireAllNonNull(mod, linkName, link);
        this.modCode = mod.getModCode();
        this.modName = mod.getModName();
        this.note = mod.getNote();
        this.links = new ArrayList<>(mod.getLinks());
        links.add(new ModLinkPair(linkName, link));
    }

    public ModCode getModCode() {
        return modCode;
    }

    public String getModName() {
        return modName;
    }

    public String getNote() {
        return note;
    }

    /**
     * Returns an immutable map of description and links, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<ModLinkPair> getLinks() {
        return FXCollections.observableList(links);
    }

    /**
     * Removes all modLinks associated with this module
     */
    public Mod clearLinks() {
        return new Mod(new Mod(this.modCode, this.modName), this.getNote());
    }

    /**
     * Returns true if both modules have the same module code.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameMod(Mod otherMod) {
        if (otherMod == this) {
            return true;
        }

        return otherMod != null
                && otherMod.getModCode().equals(getModCode());
    }

    /**
     * Returns true if both modules have the same module code, description and tags.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.mod.Mod)) {
            return false;
        }

        seedu.address.model.mod.Mod otherMod = (seedu.address.model.mod.Mod) other;
        return otherMod.getModCode().equals(getModCode())
            && otherMod.getModName().equals(getModName())
            && otherMod.getLinks().equals(getLinks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(modCode, modName, links);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getModCode())
            .append(" Name: ")
            .append(getModName())
            .append(" Notes: ")
            .append(getNote())
            .append(" Links: ");
        getLinks().forEach(p -> builder.append(p.getKey()).append("=").append(p.getValue()).append("; "));
        return builder.toString();
    }

}
