package com.notably.model.block;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Block's body in Notably.
 * Guarantees: immutable; is non-null
 */
public class Body {
    public final String blockBody;

    /**
     * Constructs a {@code Body}.
     *
     * @param body A valid body.
     */
    public Body(String body) {
        requireNonNull(body);
        blockBody = body.trim();
    }

    /**
     * Returns a String of the Body contents.
     *
     * @return Body text string.
     */
    public String getText() {
        return this.blockBody;
    }
}
