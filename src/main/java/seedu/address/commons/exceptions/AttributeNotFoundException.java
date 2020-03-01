package seedu.address.commons.exceptions;

/*
 * AttributeNotFoundException
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>AttributeNotFoundException class describes the behavior
 * when an Attribute is not found in the AttributeList.</p>
 * @author AY1920S2-W15-2
 */

public class AttributeNotFoundException extends Exception {
    public AttributeNotFoundException(String message) {
        super(message);
    }
}
