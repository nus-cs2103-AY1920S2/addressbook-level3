package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.AttributeNotFoundException;
import seedu.address.commons.exceptions.DuplicatePrefixException;

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

    public Attribute findAttribute(String attributePrefix) throws AttributeNotFoundException, DuplicatePrefixException {
        try {
            checkPrefix(attributePrefix);
        } catch (AttributeNotFoundException | DuplicatePrefixException e) {
            throw e;
        }
        return attributes.stream().filter(attribute -> attribute.toString().startsWith(attributePrefix))
                                  .findFirst()
                                  .get();
    }

    private String deleteAttribute(String attributePrefix) throws AttributeNotFoundException, DuplicatePrefixException {
        Attribute attribute = findAttribute(attributePrefix);
        attributes.remove(attribute);
        return String.format("Successfully removed %s", attribute);
    }

    private void checkPrefix(String attributePrefix) throws AttributeNotFoundException, DuplicatePrefixException {
        long startWithPrefix = attributes.stream()
                .filter(attribute -> attribute.toString().startsWith(attributePrefix))
                .count();

        if (startWithPrefix > 1) {
            throw new DuplicatePrefixException(DUPLICATE_MESSAGE);
        } else if (startWithPrefix == 0) {
            throw new AttributeNotFoundException(NOT_FOUND_MESSAGE);
        }
    }
    private boolean isDuplicate(Attribute attribute) {
        return attributes.stream().noneMatch(attribute::equals);
    }
}
