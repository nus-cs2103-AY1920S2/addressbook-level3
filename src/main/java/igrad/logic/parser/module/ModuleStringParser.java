package igrad.logic.parser.module;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import igrad.model.module.ModuleCode;

/**
 * Parses a string containing moduleCodes and returns
 * a list of moduleCodes. Strings can be in any format.
 */
public class ModuleStringParser {

    private String stringOfModules;
    private ModuleCode[] moduleCodes;

    public ModuleStringParser(String stringOfModules) {
        this.stringOfModules = stringOfModules;
        this.moduleCodes = getModulesFromString();
    }

    /**
     * Returns a String array of preclusion module codes in String form from data.
     */
    public ModuleCode[] getModulesFromString() {

        // stringOfModules can be in multiple forms.
        String[] strings = stringOfModules.split(" ");

        //remove any non ASCII characters
        //remove whitespace
        //validate with validation regex
        List<String> stringsArray = Stream.of(strings)
                                    .map(s -> s.replaceAll("[^a-zA-Z0-9\\s]", ""))
                                    .map(String::trim)
                                    .filter(s -> s.matches(ModuleCode.VALIDATION_REGEX))
                                    .collect(Collectors.toList());

        return new ModuleCode[0];
    }

    public ModuleCode[] getModuleCodes() {
        return moduleCodes;
    }

}
