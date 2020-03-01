package seedu.address.commons.exceptions;

/*
 * DuplicatePrefixException
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>DuplicatePrefixException describes the behavior when
 * there are multiple number of attributes with the same prefix..</p>
 * @author AY1920S2-W15-2
 */

public class DuplicatePrefixException extends Exception {
    public DuplicatePrefixException(String message) {
        super(message);
    }
}
