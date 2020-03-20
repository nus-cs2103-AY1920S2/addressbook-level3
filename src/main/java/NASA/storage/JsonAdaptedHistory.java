package nasa.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import nasa.commons.exceptions.IllegalValueException;
import nasa.logic.commands.Command;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.HistoryBook;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.activity.UniqueActivityList;
import nasa.model.history.ModuleListHistory;
import nasa.model.module.UniqueModuleList;

@JsonRootName(value = "historybook")
public class JsonAdaptedHistory {

    private final List<List<JsonAdaptedModule>> moduleList = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedHistory(@JsonProperty("moduleList") List<List<JsonAdaptedModule>> moduleList) {
        if (moduleList != null) {
            this.moduleList.addAll(moduleList);
        }
    }

    public JsonAdaptedHistory(ReadOnlyHistory<UniqueModuleList> source) {
        moduleList.addAll(source.getModuleListHistory().stream()
            .map(x -> x.asUnmodifiableObservableList().stream()
                    .map(JsonAdaptedModule::new).collect(Collectors.toList()))
            .collect(Collectors.toList()));
    }

    public ModuleListHistory<UniqueModuleList> toModelType() throws IllegalValueException {
        final List<UniqueModuleList> historyList = new ArrayList<>();
        for (List<JsonAdaptedModule> list : moduleList) {
            final UniqueModuleList uniqueList = new UniqueModuleList();
            for (JsonAdaptedModule module : list) {
                uniqueList.add(module.toModelType());
            }
            historyList.add(uniqueList);
        }

        final ModuleListHistory<UniqueModuleList> temp = new ModuleListHistory<>();
        temp.setStackList(historyList);
        return temp;
    }
}
