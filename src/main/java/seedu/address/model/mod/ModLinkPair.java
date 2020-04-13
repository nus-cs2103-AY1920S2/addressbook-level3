package seedu.address.model.mod;

/**
 * Data structure of module link together with its descriptor.
 */
public class ModLinkPair {
    private ModLink modLink;
    private String name;

    public ModLinkPair(String name, ModLink modLink) {
        this.modLink = modLink;
        this.name = name;
    }

    public String getKey() {
        return name;
    }

    public ModLink getValue() {
        return modLink;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModLinkPair)) {
            return false;
        }

        ModLinkPair otherModLinkPair = (ModLinkPair) other;
        return otherModLinkPair.getKey().equals(this.getKey())
            && otherModLinkPair.getValue().equals(this.getValue());
    }
}
