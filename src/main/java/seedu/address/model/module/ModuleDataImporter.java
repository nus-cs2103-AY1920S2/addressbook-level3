package seedu.address.model.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.Main;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;


public class ModuleDataImporter {

    private static final String DOWNLOAD_URL = "https://api.nusmods.com/v2/{0}/moduleInfo.json";
    private static final String[] acadYears = new String[] {
        "2019-2020",
        "2018-2019"
    };
    private static List<Module> modules = new ArrayList<>();

    public static List<Module> run() {
        File file;
        Module module;
        String fileName = "";
        for (String acadYear : acadYears) {
            try {
                fileName = "json/moduleInfo_{0}.json".replace("{0}", acadYear);
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                String text = new Scanner(Main.class.getClassLoader().getResourceAsStream(fileName),
                    "UTF-8").useDelimiter("\\A").next();
                Optional<JsonSerializableModule[]> optionalModules = JsonUtil.readJsonString(text,
                    JsonSerializableModule[].class);
                if (optionalModules.isPresent()) {
                    JsonSerializableModule[] moduleArray = optionalModules.get();
                    for (JsonSerializableModule m : moduleArray) {
                        try {
                            module = m.toModelType();
                            if (!modules.contains(module)) {
                                modules.add(module);
                            }
                        } catch (IllegalValueException ex) {
                            System.err.println(ex);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading file: " + fileName);
                System.err.println(e);
            }
        }
        return modules;
    }

    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(modules));
    }
}
