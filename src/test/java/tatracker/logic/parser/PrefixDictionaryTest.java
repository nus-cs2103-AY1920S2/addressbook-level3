package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PrefixDictionaryTest {
    // Check that modifiable Lists are stored as unmodifiable internally
    private List<Prefix> parameters = Arrays.asList(Prefixes.DATE, Prefixes.START_TIME);
    private List<Prefix> optionals = Arrays.asList(Prefixes.END_TIME, Prefixes.NOTES);

    private Prefix parameterPrefix = Prefixes.START_TIME;
    private Prefix optionalPrefix = Prefixes.END_TIME;

    private PrefixDictionary dictionary = new PrefixDictionary(parameters, optionals);
    private PrefixDictionary emptyDictionary = new PrefixDictionary();

    @Test
    public void constructor_validFields_success() {
        // Filled out details
        assertNotSame(parameters, dictionary.getParameters());
        assertNotSame(optionals, dictionary.getOptionals());

        assertTrue(parameters.stream()
                .map(Prefix::getPrefix)
                .allMatch(dictionary::hasPrefixDetails));

        assertTrue(optionals.stream()
                .map(Prefix::getPrefix)
                .allMatch(dictionary::hasPrefixDetails));

        // Empty details
        PrefixDictionary dictionaryWithEmptyValues = new PrefixDictionary(List.of(), List.of());
        assertTrue(dictionaryWithEmptyValues.getParameters().isEmpty());
        assertTrue(dictionaryWithEmptyValues.getOptionals().isEmpty());
    }

    @Test
    public void constructor_invalidFields_throwsIllegalArgumentException() {
        // Duplicate parameter in parameters
        List<Prefix> editedPrefixes = new ArrayList<>(parameters);
        editedPrefixes.add(parameterPrefix);

        List<Prefix> duplicateParamInParams = List.copyOf(editedPrefixes);

        assertThrows(IllegalArgumentException.class, () ->
                new PrefixDictionary(duplicateParamInParams, optionals));

        // Duplicate optional in parameters
        editedPrefixes = new ArrayList<>(parameters);
        editedPrefixes.add(optionalPrefix);

        List<Prefix> duplicateOptInParams = List.copyOf(editedPrefixes);

        assertThrows(IllegalArgumentException.class, () ->
                new PrefixDictionary(duplicateOptInParams, optionals));

        // Duplicate optional in parameters
        editedPrefixes = new ArrayList<>(optionals);
        editedPrefixes.add(parameterPrefix);

        List<Prefix> duplicateParamInOpts = List.copyOf(editedPrefixes);

        assertThrows(IllegalArgumentException.class, () ->
                new PrefixDictionary(parameters, duplicateParamInOpts));

        // Duplicate optional in parameters
        editedPrefixes = new ArrayList<>(optionals);
        editedPrefixes.add(optionalPrefix);

        List<Prefix> duplicateOptInOpts = List.copyOf(editedPrefixes);

        assertThrows(IllegalArgumentException.class, () ->
                new PrefixDictionary(parameters, duplicateOptInOpts));
    }

    @Test
    public void hasPreamble() {
        // Has preamble only -> true
        PrefixDictionary pd = new PrefixDictionary(List.of(Prefixes.INDEX)); // Example prefix which has preamble
        assertTrue(pd.hasPreamble());

        // No preamble -> false
        pd = new PrefixDictionary(List.of(Prefixes.NAME));
        assertFalse(pd.hasPreamble());

        // Has preamble and others -> true
        pd = new PrefixDictionary(List.of(Prefixes.NAME, Prefixes.INDEX));
        assertTrue(pd.hasPreamble());

        // No prefixes -> false
        pd = new PrefixDictionary();
        assertFalse(pd.hasPreamble());
    }
}
