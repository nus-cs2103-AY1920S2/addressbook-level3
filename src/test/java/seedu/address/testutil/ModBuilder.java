package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.model.mod.ModLinkPair;

/**
 * A utility class to help build {@code Mod} objects
 */
public class ModBuilder {
    public static final String DEFAULT_MODCODE = "CS2103";
    public static final String DEFAULT_MODNAME = "SOFTWARE ENGINEERING";

    private ModCode modCode;
    private String modName;
    private String note;
    private List<ModLinkPair> links;

    public ModBuilder() {
        this.modCode = new ModCode(DEFAULT_MODCODE);
        this.modName = DEFAULT_MODNAME;
        this.note = "";
        this.links = new ArrayList<>();
    }

    /**
     * Initializes the ModBuilder with the data of {@code modToCopy}.
     */
    public ModBuilder(Mod modToCopy) {
        this.modCode = modToCopy.getModCode();
        this.modName = modToCopy.getModName();
        this.note = modToCopy.getNote();
        this.links = modToCopy.getLinks();
    }

    /**
     * Sets the {@code modCode} of the {@code Mod} to be built.
     */
    public ModBuilder withModCode(String modCode) {
        this.modCode = new ModCode(modCode);
        return this;
    }

    /**
     * Sets the {@code modName} of the {@code Mod} to be built.
     */
    public ModBuilder withModName(String modName) {
        this.modName = modName;
        return this;
    }

    /**
     * Sets the {@code modNote} of the {@code Mod} to be built.
     */
    public ModBuilder withModNote(String modNote) {
        this.note = modNote;
        return this;
    }

    /**
     * Adds a {@code modLinkPair} to the {@code Mod} to be built.
     */
    public ModBuilder addModLinkPair(String modLinkName, String modLink) {
        this.links.add(new ModLinkPair(modLinkName, new ModLink(modLink)));
        return this;
    }

    /**
     * Builds the Mod.
     */
    public Mod build() {
        Mod mod = new Mod(modCode, modName);
        mod = new Mod(mod, note);
        for (ModLinkPair modLinkPair : links) {
            mod = new Mod(mod, modLinkPair.getKey(), modLinkPair.getValue());
        }
        return mod;
    }
}
