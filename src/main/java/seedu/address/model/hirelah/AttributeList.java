package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.ArrayList;

/*
 * AttributeList
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>AttributeList class manages the list of attributes that have been
 * added by the interviewer. AttributeList can retrieve the attributes using
 * the prefix.</p>
 * @author AY1920S2-W15-2
 */

public class AttributeList {
    private ArrayList<Attribute> attributes;
    private static final String DUPLICATE_MESSAGE = "There are multiple attributes with the same prefix.";
    private static final String NOT_FOUND_MESSAGE = "No attributes with the entered prefix.";

    /**
     * Constructs an AttributeList instance.
     */

    public AttributeList() {
        this.attributes = new ArrayList<>();
    }

    /**
     * Adds the attribute to the list.
     * @param attributeName The attribute name.
     * @return The message outcome.
     */

    public String addAttribute(String attributeName) {
        try {
            Attribute attribute = new Attribute(attributeName);
            boolean isDuplicate = isDuplicate(attribute);

            if (isDuplicate) {
                return "This attribute is already exists!";
            }

            attributes.add(attribute);
            return String.format("Successfully added attribute: %s", attribute);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Find the attribute based on its prefix.
     * @param attributePrefix The prefix of the attribute.
     * @return The corresponding Attribute instance.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Attribute found.
     */

    public Attribute findAttribute(String attributePrefix) throws IllegalValueException {
        try {
            checkPrefix(attributePrefix);
        } catch (IllegalValueException e) {
            throw e;
        }
        return attributes.stream().filter(attribute -> attribute.toString().startsWith(attributePrefix))
                                  .findFirst()
                                  .get();
    }

    /**
     * Deletes the attribute by its prefix.
     * @param attributePrefix The prefix of the attribute.
     * @return The outcome message.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Attribute found.
     */

    private String deleteAttribute(String attributePrefix) throws IllegalValueException {
        Attribute attribute = findAttribute(attributePrefix);
        attributes.remove(attribute);
        return String.format("Successfully removed %s", attribute);
    }

    /**
     * Checks the number of attributes that starts with the prefix.
     * @param attributePrefix The prefix of the attribute.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Attribute found.
     */
    private void checkPrefix(String attributePrefix) throws IllegalValueException {
        long startWithPrefix = attributes.stream()
                .filter(attribute -> attribute.toString().startsWith(attributePrefix))
                .count();

        if (startWithPrefix > 1) {
            throw new IllegalValueException(DUPLICATE_MESSAGE);
        } else if (startWithPrefix == 0) {
            throw new IllegalValueException(NOT_FOUND_MESSAGE);
        }
    }
    private boolean isDuplicate(Attribute attribute) {
        return attributes.stream().noneMatch(attribute::equals);
    }
}
