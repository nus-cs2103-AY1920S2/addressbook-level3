package seedu.address.model.hirelah;

/*
 * Attribute
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * Attribute class represents the parameters that are
 * taken into account to assess the interviewee.
 * @author AY1920S2-W15-2
 */

public class Attribute {
    private String name;

    /**
     * Constructs an Attribute instance.
     * @param name The name of the attribute.
     */

    public Attribute(String name) {
        this.name = name;
    }

    /**
     * Overrides the Object's toString method so that
     * it displays the name of the attribute.
     * @return The name of the attribute.
     */

    @Override
    public String toString() {
        return this.name;
    }
}
