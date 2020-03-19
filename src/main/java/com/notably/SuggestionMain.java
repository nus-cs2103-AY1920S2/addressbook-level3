package com.notably;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.Path;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.logic.suggestion.commands.OpenSuggestionCommand;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.commandinput.CommandInputModel;
import com.notably.model.commandinput.CommandInputModelImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;

/**
 * Main class to test Suggestion feature.
 */
public class SuggestionMain {
    /**
     * Main class.
     * @param args Argument.
     */
    public static void main(String[] args) throws InvalidPathException {
        System.out.println("Input:");
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        CommandInputModel commandInputModel = new CommandInputModelImpl();
        Model model = new ModelManager(suggestionModel, commandInputModel);

        List<String> components = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equalsIgnoreCase("exit")) {
            String[] inputs = input.split(" ");
            String command = inputs[0];
            String pathName = inputs[1];
            Path path = AbsolutePath.fromString(pathName);
            SuggestionCommand suggestionCommand = null;

            if (command.equalsIgnoreCase("open")) {
                suggestionCommand = new OpenSuggestionCommand(path);
            } else { // TODO: add more commands.
                System.out.println("Command not recognized");
            }

            suggestionCommand.execute(model);
            input = sc.nextLine();
        }
    }
}
